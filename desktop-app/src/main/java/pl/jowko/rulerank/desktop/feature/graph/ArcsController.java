package pl.jowko.rulerank.desktop.feature.graph;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;

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
	
	private RuleRankTab arcsTab;
	private NodeArcs arcs;
	
	private LanguageService labels;
	
	void initializeArcs(NodeArcs arcs, RuleRankTab arcsTab) {
		this.arcsTab = arcsTab;
		this.arcs = arcs;
		
		labels = LanguageService.getInstance();
		translateLabels();
		initialize();
	}
	
	void initializeArcs(NodeArcs arcs) {
		this.arcs = arcs;
		initialize();
	}
	
	private void initialize() {
		arcsTab.setText(labels.get(Labels.ARCS_TAB_TITLE) + arcs.getNodeId());
		outS.setText(arcs.getOutS());
		outSc.setText(arcs.getOutSc());
		inS.setText(arcs.getInS());
		inSc.setText(arcs.getInSc());
	}
	
	private void translateLabels() {
		outArcsLabel.setText(labels.get(Labels.ARCS_OUTGOING));
		outSLabel.setText(labels.get(Labels.ARCS_OUTGOING_S));
		outScLabel.setText(labels.get(Labels.ARCS_OUTGOING_SC));
		inArcsLabel.setText(labels.get(Labels.ARCS_INGOING));
		inSLabel.setText(labels.get(Labels.ARCS_INGOING_S));
		inScLabel.setText(labels.get(Labels.ARCS_INGOING_SC));
	}
	
}
