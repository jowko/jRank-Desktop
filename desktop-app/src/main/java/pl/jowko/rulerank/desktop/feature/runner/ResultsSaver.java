package pl.jowko.rulerank.desktop.feature.runner;

import pl.jowko.rulerank.desktop.feature.properties.RuleRankParameter;
import pl.jowko.rulerank.desktop.feature.properties.RuleRankProperties;
import pl.jowko.rulerank.desktop.service.JRSFileMediator;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.ranking.RankerResults;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-06-05
 * This class saves results from experiment.
 */
class ResultsSaver {
	
	private RankerResults results;
	private RuleRankProperties properties;
	private String experimentPath;
	
	/**
	 * @param results from ruleRank experiment
	 * @param properties from properties form with were used in experiment
	 * @param experimentPath to save all files
	 */
	ResultsSaver(RankerResults results, RuleRankProperties properties, String experimentPath) {
		this.results = results;
		this.properties = properties;
		this.experimentPath = experimentPath;
	}
	
	/**
	 * Saves all experiment files from RankerResults object.
	 * Files path and other options are extracted from RuleRankProperties.
	 * All files will be saved in experimentPath.
	 */
	void save() {
		if(isNull(results))
			return;
		
		savePctFile();
		saveApxFile();
		saveRulesFile();
		saveRankingFile();
		saveGraphFile();
		saveReportFile();
	}
	
	private void savePctFile() {
		String pctFilePath = getAbsolutePath(properties.getPctFile());
		JRSFileMediator.saveMemoryContainer(pctFilePath, properties.getPctFile(), results.learningPct);
	}
	
	private void saveApxFile() {
		try {
			String pctApxPath = getAbsolutePath(properties.getPctApxFile());
			boolean writeDominationInformation = getBoolean(properties.getWriteDominationInformation());
			results.unionContainer.writeApproximations(pctApxPath, properties.getConsistencyMeasureThreshold(), writeDominationInformation, null);
			logFileSaved(properties.getPctApxFile(), pctApxPath);
		} catch (IOException e) {
			RuleRankLogger.error("Error when saving pct apx file: " + e);
		}
	}
	
	private void saveRulesFile() {
		String rulesPath = getAbsolutePath(properties.getPctRulesFile());
		
		if(isNull(results.rulesContainer)) {
			handleNoRulesCase(rulesPath);
			return;
		}
		
		try {
			boolean writeRulesStatistics = getBoolean(properties.getWriteRulesStatistics());
			boolean writeLearningExamples = getBoolean(properties.getWriteLearningPositiveExamples());
			results.rulesContainer.writeRules(rulesPath, writeRulesStatistics, writeLearningExamples);
			logFileSaved(properties.getPctRulesFile(), rulesPath);
		} catch (IOException e) {
			RuleRankLogger.error("Error when saving pct rules file: " + e);
		}
	}
	
	private void handleNoRulesCase(String rulesPath) {
		try {
			RuleRankLogger.info("No rules generated. Rules file not created.");
			Path path = Paths.get(rulesPath);
			if(Files.exists(path)) {
				Files.delete(path);
			}
		} catch (IOException e) {
			RuleRankLogger.error("Error when deleting old rules file: " + e.getMessage());
		}
	}
	
	private void saveRankingFile() {
		try {
			String rankingPath = getAbsolutePath(properties.getRankingFile());
			results.ranking.writeRanking(rankingPath);
			logFileSaved(properties.getRankingFile(), rankingPath);
		} catch (IOException e) {
			RuleRankLogger.error("Error when saving ranking file: " + e);
		}
	}
	
	private void saveGraphFile() {
		try {
			String graphPath = getAbsolutePath(properties.getPreferenceGraphFile());
			results.preferenceGraph.writeDOTPreferenceGraph(graphPath);
			logFileSaved(properties.getPreferenceGraphFile(), graphPath);
		} catch (IOException e) {
			RuleRankLogger.error("Error when saving graph file: " + e);
		}
	}
	
	private void saveReportFile() {
		try {
			String reportFilePath = getAbsolutePath(properties.getReportFile());
			results.writeErrors(reportFilePath);
			logFileSaved(properties.getReportFile(), reportFilePath);
		} catch (IOException e) {
			RuleRankLogger.error("Error while saving report file: " + e);
		}
	}
	
	private String getAbsolutePath(String filePath) {
		Path path = Paths.get(filePath);
		if(path.isAbsolute()) {
			return path.toString();
		} else {
			return experimentPath + path.toString();
		}
	}
	
	private void logFileSaved(String fileName, String absoluteFilePath) {
		RuleRankLogger.info(fileName + " saved successfully in: " + absoluteFilePath);
	}
	
	private boolean getBoolean(RuleRankParameter parameter) {
		return parameter.getTextValue().equalsIgnoreCase("true");
	}
	
}
