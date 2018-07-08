package pl.jowko.jrank.desktop.feature.graph;

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
	private NodeArcs arcs;
	
	void initializeArcs(NodeArcs arcs, JRankTab arcsTab) {
		this.arcsTab = arcsTab;
		this.arcs = arcs;
		
		translateLabels();
		initialize();
	}
	
	void initializeArcs(NodeArcs arcs) {
		this.arcs = arcs;
		initialize();
	}
	
	private void initialize() {
		arcsTab.setText("Node " + arcs.getNodeId());
		outS.setText(arcs.getOutS());
		outSc.setText(arcs.getOutSc());
		inS.setText(arcs.getInS());
		inSc.setText(arcs.getInSc());
	}
	
	private void translateLabels() {
	
	}
	
}
