package pl.jowko.rulerank.desktop.feature.tabs.lower;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;

/**
 * Created by Piotr on 2018-04-09.
 * Controller for lower tabs.
 * It contains tabs for logs and also some additional tabs.
 */
public class LowerTabsController {
	
	private static LowerTabsController instance;
	
	@FXML
	private TabPane lowerTabs;
	@FXML
	private Tab logsTab;
	
	private LanguageService labels;
	
	public static LowerTabsController getInstance() {
		return instance;
	}
	
	/**
	 * Initialize this controller.
	 * Prepare instance for getInstance method.
	 * Disable manual closing of logs tab.
	 */
	@FXML
	void initialize() {
		instance = this;
		labels = LanguageService.getInstance();
		translateLabels();
		logsTab.setClosable(false);
	}
	
	/**
	 * Add provided tab to lower tabs.
	 * This tab will be closable and selected after addition.
	 * @param tab to add
	 */
	public void addTab(RuleRankTab tab) {
		tab.setClosable(true);
		lowerTabs.getTabs().add(tab);
		lowerTabs.getSelectionModel().select(tab);
	}
	
	/**
	 * This method selects log tab.
	 * When tab will be selected, it will be focused.
	 */
	public void focusOnLogTab() {
		focusOnTab(logsTab);
	}
	
	/**
	 * This method selects provided tab.
	 * Focus will be set on provided tab in lower tabs.
	 * @param tab on with focus will be set
	 */
	public void focusOnTab(Tab tab) {
		lowerTabs.getSelectionModel().select(tab);
	}
	
	/**
	 * Remove provided tab from lower tabs.
	 * Tab will be closed automatically and removed from lower tabs.
	 */
	public void closeTab(RuleRankTab tab) {
		lowerTabs.getTabs().remove(tab);
	}
	
	private void translateLabels() {
		logsTab.setText(labels.get(Labels.LOGS_TAB));
	}
	
}