package pl.jowko.rulerank.desktop.service;

import pl.jowko.rulerank.desktop.feature.properties.RuleRankProperties;
import pl.jowko.rulerank.desktop.feature.properties.PropertiesAssembler;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.rules.RulesContainer;
import pl.poznan.put.cs.idss.jrs.utilities.ISFLoader;
import pl.poznan.put.cs.idss.jrs.utilities.ISFWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by Piotr on 2018-05-16.
 * This class serves as mediator between jRS files and application.
 * It loads and save files used in java Rough Set library.
 */
public class JRSFileMediator {
	
	private JRSFileMediator() {}
	
	/**
	 * Loads simple txt file as String
	 * @param item from workspace tree, file path is read from this object
	 * @return String containing file content
	 * @throws IOException when something go wrong
	 */
	public static String loadTextFile(WorkspaceItem item) throws IOException {
		return new String(Files.readAllBytes(Paths.get(item.getFilePath())));
	}
	
	/**
	 * Loads properties from .properties files. As a result return RuleRankProperties object with filled values.
	 * @param item from workspace tree, file path is read from this object
	 * @return RuleRankProperties filled with properties values
	 */
	public static RuleRankProperties loadProperties(WorkspaceItem item) throws IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(item.getFilePath()));
		PropertiesAssembler propertiesAssembler = new PropertiesAssembler(properties);
		return propertiesAssembler.toJrankProperties();
	}
	
	/**
	 * Loads MemoryContainer using IFSLoader from jRS library
	 * @param isfPath read from workspace tree
	 * @return MemoryContainer loaded from isf file
	 */
	public static MemoryContainer loadMemoryContainer(String isfPath) {
		return ISFLoader.loadISFIntoMemoryContainer(isfPath);
	}
	
	/**
	 * Saves MemoryContainer using ISFWriter from jRS library
	 * @param filePath with file name where container will be saved
	 * @param fileName used in logging
	 * @param container with data to save into isf file
	 */
	public static void saveMemoryContainer(String filePath, String fileName, MemoryContainer container) {
		ISFWriter.saveMemoryContainerIntoISF(filePath, container);
		RuleRankLogger.info(fileName + " saved successfully in: " + filePath);
	}
	
	/**
	 * Loads RulesContainer from .rules file using jRS library
	 * @param item from workspace tree, file path is read from this object
	 * @return RulesContainer containing rules with its statistics
	 * @throws FileNotFoundException when file is not found
	 */
	public static RulesContainer loadRules(WorkspaceItem item) throws FileNotFoundException {
		return RulesContainer.loadRules(item.getFilePath());
	}
	
}
