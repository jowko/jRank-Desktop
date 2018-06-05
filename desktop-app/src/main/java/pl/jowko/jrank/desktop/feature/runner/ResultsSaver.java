package pl.jowko.jrank.desktop.feature.runner;

import pl.jowko.jrank.desktop.feature.properties.JRankParameter;
import pl.jowko.jrank.desktop.feature.properties.JRankProperties;
import pl.jowko.jrank.desktop.service.JRSFileMediator;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.ranking.RankerResults;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-06-05
 * This class saves results from experiment.
 */
class ResultsSaver {
	
	private RankerResults results;
	private JRankProperties properties;
	private String experimentPath;
	
	/**
	 * @param results from ruleRank experiment
	 * @param properties from properties form with were used in experiment
	 * @param experimentPath to save all files
	 */
	ResultsSaver(RankerResults results, JRankProperties properties, String experimentPath) {
		this.results = results;
		this.properties = properties;
		this.experimentPath = experimentPath;
	}
	
	/**
	 * Saves all experiment files from RankerResults object.
	 * Files path and other options are extracted from JRankProperties.
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
			JRankLogger.error("Error when saving pct apx file: " + e);
		}
	}
	
	private void saveRulesFile() {
		try {
			String rulesPath = getAbsolutePath(properties.getPctRulesFile());
			boolean writeRulesStatistics = getBoolean(properties.getWriteRulesStatistics());
			boolean writeLearningExamples = getBoolean(properties.getWriteLearningPositiveExamples());
			results.rulesContainer.writeRules(rulesPath, writeRulesStatistics, writeLearningExamples);
			logFileSaved(properties.getPctRulesFile(), rulesPath);
		} catch (IOException e) {
			JRankLogger.error("Error when saving pct rules file: " + e);
		}
	}
	
	private void saveRankingFile() {
		try {
			String rankingPath = getAbsolutePath(properties.getRankingFile());
			results.ranking.writeRanking(rankingPath);
			logFileSaved(properties.getRankingFile(), rankingPath);
		} catch (IOException e) {
			JRankLogger.error("Error when saving ranking file: " + e);
		}
	}
	
	private void saveGraphFile() {
		try {
			String graphPath = getAbsolutePath(properties.getPreferenceGraphFile());
			results.preferenceGraph.writeDOTPreferenceGraph(graphPath);
			logFileSaved(properties.getPreferenceGraphFile(), graphPath);
		} catch (IOException e) {
			JRankLogger.error("Error when saving graph file: " + e);
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
		JRankLogger.info(fileName + " saved successfully in: " + absoluteFilePath);
	}
	
	private boolean getBoolean(JRankParameter parameter) {
		return parameter.getTextValue().equalsIgnoreCase("true");
	}
	
}
