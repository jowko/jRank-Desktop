package pl.jowko.jrank.desktop.settings;

import pl.jowko.jrank.logger.JRankLogger;

/**
 * Created by Piotr on 2018-03-17.
 * Initializes all singleton objects before running application
 */
public class SingletonInitializer {
	
	private SingletonInitializer() {}
	
	public static void initialize() {
		JRankLogger.init("Initializing singletons.");
		
		FileManager.getInstance();
		LanguageService.getInstance();
		SettingsService.getInstance();
		LabelTranslator.getInstance();
		
		JRankLogger.init("Singletons init completed successfully.");
	}
	
}
