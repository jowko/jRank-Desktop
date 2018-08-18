package pl.jowko.rulerank.desktop.service;

import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * This interface allows to display confirmation dialog when tab is marked as edited.<br>
 * If user edited tab and try to close it, he will be asked to confirm action.<br>
 * <br>
 * Created by Piotr on 2018-08-09
 */
public interface AbandonableTabForm {
	
	/**
	 * This method should return tab related with edited form.
	 * @return tab from user form
	 */
	RuleRankTab getTab();
	
	/**
	 * Check, if user wants to keep changes. <br>
	 * If table was edited, it will show confirmation dialog.
	 */
	default boolean isUserWishToKeepChanges() {
		return getTab().isTabEdited() && not(showAbandonChangesConfirmationDialog());
	}
	
	/**
	 * Initialize close event for tab. <br>
	 * It will display confirmation dialog on close event for user if he edited tab.
	 */
	default void initializeCloseEvent() {
		getTab().setOnCloseRequest(event -> {
			if(isUserWishToKeepChanges()) {
				event.consume();
			}
		});
	}
	
	default boolean showAbandonChangesConfirmationDialog() {
		String header = LanguageService.getInstance().get(Labels.ABANDON_CHANGES_HEADER);
		return DialogsService.showConfirmationDialog(header, "");
	}
	
}
