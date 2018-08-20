package pl.jowko.rulerank.desktop.feature.rules;

import javafx.scene.Parent;
import pl.jowko.rulerank.desktop.ResourceLoader;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.poznan.put.cs.idss.jrs.rules.Rule;

import java.io.IOException;

import static pl.jowko.rulerank.desktop.feature.settings.RuleRankConst.COMMON_CSS;

/**
 * This class represents tab for statistics screen. <br>
 * It loads fxml file and passes rule to controller. <br>
 *  <br>
 * Created by Piotr on 2018-05-21.
 */
class StatisticsTab extends RuleRankTab {
	
	private StatisticsController controller;
	
	/**
	 * Create instance of statistics tab.
	 * @param rule with will be displayed as first
	 * @param tabName to set tab header.
	 * @throws IOException when something go wrong with loading fxml file
	 */
	StatisticsTab(Rule rule, String tabName) throws IOException {
		super(tabName);
		
		ResourceLoader loader = new ResourceLoader(getResourceName());
		Parent tabContent = loader.load();
		tabContent.getStylesheets().add(COMMON_CSS);
		super.setContent(tabContent);
		
		controller = loader.getController();
		controller.showRule(rule);
	}
	
	@Override
	protected String getResourceName() {
		return "/fxml/lowerTabs/statisticsTab.fxml";
	}
	
	public StatisticsController getController() {
		return controller;
	}
	
}
