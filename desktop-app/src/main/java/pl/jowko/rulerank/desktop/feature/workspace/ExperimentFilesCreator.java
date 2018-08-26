package pl.jowko.rulerank.desktop.feature.workspace;

import pl.jowko.rulerank.desktop.feature.properties.PropertiesSaver;
import pl.jowko.rulerank.desktop.feature.properties.RuleRankProperties;
import pl.jowko.rulerank.desktop.service.JRSFileMediator;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.core.ContainerFailureException;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.IntegerField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * This class helps to create new properties and isf files. <br>
 *  <br>
 * Created by Piotr on 2018-06-09
 */
public class ExperimentFilesCreator {
	
	private ExperimentFilesCreator() {}
	
	/**
	 * Creates new .properties file. <br>
	 * Properties will be saved in provided directory and learning data table will be configured in them.
	 * @param dirPath to save properties
	 * @param isfTableName with will be used to configure properties
	 * @throws IOException when something goes wrong with file saving
	 */
	public static void createPropertiesFile(String dirPath, String isfTableName) throws IOException {
		RuleRankProperties properties = new RuleRankProperties();
		properties.setLearningDataFile(isfTableName);
		new PropertiesSaver(properties).save(dirPath + "experiment.properties");
	}
	
	/**
	 * Creates empty properties file on provided path.
	 * @param filePath on with properties will be created.
	 * @throws IOException when something goes wrong with file saving
	 */
	static void createPropertiesFile(String filePath) throws IOException {
		if(not(filePath.endsWith(".properties")))
			filePath += ".properties";
		
		Path path = Paths.get(filePath);
		if(Files.exists(path)) {
			RuleRankLogger.warn("File on path: [" + path + "] already exists. Aborting save properties file action");
			return;
		}
		
		new PropertiesSaver(new RuleRankProperties()).save(filePath);
	}
	
	/**
	 * Creates new example MemoryContainer(isf file).
	 * @param dirPath to save MemoryContainer
	 * @param fileName for isf file
	 * @throws ContainerFailureException because setAttributes throws it
	 */
	static void createMemoryContainerForWorkspace(String dirPath, String fileName) throws ContainerFailureException {
		if(fileName.endsWith(".isf"))
			fileName = fileName.substring(0, fileName.length() - 4);
		
		Path path = Paths.get(dirPath + fileName + ".isf");
		if(Files.exists(path)) {
			RuleRankLogger.warn("File on path: [" + path + "] already exists. Aborting save isf file action");
			return;
		}
		
		createMemoryContainer(dirPath, fileName);
	}
	
	/**
	 * Creates new empty text file with .txt extension
	 * @param dirPath to save text file
	 * @param fileName for text file name
	 * @throws IOException because setAttributes throws it
	 */
	static void createTextFileForWorkspace(String dirPath, String fileName) throws IOException {
		if(!fileName.endsWith(".txt"))
			fileName += ".txt";
		
		Path path = Paths.get(dirPath + fileName);
		if(Files.exists(path)) {
			RuleRankLogger.warn("File on path: [" + path + "] already exists. Aborting save text file action");
			return;
		}
		
		Files.write(path, new byte[0]);
	}
	
	/**
	 * Creates new example MemoryContainer(isf file). <br>
	 * Created container will have one example with one attribute, because it cannot be empty.
	 * @param dirPath to save MemoryContainer
	 * @param isfTableName on with container will be saved
	 * @throws ContainerFailureException because setAttributes throws it
	 */
	public static void createMemoryContainer(String dirPath, String isfTableName) throws ContainerFailureException {
		MemoryContainer container = new MemoryContainer();
		
		Attribute[] attributes = new Attribute[1];
		IntegerField field = new IntegerField();
		field.setUnknown();
		attributes[0] = new Attribute("Attribute_1", field);
		
		container.setAttributes(attributes);
		container.addExample();
		
		String filePath = dirPath + isfTableName + ".isf";
		JRSFileMediator.saveMemoryContainer(filePath, isfTableName + ".isf", container);
	}
	
}
