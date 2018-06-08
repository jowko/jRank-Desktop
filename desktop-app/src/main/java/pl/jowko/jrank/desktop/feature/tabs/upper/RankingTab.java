package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.exception.JRankRuntimeException;
import pl.jowko.jrank.desktop.feature.ranking.RankingController;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.workspace.IsfFinder;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.JRSFileMediator;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

import java.io.IOException;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-05-08.
 * Tab for ranking tab.
 * It loads .ranking file, .properties file and .isf file.
 * If experiment contains multiple .properties files, class throws error.
 * Properties file is used to get path to .isf file.
 * If properties doesn't contain isf file path, it is assumed, that isf file is in same directory as ranking file and have same name.
 * @see RankingController
 * @see IsfFinder
 */
class RankingTab extends JRankTab {
	
	
	
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
			MemoryContainer container = new IsfFinder(workspaceItem, true).getMemoryContainer();
			
			if(isNull(container))
				throw new TabInitializationException("Isf data file was not found on provided path");
			
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
	
	
}
