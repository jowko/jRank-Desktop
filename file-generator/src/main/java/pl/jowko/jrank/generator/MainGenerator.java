package pl.jowko.jrank.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.jowko.jrank.desktop.feature.settings.ConfigPaths;
import pl.jowko.jrank.desktop.feature.settings.JRankInfo;
import pl.jowko.jrank.desktop.feature.settings.UserSettings;
import pl.jowko.jrank.logger.JRankLogger;

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
 * @see pl.jowko.jrank.desktop.feature.settings.UserSettingsController
 * @see UserSettings
 *
 * labels.json - contains map of maps, where languages with labels codes and translations are stored
 * @see pl.jowko.jrank.desktop.feature.internationalization.LanguageService
 * @see pl.jowko.jrank.desktop.feature.internationalization.Labels
 *
 * languages.json - contains map of language codes and language labels.
 * Example: "ENG" : "English"
 * It is used mostly in user settings form
 * @see pl.jowko.jrank.desktop.feature.internationalization.LanguageService
 * @see pl.jowko.jrank.desktop.feature.settings.UserSettingsController
 *
 * jRankInfo.json - contains information about application version displayed in about dialog from help settings
 * @see pl.jowko.jrank.desktop.controller.AboutController
 * @see JRankInfo
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
		JRankLogger.gen("Starting generating files for settings");
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
		JRankLogger.gen("Generating data directory: " + configPaths.getDataDirectory());
		Files.createDirectories(Paths.get(configPaths.getDataDirectory()));
		JRankLogger.gen("Data directory created successfully.");
		StubSettings stubSettings = new StubSettings(true);
		
		
		JRankLogger.gen("Generating UserSettings file: " + configPaths.getUserSettingsPath());
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(configPaths.getUserSettingsPath()), stubSettings.getSettings());
		
		
		JRankLogger.gen("Generating labels file: " + configPaths.getLabelsPath());
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(configPaths.getLabelsPath()), stubSettings.getLabels());
		
		
		JRankLogger.gen("Generating languages file: " + configPaths.getLanguagesPath());
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(configPaths.getLanguagesPath()), stubSettings.getLanguages());
		
		
		JRankLogger.gen("Generating jRank info file: " + configPaths.getJRankInfoPath());
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(configPaths.getJRankInfoPath()), stubSettings.getAppInfo());

		
		JRankLogger.gen("All files generated successfully.");
	}
	
}
