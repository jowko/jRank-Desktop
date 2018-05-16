package pl.jowko.jrank.desktop.service;

import pl.jowko.jrank.desktop.feature.properties.JRankProperties;
import pl.jowko.jrank.desktop.feature.properties.PropertiesAssembler;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.utilities.ISFLoader;
import pl.poznan.put.cs.idss.jrs.utilities.ISFWriter;

import java.io.FileInputStream;
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
	 * Loads properties from .properties files. As a result return JRankProperties object with filled values.
	 * @param item from workspace tree, file path is read from this object
	 * @return JRankProperties filled with properties values
	 */
	public static JRankProperties loadProperties(WorkspaceItem item) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(item.getFilePath()));
			PropertiesAssembler propertiesAssembler = new PropertiesAssembler(properties);
			return propertiesAssembler.toJrankProperties();
		} catch (IOException e) {
			JRankLogger.error("Error when reading file: " + item.getFileName(), e);
		}
		return null;
	}
	
	/**
	 * Loads MemoryContainer using IFSLoader from jRS library
	 * @param item from workspace tree, file path is read from this object
	 * @return MemoryContainer loaded from isf file
	 */
	public static MemoryContainer loadMemoryContainer(WorkspaceItem item) {
		return ISFLoader.loadISFIntoMemoryContainer(item.getFilePath());
	}
	
	/**
	 * Saves MemoryContainer using ISFWriter from jRS library
	 * @param item from workspace tree, file path is read from this object
	 * @param container with data to save into isf file
	 */
	public static void saveMemoryContainer(WorkspaceItem item, MemoryContainer container) {
		ISFWriter.saveMemoryContainerIntoISF(item.getFilePath(), container);
		JRankLogger.info(item.getFileName() + " saved successfully in: " + item.getFilePath());
	}
	
}
