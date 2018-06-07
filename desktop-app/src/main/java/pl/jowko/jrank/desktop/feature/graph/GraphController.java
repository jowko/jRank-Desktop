package pl.jowko.jrank.desktop.feature.graph;

import com.fxgraph.graph.Graph;
import com.fxgraph.layout.CircularLayout;
import com.fxgraph.layout.Layout;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;

/**
 * Created by Piotr on 2018-06-06
 * Controller for graph visualisation tab.
 * It will extract data from .graph and .isf file and display it in tab.
 */
public class GraphController {
	
	@FXML
	private BorderPane borderPane;
	
	private Graph graph;
	private WorkspaceItem workspaceItem;
	private JRankTab graphTab;
	
	/**
	 * Initialize graph tab.
	 * It will extract nodes and edges from graphFileContent and visualise it on tab.
	 * @param graphFileContent from .graph file
	 * @param workspaceItem representing .graph file
	 * @param graphTab representing tab for graph
	 */
	public void initializeGraph(String graphFileContent, WorkspaceItem workspaceItem, JRankTab graphTab) {
		this.workspaceItem = workspaceItem;
		this.graphTab = graphTab;
		
		graph = new GraphReader(graphFileContent).getGraph();
		
		borderPane.setCenter(graph.getScrollPane());
		
		Layout layout = new CircularLayout(graph);
		layout.execute();
	}
	
}
