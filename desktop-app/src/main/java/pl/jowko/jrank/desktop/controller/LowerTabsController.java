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
	
	public void onButtonAction() {
		System.out.println("button in lower tabs in second tab");
	}
	
	@FXML
	private void initialize() {
		labels = LanguageService.getInstance();
		translateLabels();
	}
	
	private void translateLabels() {
		logsTab.setText(labels.get(Labels.LOGS_TAB));
	}
	
}
