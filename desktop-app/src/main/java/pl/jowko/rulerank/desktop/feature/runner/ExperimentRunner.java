package pl.jowko.rulerank.desktop.feature.runner;

import pl.jowko.rulerank.desktop.exception.ConfigurationException;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.learningtable.LearningTable;
import pl.jowko.rulerank.desktop.feature.properties.DefaultPropertiesProvider;
import pl.jowko.rulerank.desktop.feature.properties.RuleRankProperties;
import pl.jowko.rulerank.desktop.feature.properties.RunnerPropertiesProvider;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceController;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.rulerank.desktop.utils.FileExtensionExtractor;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.Settings;
import pl.poznan.put.cs.idss.jrs.ranking.Ranker;
import pl.poznan.put.cs.idss.jrs.ranking.RankerParameters;
import pl.poznan.put.cs.idss.jrs.ranking.RankerResults;

import java.io.IOException;
import java.nio.file.Paths;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;
import static pl.jowko.rulerank.desktop.utils.StringUtils.isNullOrEmpty;

/**
 * This class is used to perform ruleRank experiment. <br>
 * It prepares data, validates them, runs experiment and save results. <br>
 *  <br>
 * Created by Piotr on 2018-06-04
 */
public class ExperimentRunner {
	
	private RuleRankProperties properties;
	private ExperimentRunnerValidator validator;
	private String experimentPath;
	
	/**
	 * Creates instance of this class. <br>
	 * Also prepares properties for later processing.
	 * @param properties from properties form
	 * @param propertiesItem representing properties file in workspace tree
	 */
	public ExperimentRunner(RuleRankProperties properties, WorkspaceItem propertiesItem) {
		RunnerPropertiesProvider propertiesProvider;
		LanguageService labels = LanguageService.getInstance();
		
		try {
			RuleRankProperties defaults = new DefaultPropertiesProvider().getDefaultProperties();
			propertiesProvider = new RunnerPropertiesProvider(properties, defaults);
		} catch (IOException | ConfigurationException e) {
			throw new RunnerException(labels.get(Labels.RUN_PROP_READ_ERROR) + e.getMessage());
		}
		
		experimentPath = propertiesItem.getExperimentPath();
		this.properties = propertiesProvider.getPropertiesWithDefaults();
		validator = new ExperimentRunnerValidator(this.properties, propertiesItem);
	}
	
	/**
	 * Runs ruleRank experiment. <br>
	 * It prepares rest of data, logs experiment parameters, runs experiment and save results.
	 */
	public void run() {
		initializeFileNames();
		if(not(validator.isValid())) {
			return;
		}
		LearningTable learningTable = validator.getLearningTable();
		LearningTable testTable = validator.getTestTable();
		Settings.getInstance().precision = properties.getPrecision();
		
		RankerParameters parameters =  new RankerParametersAssembler(properties, learningTable, testTable).getParameters();
		RuleRankLogger.info("Performing experiment with parameters:\n" + properties);
		
		Ranker ranker = new Ranker();
		RankerResults results = ranker.run(parameters);
		RuleRankLogger.info("Output from RuleRank:");
		for(String msg : ranker.getMessages()) {
			RuleRankLogger.none(msg);
		}
		
		new ResultsSaver(results, properties, experimentPath).save();
		WorkspaceController.getInstance().refresh();
	}
	
	/**
	 * If file names in properties were not configured, they will be replaced with default ones calculated from learning or test table name. <br>
	 * Learning table name is used in: rules, partial pct and apx <br>
	 * Test table name is used in: graph and ranking
	 */
	private void initializeFileNames() {
		if(isNullOrEmpty(properties.getTestDataFile()))
			properties.setTestDataFile(properties.getLearningDataFile());
		
		String learningBaseName = getBaseFileName(properties.getLearningDataFile());
		String testBaseName = getBaseFileName(properties.getTestDataFile());
		
		if(isNullOrEmpty(properties.getPctFile()))
			properties.setPctFile(learningBaseName + "_partialPCT.isf");
		
		if(isNullOrEmpty(properties.getPctApxFile()))
			properties.setPctApxFile(learningBaseName + "_partialPCT.apx");
		
		if(isNullOrEmpty(properties.getPctRulesFile()))
			properties.setPctRulesFile(learningBaseName + "_partialPCT.rules");
		
		if(isNullOrEmpty(properties.getRankingFile()))
			properties.setRankingFile(testBaseName + ".ranking");
		
		if(isNullOrEmpty(properties.getPreferenceGraphFile()))
			properties.setPreferenceGraphFile(testBaseName + ".graph");
		
		String reportFilePath = properties.getTestDataFile().replaceFirst("[.]isf", "_report") + ".txt";
		properties.setReportFile(reportFilePath);
	}
	
	private String getBaseFileName(String fileName) {
		String baseName = Paths.get(fileName).getFileName().toString();
		return FileExtensionExtractor.getFileName(baseName);
	}
	
}
