package pl.jowko.jrank.desktop.feature.settings;

import javafx.scene.control.Tooltip;
import pl.jowko.jrank.desktop.feature.internationalization.AbstractTranslator;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;

/**
 * Created by Piotr on 2018-05-20.
 * This class translates UI components for user settings dialog.
 */
class UserSettingsTranslator extends AbstractTranslator {
	
	private UserSettingsController controller;
	private LanguageService labels;
	
	UserSettingsTranslator(UserSettingsController controller) {
		this.controller = controller;
		labels = LanguageService.getInstance();
	}
	
	@Override
	public void translateFields() {
		controller.saveButton.setText(labels.get(Labels.BUTTON_SAVE));
		controller.cancelButton.setText(labels.get(Labels.BUTTON_CANCEL));
		controller.languageText.setText(labels.get(Labels.LANGUAGE));
		controller.workspaceLabel.setText(labels.get(Labels.WORKSPACE));
		controller.tooltipsEnabled.setText(labels.get(Labels.TOOLTIPS_ENABLED));
		controller.advancedPropertiesEnabled.setText(labels.get(Labels.ADVANCED_PROPERTIES_ENABLED));
		controller.advancedPropertiesEnabled.setTooltip(new Tooltip(labels.get(Labels.ADVANCED_PROPERTIES_ENABLED_TOOLTIP)));
		controller.infoText.setText(labels.get(Labels.US_INFO));
	}
	
}
