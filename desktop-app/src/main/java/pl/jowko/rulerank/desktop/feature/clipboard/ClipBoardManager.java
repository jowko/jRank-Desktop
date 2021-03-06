package pl.jowko.rulerank.desktop.feature.clipboard;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

import java.io.File;
import java.util.List;

/**
 * This class copies objects to user clipboard. <br>
 *  <br>
 * Created by Piotr on 2018-05-27
 */
public class ClipBoardManager {
	
	private static Clipboard clipboard;
	
	private ClipBoardManager() {}
	
	static {
		clipboard = Clipboard.getSystemClipboard();
	}
	
	/**
	 * Copy some string to user clipboard
	 * @param text with will be copied to clipboard
	 */
	public static void putString(String text) {
		ClipboardContent content = new ClipboardContent();
		content.putString(text);
		clipboard.setContent(content);
	}
	
	/**
	 * Puts provided object using DataFormat key.
	 * @param key on with objects resides
	 * @param value to put to Clipboard
	 */
	public static void putObject(DataFormat key, Object value) {
		ClipboardContent content = new ClipboardContent();
		content.put(key, value);
		clipboard.setContent(content);
	}
	
	/**
	 * Puts provided list fo files to user clipboard.
	 * @param files with will be putted to cliboard
	 */
	public static void putFile(List<File> files) {
		ClipboardContent content = new ClipboardContent();
		content.putFiles(files);
		clipboard.setContent(content);
	}
	
	/**
	 * Clears all data from user clipboard.
	 */
	public static void clear() {
		clipboard.clear();
	}
	
	/**
	 * Copy csv table to user clipboard. <br>
	 * It will be passed as text.
	 * @param table with will be converted to string and put to clipboard
	 */
	public static void putCsvTable(CsvTable table) {
		putString(table.toString());
	}
	
}
