package pl.jowko.rulerank.desktop.feature.textfile;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.tabs.RemovableChangeListener;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.jowko.rulerank.desktop.feature.tabs.upper.UpperTabsController;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.rulerank.desktop.service.DialogsService;

import java.io.IOException;
import java.io.PrintWriter;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-11.
 * Shows text(.txt) files in TextArea and allows to edit them.
 */
public class TextFileController {
	
	@FXML
	private TextArea textFileView;
	
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;
	
	private LanguageService labels;
	private WorkspaceItem workspaceItem;
	private RuleRankTab textFileTab;
	
	/**
	 * Initializes text tab with TextArea containing file content.
	 * @param fileContent from .txt file with will be displayed in TextArea
	 * @param workspaceItem from workspace tree
	 * @param textFileTab on with TextArea is placed
	 */
	public void initializeTab(String fileContent, WorkspaceItem workspaceItem, RuleRankTab textFileTab) {
		this.workspaceItem = workspaceItem;
		this.textFileTab = textFileTab;
		textFileView.setText(fileContent);
		labels = LanguageService.getInstance();
		initializeEditAndCloseEvent();
		translateFields();
	}
	
	/**
	 * Save text from TextArea to original file.
	 * @throws IOException when something go wrong with file save
	 */
	public void saveAction() throws IOException {
		String textContent = textFileView.getText();
		PrintWriter out = new PrintWriter(workspaceItem.getFilePath());
		out.print(textContent);
		out.close();
		
		closeTab();
	}
	
	public void cancelAction() {
		if(isUserWishToKeepChanges()) {
			return;
		}
		closeTab();
	}
	
	private void initializeEditAndCloseEvent() {
		textFileTab.setOnCloseRequest(event -> {
			if(isUserWishToKeepChanges()) {
				event.consume();
			}
		});
		textFileView.textProperty().addListener(new RemovableChangeListener(textFileTab, textFileView));
	}
	
	private void closeTab() {
		UpperTabsController.getInstance().closeTab(textFileTab);
	}
	
	/**
	 * Check, if user wants to keep changes.
	 * If table was edited, it will show confirmation dialog.
	 */
	private boolean isUserWishToKeepChanges() {
		return textFileTab.isTabEdited() && not(showAbandonChangesConfirmationDialog());
	}
	
	private boolean showAbandonChangesConfirmationDialog() {
		String header = labels.get(Labels.TXT_TAB_ABANDON_CHANGES);
		return DialogsService.showConfirmationDialog(header, "");
	}
	
	private void translateFields() {
		saveButton.setText(labels.get(Labels.TXT_TAB_SAVE_BUTTON));
		cancelButton.setText(labels.get(Labels.TXT_TAB_CANCEL_BUTTON));
	}

}
