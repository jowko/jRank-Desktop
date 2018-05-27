package pl.jowko.jrank.desktop.utils;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-05-26
 * This class helps to extract file extensions from file path.
 */
public class FileExtensionExtractor {
	
	private static Pattern fileExtensionPattern;
	
	static {
		fileExtensionPattern = Pattern.compile("\\.(?=[^.]+$)");
	}
	
	private FileExtensionExtractor() {}
	
	/**
	 * This method splits provided file path to base and extension.
	 * If filePath doesn't contains extension, null will be returned.
	 */
	public static String getExtension(String filePath) {
		if(isNull(filePath) || filePath.trim().isEmpty())
			return null;
		
		String[] array = fileExtensionPattern.split(filePath, 2);
		
		
		if(array[0].endsWith("."))
			return ".";
		// file doesn't have extension
		if(array.length <= 1) {
			return null;
		}
		
		return array[1];
	}
	
}