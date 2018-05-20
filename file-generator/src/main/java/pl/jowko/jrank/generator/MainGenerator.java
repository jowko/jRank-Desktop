package pl.jowko.jrank.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.jowko.jrank.desktop.feature.settings.ConfigPaths;
import pl.jowko.jrank.desktop.feature.settings.JRankInfo;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(configPaths.getJRankInfoPath()), new JRankInfo("0.3-SNAPSHOT", format.format(new Date())));

		
		JRankLogger.gen("All files generated successfully.");
	}
	
}
