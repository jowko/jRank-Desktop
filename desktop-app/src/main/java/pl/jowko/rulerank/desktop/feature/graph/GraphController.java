package pl.jowko.rulerank.desktop.feature.graph;

import com.fxgraph.graph.Graph;
import com.fxgraph.layout.CircularLayout;
import com.fxgraph.layout.Layout;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.jowko.rulerank.desktop.feature.tabs.lower.LowerTabsController;
import pl.jowko.rulerank.desktop.feature.tabs.upper.UpperTabsController;
import pl.jowko.rulerank.desktop.feature.workspace.IsfFinder;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

import java.io.IOException;

import static java.util.Objects.isNull;

/**
 * Controller for graph visualisation tab. <br>
 * It will extract data from .graph and .isf file and display it in tab. <br>
 *  <br>
 * Created by Piotr on 2018-06-06
 */
public class GraphController {
	
	@FXML
	private BorderPane borderPane;
	
	private RuleRankTab graphTab;
	private Graph graph;
	
	/**
	 * Initialize graph tab. <br>
	 * It will extract nodes and edges from graphFileContent and visualise it on tab.
	 * @param graphFileContent from .graph file
	 * @param workspaceItem representing .graph file
	 * @param graphTab representing tab for graph
	 */
	public void initializeGraph(String graphFileContent, WorkspaceItem workspaceItem, RuleRankTab graphTab) {
		this.graphTab = graphTab;
		GraphSelectAction selectAction = new GraphSelectAction(this);
		
		MemoryContainer container = loadMemoryContainer(workspaceItem);
		graph = new GraphReader(graphFileContent, container, selectAction).getGraph();
		
		borderPane.setCenter(graph.getScrollPane());
		
		Layout layout = new CircularLayout(graph);
		layout.execute();
	}
	
	Graph getGraph() {
		return graph;
	}
	
	/**
	 * Initializes close event for graph tab. <br>
	 * If graph tab is closed, arcs tab is also automatically closed. <br>
	 * Force close is called for graphTab, because firing onClosed event will cancel close of graphTab.
	 * @param arcsTab with represents rule rank tab containing arcs information
	 * @see UpperTabsController
	 */
	void initializeCloseEventForGraphTab(RuleRankTab arcsTab) {
		graphTab.setOnClosed(event -> {
			LowerTabsController.getInstance().closeTab(arcsTab);
			UpperTabsController.getInstance().forceCloseTab(graphTab);
		});
	}
	
	private MemoryContainer loadMemoryContainer(WorkspaceItem workspaceItem) {
		try {
			MemoryContainer container = new IsfFinder(workspaceItem, true).getMemoryContainer();
			if(isNull(container))
				throw new RuleRankRuntimeException("Isf data file was not found on provided path");
			
			return container;
		} catch (IOException e) {
			throw new RuleRankRuntimeException("Error when reading isf file for graph: " + e.getMessage());
		}
	}
	
}
