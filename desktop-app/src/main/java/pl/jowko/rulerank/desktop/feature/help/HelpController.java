package pl.jowko.rulerank.desktop.feature.help;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import pl.jowko.rulerank.desktop.Main;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.service.ConfigFileManager;
import pl.jowko.rulerank.feature.customfx.AbstractDialogForm;

/**
 * Created by Piotr on 2018-08-14
 * This controller manages help dialog.
 */
public class HelpController extends AbstractDialogForm {
	
	@FXML
	private Label pageUrlLabel;
	@FXML
	private Hyperlink pageUrl;
	@FXML
	private Label contactInfoLabel;
	@FXML
	private Label contactInfo;
	@FXML
	private Label additionalInfo;
	
	private HelpInfo helpInfo;
	
	private LanguageService labels;
	
	@FXML
	private void initialize() {
		helpInfo = ConfigFileManager.getInstance().readHelpInfo();
		fillFields();
		translateLabels();
	}
	
	/**
	 * Fills fields with data and sets open browser action to action event on hyperlink.
	 */
	private void fillFields() {
		pageUrl.setText(helpInfo.getProjectUrl());
		contactInfo.setText(helpInfo.getContactInfo());
		additionalInfo.setText(helpInfo.getAdditionalInfo());
		
		pageUrl.setOnAction(event ->
				openBrowserAction()
		);
	}
	
	private void openBrowserAction() {
		Main.getServices().showDocument(helpInfo.getProjectUrl());
	}
	
	private void translateLabels() {
		labels = LanguageService.getInstance();
		pageUrlLabel.setText(labels.get(Labels.HELP_PAGE_URL));
		contactInfoLabel.setText(labels.get(Labels.HELP_CONTACT_INFO));
	}
	
}
