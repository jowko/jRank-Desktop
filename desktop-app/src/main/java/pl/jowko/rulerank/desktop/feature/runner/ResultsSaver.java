package pl.jowko.rulerank.desktop.feature.runner;

import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.properties.RuleRankParameter;
import pl.jowko.rulerank.desktop.feature.properties.RuleRankProperties;
import pl.jowko.rulerank.desktop.service.JRSFileMediator;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.ranking.RankerResults;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.utils.PathUtils.getAbsoluteExperimentFilePath;
import static pl.poznan.put.cs.idss.jrs.ranking.RankerParameters.EXHAUSTIVE_SET_OF_RULES;

/**
 * This class saves results from experiment. <br>
 *  <br>
 * Created by Piotr on 2018-06-05
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
	 * Saves all experiment files from RankerResults object. <br>
	 * Files path and other options are extracted from RuleRankProperties. <br>
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
	
	/**
	 * Saves rule file. <br>
	 * If exhaustive set of rules were chosen, .no-rules file with be generated instead of .rules. <br>
	 * Also, old .rules and .no-rules file will be deleted.
	 */
	private void saveRulesFile() {
		String rulesPath = getAbsolutePath(properties.getPctRulesFile());
		
		if(EXHAUSTIVE_SET_OF_RULES == properties.getConsideredSetOfRules().getValue()) {
			handleNoRulesCase(rulesPath, properties.getPctRulesFile());
			return;
		}
		
		removeNoRulesFile(rulesPath);
		
		try {
			boolean writeRulesStatistics = getBoolean(properties.getWriteRulesStatistics());
			boolean writeLearningExamples = getBoolean(properties.getWriteLearningPositiveExamples());
			results.rulesContainer.writeRules(rulesPath, writeRulesStatistics, writeLearningExamples);
			logFileSaved(properties.getPctRulesFile(), rulesPath);
		} catch (IOException e) {
			RuleRankLogger.error("Error when saving pct rules file: " + e);
		}
	}
	
	/**
	 * This method handles situation, when exhaustive set was chosen. <br>
	 * .rule file will be deleted if exists and .no-rules file will be generated.
	 * @param filePath with represents absolute path to file
	 * @param relativePath with is relative path to file
	 */
	private void handleNoRulesCase(String filePath, String relativePath) {
		try {
			RuleRankLogger.info("No rules generated. Rules file not created. Removing old rules files.");
			Path rulesPath = Paths.get(filePath);
			if(Files.exists(rulesPath)) {
				Files.delete(rulesPath);
			}
			
			String noRulesPath = filePath.replace(".rules", ".no-rules");
			try (PrintWriter out = new PrintWriter(noRulesPath)) {
				out.println("[FILEINFO]");
				out.println(LanguageService.getInstance().get(Labels.RUN_NO_RULES));
			}
			logFileSaved(relativePath.replace(".rules", ".no-rules"), filePath);
			
		} catch (IOException e) {
			RuleRankLogger.error("Error when deleting old rules file: " + e.getMessage());
		}
	}
	
	/**
	 * Removes no-rules file if exists
	 * @param rulesPath with is absolute path to .rules file
	 */
	private void removeNoRulesFile(String rulesPath) {
		try {
			String noRules = rulesPath.replace(".rules", ".no-rules");
			Path noRulesPath = Paths.get(noRules);
			if(Files.exists(noRulesPath)) {
				Files.delete(noRulesPath);
			}
		} catch (IOException e) {
			RuleRankLogger.error("Error when deleting old no-rules file: " + e.getMessage());
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
		return getAbsoluteExperimentFilePath(experimentPath, filePath);
	}
	
	private void logFileSaved(String fileName, String absoluteFilePath) {
		RuleRankLogger.info(fileName + " saved successfully in: " + absoluteFilePath);
	}
	
	private boolean getBoolean(RuleRankParameter parameter) {
		return parameter.getTextValue().equalsIgnoreCase("true");
	}
	
}
