package pl.jowko.jrank.desktop.feature.runner;

import pl.jowko.jrank.desktop.feature.learningtable.LearningTable;
import pl.jowko.jrank.desktop.feature.properties.DefaultPropertiesProvider;
import pl.jowko.jrank.desktop.feature.properties.JRankProperties;
import pl.jowko.jrank.desktop.feature.properties.RunnerPropertiesProvider;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.utils.FileExtensionExtractor;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.ranking.Ranker;
import pl.poznan.put.cs.idss.jrs.ranking.RankerParameters;
import pl.poznan.put.cs.idss.jrs.ranking.RankerResults;

import java.io.IOException;
import java.nio.file.Paths;

import static java.util.Objects.isNull;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;
import static pl.jowko.jrank.desktop.utils.StringUtils.isNullOrEmpty;

/**
 * Created by Piotr on 2018-06-04
 * This class is used to perform ruleRank experiment.
 * It prepares data, validates them, runs experiment and save results.
 */
public class ExperimentRunner {
	
	private JRankProperties properties;
	private ExperimentRunnerValidator validator;
	
	/**
	 * Creates instance of this class.
	 * Also prepares properties for later processing.
	 * @param properties from properties form
	 * @param propertiesItem representing properties file in workspace tree
	 */
	public ExperimentRunner(JRankProperties properties, WorkspaceItem propertiesItem) {
		RunnerPropertiesProvider propertiesProvider;
		
		try {
			JRankProperties defaults = new DefaultPropertiesProvider().getDefaultProperties();
			propertiesProvider = new RunnerPropertiesProvider(properties, defaults);
		} catch (IOException e) {
			throw new RunnerException("Error while reading default.properties file: " + e.getMessage());
		}
		
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
		
		RankerParameters parameters =  new RankerParametersAssembler(properties, learningTable, testTable).getParameters();
		JRankLogger.info("Performing experiment with parameters:\n" + properties);
		
		Ranker ranker = new Ranker();
		RankerResults results = ranker.run(parameters);
		JRankLogger.info("Output from RuleRank:");
		for(String msg : ranker.getMessages()) {
			JRankLogger.none(msg);
		}
		saveResults(results);
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
	
	private void saveResults(RankerResults results) {
		if(isNull(results))
			return;
		//TODO implement me
	}
	
}
