package pl.jowko.jrank.desktop.feature.workspace;

import pl.jowko.jrank.desktop.feature.properties.JRankProperties;
import pl.jowko.jrank.desktop.feature.properties.PropertiesSaver;
import pl.jowko.jrank.desktop.service.JRSFileMediator;
import pl.poznan.put.cs.idss.jrs.core.ContainerFailureException;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.IntegerField;

import java.io.IOException;

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
		JRankProperties properties = new JRankProperties();
		properties.setLearningDataFile(isfTableName);
		new PropertiesSaver(properties).save(dirPath + "experiment.properties");
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
		attributes[0] = new Attribute("Example", field);
		
		container.setAttributes(attributes);
		container.addExample();
		
		String filePath = dirPath + isfTableName + ".isf";
		JRSFileMediator.saveMemoryContainer(filePath, isfTableName + ".isf", container);
	}
	
}
