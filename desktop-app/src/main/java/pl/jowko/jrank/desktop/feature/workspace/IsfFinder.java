package pl.jowko.jrank.desktop.feature.workspace;

import pl.jowko.jrank.desktop.exception.JRankRuntimeException;
import pl.jowko.jrank.desktop.feature.properties.JRankProperties;
import pl.jowko.jrank.desktop.service.JRSFileMediator;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;
import static pl.jowko.jrank.desktop.utils.FileExtensionExtractor.getExtension;

/**
 * Created by Piotr on 2018-06-08
 * This class is used to load isf file using workspaceItem.
 * It will try to read isf file name from properties or guess its name using workspaceItem file name.
 */
public class IsfFinder {
	
	private WorkspaceItem workspaceItem;
	private boolean isTestFileIncluded;
	private String experimentDirectory;
	private String experimentName;
	
	/**
	 * Initializes instance of this class.
	 * @param workspaceItem with will be used to find associated with it isf file
	 * @param isTestFileIncluded determines, if test isf file should be included in search.
	 *                           If it is included, in case when test file is configured, test file is returned instead of learning table
	 */
	public IsfFinder(WorkspaceItem workspaceItem, boolean isTestFileIncluded) {
		this.workspaceItem = workspaceItem;
		this.isTestFileIncluded = isTestFileIncluded;
		
		Path experimentPath = Paths.get(workspaceItem.getFilePath()).getParent();
		experimentDirectory = experimentPath.toString() + "\\";
		experimentName = experimentPath.getFileName().toString();
	}
	
	/**
	 * Get memory container for experiment associated with provided workspaceItem.
	 * It uses .properties file to find correct path for learning data file.
	 * @return MemoryContainer
	 * @throws IOException when something goes wrong
	 */
	public MemoryContainer getMemoryContainer() throws IOException {
		List<WorkspaceItem> items = new FilesFinder().findAllFiles(experimentDirectory);
		
		WorkspaceItem propertiesItem = getExperimentProperties(items);
		JRankProperties properties = JRSFileMediator.loadProperties(propertiesItem);
		
		return getContainerFromProperties(properties);
	}
	
	/**
	 * Gets .properties item for current experiment.
	 * If in experiment directory is more than one properties file, or there is no such file, exception is thrown.
	 * @param items from current experiment
	 * @return properties item containing properties path
	 */
	private WorkspaceItem getExperimentProperties(List<WorkspaceItem> items) {
		List<WorkspaceItem> properties = items.stream().filter(item -> FileType.JRANK_SETTINGS.equals(item.getFileType()))
				.collect(Collectors.toList());
		
		if(properties.size() > 1)
			throw new JRankRuntimeException("Experiment: [" + experimentName + "] has more than one properties file. Fix this issue before accessing this file.");
		
		if(properties.size() == 1)
			return properties.get(0);
		
		throw new JRankRuntimeException("Experiment: [" + experimentName + "] does not have properties file. Fix this issue before accessing this file.");
	}
	
	/**
	 * Gets MemoryContainer using properties file.
	 * Then full path for isf file is calculated and file is loaded.
	 * If properties doesn't have isf file configured, it is assumed that isf table is in same directory as file represented by workspaceItem.
	 * Also is assumed that isf file name is same as workspaceItem file name.
	 * If workspaceItem file had name: someExperiment.ranking, it is assumed that isf file have name: someExperiment.isf.
	 * @param properties contains experiment settings
	 * @return MemoryContainer with learning data table
	 */
	private MemoryContainer getContainerFromProperties(JRankProperties properties) {
		String dataFilePath = getPathToIsfFile(properties);
		
		if(nonNull(dataFilePath)) {
			JRankLogger.info("Reading learning data table from: [" + dataFilePath + "] for tab");
			return JRSFileMediator.loadMemoryContainer(dataFilePath);
		}
		
		String extension = getExtension(workspaceItem.getFileName());
		String isfPath = experimentDirectory + workspaceItem.getFileName().replace(extension, "isf");
		JRankLogger.info("Learning data table name was not configured in properties file. Assuming path: [" + isfPath + ']');
		
		return JRSFileMediator.loadMemoryContainer(isfPath);
	}
	
	/**
	 * Get path to isf file.
	 * It checks in properties have configured isf file location.
	 * At first, test data file configuration is checked.
	 * It it is not configured, learning data file configuration is checked.
	 * If none of this paths are configured, null is returned.
	 * If isf file location is configured, it is checked if path is relative or absolute.
	 */
	private String getPathToIsfFile(JRankProperties properties) {
		String dataFilePath = null;
		if(isTestFileIncluded && nonNull(properties.getTestDataFile())  && not(properties.getTestDataFile().trim().isEmpty())) {
			dataFilePath = properties.getTestDataFile();
		} else if(nonNull(properties.getLearningDataFile()) && not(properties.getLearningDataFile().trim().isEmpty())) {
			dataFilePath = properties.getLearningDataFile();
		}
		
		if(isNull(dataFilePath))
			return null;
		
		Path path = Paths.get(dataFilePath);
		if(path.isAbsolute())
			return dataFilePath;
		
		// path is relative
		return experimentDirectory + dataFilePath;
	}
	
}
