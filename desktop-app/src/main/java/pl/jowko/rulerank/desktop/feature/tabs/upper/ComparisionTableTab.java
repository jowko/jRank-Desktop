package pl.jowko.rulerank.desktop.feature.tabs.upper;

import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;
import pl.jowko.rulerank.desktop.feature.pct.PCTController;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.jowko.rulerank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

import java.io.IOException;

/**
 * This tab displays partial pairwise comparison table from partialPCT.isf file. <br>
 * <br>
 * Created by Piotr on 2018-05-08.
 */
class ComparisionTableTab extends RuleRankTab {
	
	/**
	 * Creates partial pairwise comparison table tab for partialPCT.isf files. <br>
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
		} catch (RuleRankRuntimeException e) {
			throwInitializationException("pct isf", workspaceItem.getFileName(), e);
		}
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/upperTabs/comparisionTableTab.fxml";
	}
	
}
