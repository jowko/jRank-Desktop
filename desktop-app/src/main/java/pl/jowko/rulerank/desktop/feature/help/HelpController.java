package pl.jowko.rulerank.desktop.feature.help;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import pl.jowko.rulerank.desktop.Main;
import pl.jowko.rulerank.desktop.feature.clipboard.ClipBoardManager;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.service.ConfigFileManager;
import pl.jowko.rulerank.feature.customfx.AbstractDialogForm;
import pl.jowko.rulerank.feature.customfx.SelectableLabel;
import pl.jowko.rulerank.logger.RuleRankLogger;

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
	private SelectableLabel contactInfo;
	@FXML
	private Label additionalInfo;
	
	private HelpInfo helpInfo;
	
	private LanguageService labels;
	
	@FXML
	private void initialize() {
		helpInfo = ConfigFileManager.getInstance().readHelpInfo();
		labels = LanguageService.getInstance();
		fillFields();
		initializePageUrlContextMenu();
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
	
	/**
	 * Initializes Context menu on page url field.
	 * It adds Copy action to this field with copies url to user clipboard.
	 */
	private void initializePageUrlContextMenu() {
		MenuItem copyItem = new MenuItem(labels.get(Labels.HELP_COPY_URL));
		
		copyItem.setOnAction(e -> {
			ClipBoardManager.putString(helpInfo.getProjectUrl());
			RuleRankLogger.info("Page url was copied to clipboard successfully.");
		});
		
		pageUrl.setContextMenu(new ContextMenu(copyItem));
	}
	
	private void translateLabels() {
		pageUrlLabel.setText(labels.get(Labels.HELP_PAGE_URL));
		contactInfoLabel.setText(labels.get(Labels.HELP_CONTACT_INFO));
	}
	
}
