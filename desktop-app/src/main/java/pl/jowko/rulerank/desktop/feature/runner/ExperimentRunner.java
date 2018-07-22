package pl.jowko.rulerank.desktop.feature.runner;

import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.learningtable.LearningTable;
import pl.jowko.rulerank.desktop.feature.properties.DefaultPropertiesProvider;
import pl.jowko.rulerank.desktop.feature.properties.JRankProperties;
import pl.jowko.rulerank.desktop.feature.properties.RunnerPropertiesProvider;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceController;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.rulerank.desktop.utils.FileExtensionExtractor;
import pl.jowko.rulerank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.Settings;
import pl.poznan.put.cs.idss.jrs.ranking.Ranker;
import pl.poznan.put.cs.idss.jrs.ranking.RankerParameters;
import pl.poznan.put.cs.idss.jrs.ranking.RankerResults;

import java.io.IOException;
import java.nio.file.Paths;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;
import static pl.jowko.rulerank.desktop.utils.StringUtils.isNullOrEmpty;

/**
 * Created by Piotr on 2018-06-04
 * This class is used to perform ruleRank experiment.
 * It prepares data, validates them, runs experiment and save results.
 */
public class ExperimentRunner {
	
	private JRankProperties properties;
	private ExperimentRunnerValidator validator;
	private String experimentPath;
	
	/**
	 * Creates instance of this class.
	 * Also prepares properties for later processing.
	 * @param properties from properties form
	 * @param propertiesItem representing properties file in workspace tree
	 */
	public ExperimentRunner(JRankProperties properties, WorkspaceItem propertiesItem) {
		RunnerPropertiesProvider propertiesProvider;
		LanguageService labels = LanguageService.getInstance();
		
		try {
			JRankProperties defaults = new DefaultPropertiesProvider().getDefaultProperties();
			propertiesProvider = new RunnerPropertiesProvider(properties, defaults);
		} catch (IOException e) {
			throw new RunnerException(labels.get(Labels.RUN_PROP_READ_ERROR) + e.getMessage());
		}
		
		experimentPath = propertiesItem.getExperimentPath();
		this.properties = propertiesProvider.getPropertiesWithDefaults();
		validator = new ExperimentRunnerValidator(this.properties, propertiesItem);
	}
	
	/**
	 * Runs ruleRank experiment.
	 * It prepares rest of data, logs experiment parameters, runs experiment and save results.
	 */
	public void run() {
		if(not(validator.isValid())) {
			return;
		}
		LearningTable learningTable = validator.getLearningTable();
		LearningTable testTable = validator.getTestTable();
		initializeFileNames();
		Settings.getInstance().precision = properties.getPrecision();
		
		RankerParameters parameters =  new RankerParametersAssembler(properties, learningTable, testTable).getParameters();
		JRankLogger.info("Performing experiment with parameters:\n" + properties);
		
		Ranker ranker = new Ranker();
		RankerResults results = ranker.run(parameters);
		JRankLogger.info("Output from RuleRank:");
		for(String msg : ranker.getMessages()) {
			JRankLogger.none(msg);
		}
		
		new ResultsSaver(results, properties, experimentPath).save();
		WorkspaceController.getInstance().refresh();
	}
	
	/**
	 * If file names in properties were not configured, they will be replaced with default ones calculated from learning table name.
	 */
	private void initializeFileNames() {
		String baseName = Paths.get(properties.getLearningDataFile()).getFileName().toString();
		baseName = FileExtensionExtractor.getFileName(baseName);
		
		if(isNullOrEmpty(properties.getTestDataFile()))
			properties.setTestDataFile(properties.getLearningDataFile());
		
		if(isNullOrEmpty(properties.getPctFile()))
			properties.setPctFile(baseName + "_partialPCT.isf");
		
		if(isNullOrEmpty(properties.getPctApxFile()))
			properties.setPctApxFile(baseName + "_partialPCT.apx");
		
		if(isNullOrEmpty(properties.getPctRulesFile()))
			properties.setPctRulesFile(baseName + "_partialPCT.rules");
		
		if(isNullOrEmpty(properties.getRankingFile()))
			properties.setRankingFile(baseName + ".ranking");
		
		if(isNullOrEmpty(properties.getPreferenceGraphFile()))
			properties.setPreferenceGraphFile(baseName + ".graph");
	}
	
}
