package pl.jowko.rulerank.desktop.feature.tabs;

import javafx.scene.control.Tab;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.service.DialogsService;

import java.util.List;

/**
 * This class helps to detect if any tabs are edited. <br>
 * <br>
 * Created by Piotr on 2018-05-25
 */
public class TabEditionChecker {
	
	private LanguageService labels;
	
	public TabEditionChecker() {
		labels = LanguageService.getInstance();
	}
	
	/**
	 * Checks if any tab was edited.
	 * @param tabs to check
	 * @return true if at least one tab was edited, false otherwise
	 */
	public boolean areTabsInEditMode(List<Tab> tabs) {
		return getEditedTabsCount(tabs) > 0;
	}
	
	/**
	 * Count edited tabs in provided list.
	 * @param tabs to check
	 * @return count of edited tabs
	 */
	public long getEditedTabsCount(List<Tab> tabs) {
		return tabs.stream()
				.filter(tab -> {
					RuleRankTab ruleRankTab = (RuleRankTab) tab;
					return ruleRankTab.isTabEdited();
				})
				.count();
	}
	
	/**
	 * Display confirmation dialog if tab edition occurred.
	 * @param isEditModeOn boolean condition indicating that confirmation dialog should be displayed
	 * @param labelCode with will be displayed on confirmation dialog
	 * @return true is user confirm action or no edit action occurred, false otherwise
	 */
	public boolean shouldPerformAction(boolean isEditModeOn, String labelCode) {
		if(isEditModeOn)
			return DialogsService.showConfirmationDialog(labels.get(labelCode));
		return true;
	}
	
}
