package pl.jowko.jrank.desktop;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Created by Piotr on 2018-03-25.
 */
public class ResourceLoader {
	
	public <T> T load(String name) throws IOException {
		FXMLLoader fxml = new FXMLLoader();
		fxml.setLocation(getClass().getResource(name));
		return fxml.load();
	}
	
}
