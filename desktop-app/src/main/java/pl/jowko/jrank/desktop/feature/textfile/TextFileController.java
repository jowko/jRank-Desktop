package pl.jowko.jrank.desktop.feature.textfile;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created by Piotr on 2018-05-11.
 * Shows text(.txt) files in TextArea.
 */
public class TextFileController {
	
	@FXML
	private TextArea textFileView;
	
	/**
	 * Initializes text tab with TextArea containing file content.
	 * @param fileContent from .txt file with will be displayed in TextArea
	 */
	public void initializeTab(String fileContent) {
		textFileView.setText(fileContent);
	}

}
