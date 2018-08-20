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
import pl.jowko.rulerank.desktop.service.AbandonableTabForm;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Shows text(.txt) files in TextArea and allows to edit them. <br>
 *  <br>
 * Created by Piotr on 2018-05-11.
 */
public class TextFileController implements AbandonableTabForm {
	
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
	
	@Override
	public RuleRankTab getTab() {
		return textFileTab;
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
		initializeCloseEvent();
		textFileView.textProperty().addListener(new RemovableChangeListener<>(textFileTab, textFileView.textProperty()));
	}
	
	private void closeTab() {
		UpperTabsController.getInstance().closeTab(textFileTab);
	}
	
	private void translateFields() {
		saveButton.setText(labels.get(Labels.TXT_TAB_SAVE_BUTTON));
		cancelButton.setText(labels.get(Labels.TXT_TAB_CANCEL_BUTTON));
	}

}
