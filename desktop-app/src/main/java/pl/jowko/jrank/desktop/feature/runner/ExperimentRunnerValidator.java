package pl.jowko.jrank.desktop.feature.runner;

import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.learningtable.LearningTable;
import pl.jowko.jrank.desktop.feature.learningtable.LearningTableValidator;
import pl.jowko.jrank.desktop.feature.properties.JRankProperties;
import pl.jowko.jrank.desktop.feature.properties.PropertiesMandatoryFieldsValidator;
import pl.jowko.jrank.desktop.feature.properties.PropertiesValidator;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.desktop.service.JRSFileMediator;
import pl.jowko.jrank.desktop.utils.StringUtils;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.isNull;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;
import static pl.jowko.jrank.desktop.utils.FileExtensionExtractor.getFileName;
import static pl.jowko.jrank.desktop.utils.StringUtils.isNotNullOrEmpty;

/**
 * Created by Piotr on 2018-06-04
 * This class validates properties before running ruleRank experiment.
 * It also reads learning memory container.
 */
class ExperimentRunnerValidator {
	
	private PropertiesValidator validator;
	private PropertiesMandatoryFieldsValidator emptyFieldsValidator;
	private JRankProperties properties;
	private LearningTable learningTable;
	private LearningTable testTable;
	private String experimentPath;
	private LanguageService labels;
	
	/**
	 * Creates instance of this class.
	 * @param properties to validate
	 * @param propertiesItem representing properties file in workspace tree
	 */
	ExperimentRunnerValidator(JRankProperties properties, WorkspaceItem propertiesItem) {
		this.properties = properties;
		experimentPath = propertiesItem.getExperimentPath();
		
		validator = new PropertiesValidator(properties);
		emptyFieldsValidator = new PropertiesMandatoryFieldsValidator(properties);
		labels = LanguageService.getInstance();
	}
	
	/**
	 * Checks, if provided properties are valid.
	 * It will validate properties, learning and test data table and check if any files will be overridden.
	 * @return true if all files are valid
	 */
	boolean isValid() {
		if(not(validator.isValid()))
			throw new RunnerException(validator.getErrorMessages());
		
		if(not(emptyFieldsValidator.isValid()))
			throw new RunnerException(emptyFieldsValidator.getErrorMessages());
		
		learningTable = readAndValidateMemoryContainer(properties.getLearningDataFile(), labels.get(Labels.RUN_LEARNING_FILE));
		
		if(StringUtils.isNotNullOrEmpty(properties.getTestDataFile()))
			testTable = readAndValidateMemoryContainer(properties.getTestDataFile(), labels.get(Labels.RUN_TEST_FILE));
		else
			testTable = learningTable;
		
		validateAndChooseLearningInformation();
		
		if(isFilesWillBeOverridden())
			return DialogsService.showConfirmationDialog("", labels.get(Labels.RUN_OVERRIDE_FILES));
		
		return true;
	}
	
	/**
	 * Gets loaded isf learning table
	 * @return LearningTable with loaded learning data
	 */
	LearningTable getLearningTable() {
		return learningTable;
	}
	
	/**
	 * Gets loaded isf test table
	 * @return LearningTable with loaded test data
	 */
	LearningTable getTestTable() {
		return testTable;
	}
	
	/**
	 * Loads isf data table and validates it.
	 * @param filePath to isf file
	 * @return LearningTable containing isf data table
	 */
	private LearningTable readAndValidateMemoryContainer(String filePath, String containerName) {
		MemoryContainer container;
		try {
			container = readMemoryContainer(filePath);
		} catch (RuntimeException e) {
			throw new RunnerException(e.getMessage());
		}
		
		if(isNull(container))
			throw new RunnerException(containerName + labels.get(Labels.RUN_FILE_IS_EMPTY));
		
		LearningTable learningTable = new LearningTable(container);
		LearningTableValidator validator = new LearningTableValidator(learningTable);
		
		if(validator.isValid())
			return learningTable;
		
		throw new RunnerException(labels.get(Labels.RUN_ISF_ERRORS) + '\n' + validator.getErrorMessages());
	}
	
	private MemoryContainer readMemoryContainer(String filePath) {
		Path path = Paths.get(filePath);
		String containerPath = getAbsolutePath(path);
		JRankLogger.info("Reading learning table from: " + containerPath);
		
		return JRSFileMediator.loadMemoryContainer(containerPath);
	}
	
	/**
	 * This method checks, what sources of information user configured.
	 * If used configured more than one source of information,
	 * he will be asked to chose on of them.
	 * @see ExperimentRunnerDialog
	 */
	private void validateAndChooseLearningInformation() {
		boolean isRanking = isNotNullOrEmpty(properties.getReferenceRanking());
		boolean isPairs = isNotNullOrEmpty(properties.getPairs());
		boolean isDecisionAtt = isContainerHasDecisionAttribute();
		
		// xor for tree elements, if only one type of information is provided, return
		if((isRanking ^ isPairs ^ isDecisionAtt) ^ (isRanking && isPairs && isDecisionAtt))
			return;
		
		ExperimentRunnerDialog dialog = new ExperimentRunnerDialog(isRanking, isPairs, isDecisionAtt);
		dialog.showDialog();
		
		if(dialog.isRankingSelected()) {
			properties.setPairs("");
			//TODO decision attributes?
		} else if(dialog.isPairsSelected()) {
			properties.setReferenceRanking("");
			//TODO decision attributes?
		} else if(dialog.isAttributeSelected()) {
			properties.setReferenceRanking("");
			properties.setPairs("");
		}
	}
	
	private boolean isContainerHasDecisionAttribute() {
		for(Attribute attribute : learningTable.getAttributes()) {
			if(attribute.getKind() == Attribute.DECISION)
				return true;
		}
		return false;
	}
	
	private boolean isFilesWillBeOverridden() {
		try {
			isFileExists(properties.getPctFile(), true, ".isf");
			isFileExists(properties.getPctApxFile(), true, ".apx");
			isFileExists(properties.getPctRulesFile(), true, ".rules");
			isFileExists(properties.getRankingFile(), false, ".ranking");
			isFileExists(properties.getPreferenceGraphFile(), false, ".graph");
		} catch (RunnerException e) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if file with provided path exists.
	 * If file exists, exception will be throw to indicate that files will be overridden.
	 * @param filePath of file to check
	 * @param isPctFile indicates if this file is one of pct files
	 * @param extension to indicate file extension
	 */
	private void isFileExists(String filePath, boolean isPctFile, String extension) {
		if(isNotNullOrEmpty(filePath)) {
			filePath = getAbsolutePath(Paths.get(filePath));
		} else {
			Path path = Paths.get(properties.getLearningDataFile()).getFileName();
			String fileName = getFileName(path.toString());
			
			if(isPctFile)
				fileName += "_partialPCT" + extension;
			else
				fileName += extension;
			
			filePath = experimentPath + fileName;
		}
		
		if(Files.exists(Paths.get(filePath)))
			throw new RunnerException();
	}
	
	private String getAbsolutePath(Path path) {
		if(path.isAbsolute()) {
			return path.toString();
		} else {
			return experimentPath + path.toString();
		}
	}
	
}
