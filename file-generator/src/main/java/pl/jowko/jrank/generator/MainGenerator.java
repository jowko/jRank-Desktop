package pl.jowko.jrank.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.jowko.jrank.desktop.settings.JRankInfo;
import pl.jowko.jrank.desktop.settings.JRankSettings;
import pl.jowko.jrank.desktop.settings.UserSettings;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static pl.jowko.jrank.desktop.settings.JRankConst.*;

/**
 * Created by Piotr on 2018-03-16.
 * Used to generate settings files for desktop application. Only used for development.
 */
public class MainGenerator {
	
	private ObjectMapper mapper;
	
	public static void main(String[] args) throws IOException {
		JRankLogger.gen("Starting generating files for settings");
		MainGenerator generator = new MainGenerator();
		generator.generateSettingsFile();
	}
	
	private MainGenerator() {
		mapper = new ObjectMapper();
	}
	
	private void generateSettingsFile() throws IOException {
		JRankLogger.gen("Generating data directory: " + DATA_DIRECTORY);
		Files.createDirectories(Paths.get(DATA_DIRECTORY));
		JRankLogger.gen("Data directory created successfully.");
		
		JRankLogger.gen("Generating UserSettings file: " + USER_SETTING_FILE);
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(USER_SETTING_FILE), new UserSettings());
		
		JRankLogger.gen("Generating labels file: " + LABELS_FILE);
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(LABELS_FILE), new StubSettings().getLabels());
		
		JRankLogger.gen("Generating languages file: " + LANGUAGES_FILE);
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(LANGUAGES_FILE), new StubSettings().getLanguages());
		
		JRankLogger.gen("Generating jRank default settings file: " + JRANK_SETTINGS_FILE);
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(JRANK_SETTINGS_FILE), new JRankSettings());
		
		JRankLogger.gen("Generating jRank info file");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(JRANK_INFO_FILE), new JRankInfo("0.1-SNAPSHOT", format.format(new Date())));

		JRankLogger.gen("All files generated successfully.");
	}
	
}
