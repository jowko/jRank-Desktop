package pl.jowko.jrank.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
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
		JRankLogger.init("Initialized logs tab");
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
