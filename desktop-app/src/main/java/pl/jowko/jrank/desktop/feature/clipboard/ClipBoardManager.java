package pl.jowko.jrank.desktop.feature.clipboard;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

import java.io.File;
import java.util.Collections;

/**
 * Created by Piotr on 2018-05-27
 * This class copies objects to user clipboard.
 */
public class ClipBoardManager {
	
	private static Clipboard clipboard;
	
	private ClipBoardManager() {}
	
	static {
		clipboard = Clipboard.getSystemClipboard();
	}
	
	/**
	 * Copy some string to user clipboard
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
	 * Clears all data from user clipboard.
	 */
	public static void clear() {
		clipboard.clear();
	}
	
	/**
	 * Copy csv table to user clipboard.
	 * It will be passed as text.
	 */
	public static void putCsvTable(CsvTable table) {
		putString(table.toString());
	}
	
}
