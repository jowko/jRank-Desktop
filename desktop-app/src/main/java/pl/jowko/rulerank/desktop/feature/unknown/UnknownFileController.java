package pl.jowko.rulerank.desktop.feature.unknown;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created by Piotr on 2018-05-21.
 * This controller shows text content of files when file type is not recognized by application.
 * File content is displayed in non editable TextArea field.
 */
public class UnknownFileController {
	
	@FXML
	private TextArea unknownFileView;
	
	/**
	 * Initializes unknown tab containing non editable TextArea with file content.
	 * @param fileContent from read in file with will be displayed as text in unknown file tab.
	 */
	public void initializeTab(String fileContent) {
		unknownFileView.setText(fileContent);
	}
	
}
