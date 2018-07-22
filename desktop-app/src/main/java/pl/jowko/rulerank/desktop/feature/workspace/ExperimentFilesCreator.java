package pl.jowko.rulerank.desktop.feature.workspace;

import pl.jowko.rulerank.desktop.feature.properties.RuleRankProperties;
import pl.jowko.rulerank.desktop.feature.properties.PropertiesSaver;
import pl.jowko.rulerank.desktop.service.JRSFileMediator;
import pl.poznan.put.cs.idss.jrs.core.ContainerFailureException;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.IntegerField;

import java.io.IOException;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-06-09
 * This class helps to create new properties and isf files.
 */
public class ExperimentFilesCreator {
	
	/**
	 * Creates new .properties file.
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
		
		createMemoryContainer(dirPath, fileName);
	}
	
	/**
	 * Creates new example MemoryContainer(isf file).
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
