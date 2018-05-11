package pl.jowko.jrank.desktop.feature.textfile;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created by Piotr on 2018-05-11.
 */
public class TextFileController {
	
	@FXML
	private TextArea textFileVIew;

	public void initializeTab(String fileContent) {
		textFileVIew.setText(fileContent);
	}

}
