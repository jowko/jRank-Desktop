package pl.jowko.jrank.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import pl.jowko.jrank.desktop.feature.settings.Labels;
import pl.jowko.jrank.desktop.service.LanguageService;

/**
 * Created by Piotr on 2018-04-09.
 */
public class LowerTabsController {
	
	@FXML
	private Tab logsTab;
	
	private LanguageService labels;
	
	@FXML
	private void initialize() {
		labels = LanguageService.getInstance();
		translateLabels();
	}
	
	private void translateLabels() {
		logsTab.setText(labels.get(Labels.LOGS_TAB));
	}
	
}
