package pl.jowko.jrank.desktop.feature.tabs.upper;

import pl.jowko.jrank.desktop.exception.JRankRuntimeException;
import pl.jowko.jrank.desktop.feature.pct.PCTController;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

import java.io.IOException;

/**
 * Created by Piotr on 2018-05-08.
 * This tab displays partial comparision table from partialPCT.isf file.
 */
class ComparisionTableTab extends JRankTab {
	
	/**
	 * Creates partial comparision table tab for partialPCT.isf files.
	 * It will load fxml file and initialize tab with .partialPCT.isf file content.
	 * @param container with isf table of PCT type
	 * @param workspaceItem from workspace tree
	 * @param tabName to display on tab header(tab text)
	 * @throws TabInitializationException when error occur on tab initialization
	 * @throws IOException when somethings goes wrong with file reading
	 */
	ComparisionTableTab(MemoryContainer container, WorkspaceItem workspaceItem, String tabName) throws TabInitializationException, IOException {
		super(tabName);
		try {
			PCTController controller = initializeTabAndGetController(workspaceItem);
			controller.initializeRanking(container);
		} catch (JRankRuntimeException e) {
			throwInitializationException("pct isf", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/comparisionTableTab.fxml";
	}
	
}
