package pl.jowko.rulerank.desktop.feature.settings;

import javafx.scene.control.Tooltip;
import pl.jowko.rulerank.desktop.feature.internationalization.AbstractTranslator;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;

/**
 * This class translates UI components for user settings dialog. <br>
 *  <br>
 * Created by Piotr on 2018-05-20.
 */
class UserSettingsTranslator extends AbstractTranslator {
	
	private UserSettingsController controller;
	
	UserSettingsTranslator(UserSettingsController controller) {
		this.controller = controller;
	}
	
	@Override
	public void translateFields() {
		controller.saveButton.setText(get(Labels.BUTTON_SAVE));
		controller.cancelButton.setText(get(Labels.BUTTON_CANCEL));
		controller.languageText.setText(get(Labels.LANGUAGE));
		controller.workspaceLabel.setText(get(Labels.WORKSPACE));
		controller.csvSeparatorLabel.setText(get(Labels.CSV_SEPARATOR));
		controller.tooltipsEnabled.setText(get(Labels.TOOLTIPS_ENABLED));
		controller.advancedPropertiesEnabled.setText(get(Labels.ADVANCED_PROPERTIES_ENABLED));
		controller.advancedPropertiesEnabled.setTooltip(new Tooltip(get(Labels.ADVANCED_PROPERTIES_ENABLED_TOOLTIP)));
		controller.manualEditionEnabled.setText(get(Labels.MANUAL_EDITION_ENABLED));
		controller.manualEditionEnabled.setTooltip(new Tooltip(get(Labels.MANUAL_EDITION_ENABLED_TOOLTIP)));
		controller.startMaximized.setText(get(Labels.START_MAXIMIZED));
		controller.infoText.setText(get(Labels.US_INFO));
	}
	
}
