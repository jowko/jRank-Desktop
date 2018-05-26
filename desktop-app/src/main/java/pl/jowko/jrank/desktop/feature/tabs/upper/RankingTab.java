package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.exception.JRankRuntimeException;
import pl.jowko.jrank.desktop.feature.properties.JRankProperties;
import pl.jowko.jrank.desktop.feature.ranking.RankingController;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.RankingInitializationException;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.workspace.FileType;
import pl.jowko.jrank.desktop.feature.workspace.FilesFinder;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
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

/**
 * Created by Piotr on 2018-05-08.
 * Tab for ranking tab.
 * It loads .ranking file, .properties file and .isf file.
 * If experiment contains multiple .properties files, class throws error.
 * Properties file is used to get path to .isf file.
 * If properties doesn't contain isf file path, it is assumed, that isf file is in same directory as ranking file and have same name.
 * @see RankingController
 */
class RankingTab extends JRankTab {
	
	private String experimentDirectory;
	private String experimentName;
	
	/**
	 * Creates ranking tab for .ranking files.
	 * It will load fxml file and initialize tab with .ranking file content.
	 * It also loads MemoryContainer and properties file.
	 * @param workspaceItem from workspace tree
	 * @param tabName to display on tab header(tab text)
	 * @throws TabInitializationException when error occur on tab initialization
	 * @throws IOException when somethings goes wrong with file reading
	 */
	RankingTab(WorkspaceItem workspaceItem, String tabName) throws IOException, TabInitializationException {
		super(tabName);
		try {
			String rankingFileContent = JRSFileMediator.loadTextFile(workspaceItem);
			MemoryContainer container = getMemoryContainer(workspaceItem);
			
			if(isNull(container))
				throw new TabInitializationException("Learning data file was not found on provided path");
			
			RankingController controller = initializeTabAndGetController(workspaceItem);
			controller.initializeRanking(rankingFileContent, container);
			
		} catch (JRankRuntimeException e) {
			throwInitializationException("ranking", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/rankingTab.fxml";
	}
	
	/**
	 * Get memory container for experiment associated with provided .ranking file.
	 * It uses .properties file to find correct path for learning data file.
	 * @param rankingItem from workspace tree
	 * @return MemoryContainer with data needed to create ranking tab.
	 * @throws IOException when something goes wrong
	 */
	private MemoryContainer getMemoryContainer(WorkspaceItem rankingItem) throws IOException {
		Path experimentPath = Paths.get(rankingItem.getFilePath()).getParent();
		experimentDirectory = experimentPath.toString() + "\\";
		experimentName = experimentPath.getFileName().toString();
		List<WorkspaceItem> items = new FilesFinder().findAllFiles(experimentDirectory);
		
		WorkspaceItem propertiesItem = getExperimentProperties(items);
		JRankProperties properties = JRSFileMediator.loadProperties(propertiesItem);
		
		return getContainerFromProperties(properties, rankingItem);
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
			throw new RankingInitializationException("Experiment: [" + experimentName + "] has more than one properties file. Fix this issue before accessing ranking file.");
		
		if(properties.size() == 1)
			return properties.get(0);
		
		throw new RankingInitializationException("Experiment: [" + experimentName + "] does not have properties file. Fix this issue before accessing ranking file.");
	}
	
	/**
	 * Gets MemoryContainer using properties file.
	 * First, it checks in properties have configured isf file location.
	 * If isf file location is configured, it is checked if path is relative or absolute.
	 * Then full path for isf file is calculated and file is loaded.
	 * If properties doesn't have isf file configured, it is assumed that isf table is in same directory as ranking file.
	 * Also, isf file name is derrived from ranking file name.
	 * If ranking file had name: someExperiment.ranking, it is assumed that isf file have name: someExperiment.isf.
	 * @param properties contains experiment settings
	 * @param rankingItem from workspace tree
	 * @return MemoryContainer with learning data table
	 */
	private MemoryContainer getContainerFromProperties(JRankProperties properties, WorkspaceItem rankingItem) {
		
		if(nonNull(properties.getLearningDataFile()) && not(properties.getLearningDataFile().trim().isEmpty())) {
			Path path = Paths.get(properties.getLearningDataFile());
			if(path.isAbsolute()) {
				JRankLogger.info("Reading learning data table from: [" + properties.getLearningDataFile() + "] for ranking tab");
				return JRSFileMediator.loadMemoryContainer(properties.getLearningDataFile());
			} else {
				String isfPath = experimentDirectory + properties.getLearningDataFile();
				JRankLogger.info("Reading learning data table from: [" + isfPath + "] for ranking tab");
				return JRSFileMediator.loadMemoryContainer(isfPath);
			}
		}
		
		String isfPath = experimentDirectory + rankingItem.getFileName().replace(".ranking", ".isf");
		JRankLogger.info("Learning data table name was not configured in properties file. Assuming path: [" + isfPath + ']');
		
		return JRSFileMediator.loadMemoryContainer(isfPath);
	}
	
}
