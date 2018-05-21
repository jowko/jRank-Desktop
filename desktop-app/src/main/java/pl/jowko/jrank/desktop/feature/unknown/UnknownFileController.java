package pl.jowko.jrank.desktop.feature.unknown;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created by Piotr on 2018-05-21.
 * This controller shows text content of files when file type is not recognized by application.
 * File content is displayed in TextArea field.
 */
public class UnknownFileController {
	
	@FXML
	private TextArea unknownFileView;
	
	public void initializeTab(String fileContent) {
		unknownFileView.setText(fileContent);
	}
	
}
