package pl.jowko.jrank.desktop.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Piotr on 2018-04-10.
 */
public class LogsController {
	
	@FXML
	TextArea logsTextArea;
	
	@FXML
	public void initialize() {
		Console console = new Console(logsTextArea);
		PrintStream ps = new PrintStream(console, true);
		System.setOut(ps);
		System.setErr(ps);
		initializeContextMenu();
		JRankLogger.init("Initialized logs tab");
	}
	
	private void initializeContextMenu() {
		ContextMenu menu = new ContextMenu();
		menu.getItems().add(createClearLogMenuItem());
		logsTextArea.setContextMenu(menu);
	}
	
	private MenuItem createClearLogMenuItem() {
		MenuItem item = new MenuItem("Clear logs");
		item.setOnAction(event -> logsTextArea.clear());
		return item;
	}
	
	public static class Console extends OutputStream {
		
		private TextArea output;
		
		Console(TextArea textArea) {
			this.output = textArea;
		}
		
		@Override
		public void write(int i) {
			output.appendText(String.valueOf((char) i));
		}
		
		@Override
		public void write(byte[] b, int off, int len) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0 ; i < len ; i++) {
				builder.append((char) b[off + i]);
			}
			output.appendText(builder.toString());
		}
	}
	
}
