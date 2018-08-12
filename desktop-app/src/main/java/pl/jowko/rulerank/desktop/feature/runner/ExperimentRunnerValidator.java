package pl.jowko.rulerank.desktop.feature.runner;

import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.learningtable.LearningTable;
import pl.jowko.rulerank.desktop.feature.learningtable.LearningTableValidator;
import pl.jowko.rulerank.desktop.feature.learningtable.MemoryContainerAssembler;
import pl.jowko.rulerank.desktop.feature.properties.RuleRankProperties;
import pl.jowko.rulerank.desktop.feature.properties.PropertiesMandatoryFieldsValidator;
import pl.jowko.rulerank.desktop.feature.properties.PropertiesValidator;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.rulerank.desktop.service.DialogsService;
import pl.jowko.rulerank.desktop.service.JRSFileMediator;
import pl.jowko.rulerank.desktop.utils.StringUtils;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.core.ContainerFailureException;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.ranking.SimpleRanking;
import pl.poznan.put.cs.idss.jrs.types.Attribute;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;
import static pl.jowko.rulerank.desktop.utils.PathUtils.getAbsoluteExperimentFilePath;
import static pl.jowko.rulerank.desktop.utils.StringUtils.isNotNullOrEmpty;
import static pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainerDecisionsManager.getFirstDecisionAttributeIndex;

/**
 * Created by Piotr on 2018-06-04
 * This class validates properties before running ruleRank experiment.
 * It also reads learning memory container.
 */
class ExperimentRunnerValidator {
	
	private PropertiesValidator validator;
	private PropertiesMandatoryFieldsValidator emptyFieldsValidator;
	private RuleRankProperties properties;
	private LearningTable learningTable;
	private LearningTable testTable;
	private String experimentPath;
	private LanguageService labels;
	
	/**
	 * Creates instance of this class.
	 * @param properties to validate
	 * @param propertiesItem representing properties file in workspace tree
	 */
	ExperimentRunnerValidator(RuleRankProperties properties, WorkspaceItem propertiesItem) {
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
		
		String fileNames = getOverriddenFileNames();
		if(fileNames.length() > 0)
			return DialogsService.showConfirmationDialog("", labels.get(Labels.RUN_OVERRIDE_FILES) + '\n' + fileNames);
		
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
		
		LearningTable table = new LearningTable(container);
		LearningTableValidator learningTableValidator = new LearningTableValidator(table);
		
		if(learningTableValidator.isValid())
			return table;
		
		throw new RunnerException(labels.get(Labels.RUN_ISF_ERRORS) + '\n' + learningTableValidator.getErrorMessages());
	}
	
	private MemoryContainer readMemoryContainer(String filePath) {
		String containerPath = getAbsoluteExperimentFilePath(experimentPath, filePath);
		RuleRankLogger.info("Reading learning table from: " + containerPath);
		
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
		
		if(not(isRanking) && not(isPairs) && not(isDecisionAtt)) {
			throw new RunnerException(labels.get(Labels.PROP_EMPTY_PAIRS_RANKING));
		}
		
		ExperimentRunnerDialog dialog = new ExperimentRunnerDialog(isRanking, isPairs, isDecisionAtt);
		dialog.showDialog();
		
		if(dialog.isRankingSelected()) {
			properties.setPairs("");
		} else if(dialog.isPairsSelected()) {
			properties.setReferenceRanking("");
		} else if(dialog.isAttributeSelected()) {
			properties.setPairs("");
			calculateAndSetReferenceRanking();
		}
	}
	
	/**
	 * This method was copied from RuleRank console application.
	 * It will calculate ranking using decision attribute.
	 */
	private void calculateAndSetReferenceRanking() {
		MemoryContainer container;
		try {
			container = MemoryContainerAssembler.assembleContainerFromTable(learningTable);
		} catch (ContainerFailureException e) {
			throw new RuleRankRuntimeException(e.getMessage());
		}
		
		//try to get preference information from decision attribute values
		int decisionAttributeIndex = getFirstDecisionAttributeIndex(container);
		
		//there is a decision criterion in the learning information table
		if (decisionAttributeIndex >= 0 && container.getAttribute(decisionAttributeIndex).getPreferenceType() != Attribute.NONE) {
			SimpleRanking ranking = new SimpleRanking(container);
			properties.setReferenceRanking(ranking.toSingleLineString());
		}
	}
	
	private boolean isContainerHasDecisionAttribute() {
		for(Attribute attribute : learningTable.getAttributes()) {
			if(attribute.getKind() == Attribute.DECISION)
				return true;
		}
		return false;
	}
	
	/**
	 * Gets overridden file names. If any file from properties could be overridden, its name will be returned.
	 * @return overridden file names
	 */
	private String getOverriddenFileNames() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(getFileNameIfExists(properties.getPctFile()));
		builder.append(getFileNameIfExists(properties.getPctApxFile()));
		builder.append(getFileNameIfExists(properties.getPctRulesFile()));
		builder.append(getFileNameIfExists(properties.getRankingFile()));
		builder.append(getFileNameIfExists(properties.getPreferenceGraphFile()));
		builder.append(getFileNameIfExists(properties.getReportFile()));
		
		if(builder.length() > 0)
			return builder.substring(0, builder.length()-2); // skip last space and comma
		
		return "";
	}
	
	/**
	 * Checks if file with provided path exists.
	 * If file exists, its file name will be returned.
	 * @param fileName of file to check
	 */
	private String getFileNameIfExists(String fileName) {
		String filePath = getAbsoluteExperimentFilePath(experimentPath, fileName);
		Path path = Paths.get(filePath);
		
		if(Files.exists(path))
			return " [" + path.getFileName() + "], ";
		else
			return "";
	}
	
}
