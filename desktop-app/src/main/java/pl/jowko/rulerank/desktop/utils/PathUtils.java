package pl.jowko.rulerank.desktop.utils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Piotr on 2018-08-12
 * This class contains helper method for path processing.
 */
public class PathUtils {
	
	private PathUtils() {}
	
	/**
	 * This method converts provided path to absolute path.
	 * If provided path is already absolute, it will be returned without changes.
	 * @param filePath with will be converted to absolute path
	 * @return absolute path
	 */
	public static String getAbsolute(String filePath) {
		if(!Paths.get(filePath).isAbsolute()) {
			return new File("").getAbsolutePath() + filePath;
		}
		return filePath;
	}
	
	/**
	 * Gets absolute or relative parent directory path(depending on parameter).
	 * @param path relative or absolute containing directory
	 * @return parent directory from provided path
	 */
	public static String getParentDirectory(String path) {
		String directory = Paths.get(path).getParent().toString();
		return getSubDirectoryPath(directory);
	}
	
	/**
	 * Gets absolute file path in provided experiment.
	 * If provided filePath is absolute, it is returned as result.
	 * If path is relative, it is assumed, that file should be in experiment directory or subdirectory.
	 * @param experimentPath with should be absolute
	 * @param filePath with can be absolute or relative
	 * @return absolute path to file in provided experiment
	 */
	public static String getAbsoluteExperimentFilePath(String experimentPath, String filePath) {
		Path path = Paths.get(filePath);
		
		if(path.isAbsolute()) {
			return filePath;
		} else {
			return experimentPath + path.toString();
		}
	}
	
	/**
	 * Gets subdirectory path for provided directory path.
	 * @param directory from with subdirectory path will be returned
	 * @return subdirectory path for provided directory
	 */
	public static String getSubDirectoryPath(String directory) {
		return FilenameUtils.concat(directory, "");
	}
	
}
