package pl.jowko.rulerank.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.jowko.rulerank.desktop.feature.settings.ConfigPaths;
import pl.jowko.rulerank.desktop.feature.settings.RuleRankInfo;
import pl.jowko.rulerank.desktop.feature.settings.UserSettings;
import pl.jowko.rulerank.logger.RuleRankLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Piotr on 2018-03-16.
 * Used to generate settings files for desktop application. Only used for development.
 *
 * It generates 4 configuration files:
 *
 * userSettings.json - contains user settings, with are editable in user settings dialog form from settings menu
 * @see pl.jowko.rulerank.desktop.feature.settings.UserSettingsController
 * @see UserSettings
 *
 * labels.json - contains map of maps, where languages with labels codes and translations are stored
 * @see pl.jowko.rulerank.desktop.feature.internationalization.LanguageService
 * @see pl.jowko.rulerank.desktop.feature.internationalization.Labels
 *
 * languages.json - contains map of language codes and language labels.
 * Example: "ENG" : "English"
 * It is used mostly in user settings form
 * @see pl.jowko.rulerank.desktop.feature.internationalization.LanguageService
 * @see pl.jowko.rulerank.desktop.feature.settings.UserSettingsController
 *
 * ruleRankInfo.json - contains information about application version displayed in about dialog from help settings
 * @see pl.jowko.rulerank.desktop.controller.AboutController
 * @see RuleRankInfo
 *
 * Default data are taken from StubSettings class.
 * @see StubSettings
 */
public class MainGenerator {
	
	private ObjectMapper mapper;
	
	/**
	 * Main method of this application.
	 * It starts configuration files generation process.
	 * @param args are not used
	 * @throws IOException when something goes wrong with saving files
	 */
	public static void main(String[] args) throws IOException {
		RuleRankLogger.gen("Starting generating files for settings");
		MainGenerator generator = new MainGenerator();
		generator.generateSettingsFile();
	}
	
	private MainGenerator() {
		mapper = new ObjectMapper();
	}
	
	/**
	 * Generates configuration files for ruleRank application.
	 * More details are in this class JavaDoc header.
	 * Saved data are taken from StubSettings class where default settings are stored.
	 * @see StubSettings
	 * @throws IOException when something goes wrong with saving files
	 */
	private void generateSettingsFile() throws IOException {
		ConfigPaths configPaths = ConfigPaths.getInstance();
		RuleRankLogger.gen("Generating data directory: " + configPaths.getDataDirectory());
		Files.createDirectories(Paths.get(configPaths.getDataDirectory()));
		RuleRankLogger.gen("Data directory created successfully.");
		StubSettings stubSettings = new StubSettings(true);
		
		
		RuleRankLogger.gen("Generating UserSettings file: " + configPaths.getUserSettingsPath());
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(configPaths.getUserSettingsPath()), stubSettings.getSettings());
		
		
		RuleRankLogger.gen("Generating labels file: " + configPaths.getLabelsPath());
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(configPaths.getLabelsPath()), stubSettings.getLabels());
		
		
		RuleRankLogger.gen("Generating languages file: " + configPaths.getLanguagesPath());
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(configPaths.getLanguagesPath()), stubSettings.getLanguages());
		
		
		RuleRankLogger.gen("Generating RuleRank info file: " + configPaths.getRuleRankInfoPath());
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(configPaths.getRuleRankInfoPath()), stubSettings.getAppInfo());

		
		RuleRankLogger.gen("All files generated successfully.");
	}
	
}
