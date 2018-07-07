package pl.jowko.jrank.desktop.feature.graph;

import com.fxgraph.graph.Cell;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;

/**
 * Created by Piotr on 2018-06-24
 * This controller controls information about graphNode in graph in graph tab.
 * It displays information about graphNode arcs.
 */
public class ArcsController {
	
	@FXML private Label outArcsLabel;
	@FXML private Label outSLabel;
	@FXML private Label outS;
	@FXML private Label outScLabel;
	@FXML private Label outSc;
	@FXML private Label inArcsLabel;
	@FXML private Label inSLabel;
	@FXML private Label inS;
	@FXML private Label inScLabel;
	@FXML private Label inSc;
	
	private JRankTab arcsTab;
	private Cell graphNode;
	
	void initializeArcs(Cell node, JRankTab arcsTab) {
		this.arcsTab = arcsTab;
		this.graphNode = node;
		
		translateLabels();
		initialize();
	}
	
	void initializeArcs(Cell node) {
		this.graphNode = node;
		initialize();
	}
	
	private void initialize() {
		arcsTab.setText("Node " + graphNode.getCellId());
	}
	
	private void translateLabels() {
	
	}
	
}
