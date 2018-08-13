package pl.jowko.rulerank.desktop.feature.workspace;

import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;
import static pl.jowko.rulerank.desktop.utils.PathUtils.getParentDirectory;
import static pl.jowko.rulerank.desktop.utils.PathUtils.getSubDirectoryPath;

/**
 * Created by Piotr on 2018-04-21.
 * This class represents one position in workspace tree.
 */
public class WorkspaceItem implements Comparable<WorkspaceItem> {
	
	private static Comparator<String> stringComparator = Comparator.comparing(String::toLowerCase);
	
	private String fileName;
	private String filePath;
	private FileType fileType;
	
	/**
	 * Create instance of item used in tree.
	 * @param fileName with is displayed in tree
	 * @param filePath with represents absolute path of file
	 * @param fileType with represents type of file
	 * @see FileType
	 */
	WorkspaceItem(String fileName, String filePath, FileType fileType) {
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileType = fileType;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public FileType getFileType() {
		return fileType;
	}
	
	/**
	 * Gets experiment directory path extracted from file path.
	 * @return experiment directory path
	 */
	public String getExperimentPath() {
		if(FileType.DIRECTORY.equals(fileType) || FileType.ROOT.equals(fileType))
			return getSubDirectoryPath(filePath);
		return getParentDirectory(filePath);
	}
	
	/**
	 * Gets experiment name from file path.
	 * @return name of directory for experiment associated with this item
	 */
	public String getExperimentName() {
		if(FileType.DIRECTORY.equals(fileType) || FileType.ROOT.equals(fileType))
			return filePath;
		return Paths.get(filePath).getParent().getFileName().toString();
	}
	
	@Override
	public String toString() {
		return "WorkspaceItem{" +
				"fileName='" + fileName + '\'' +
				", filePath='" + filePath + '\'' +
				", fileType=" + fileType +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		WorkspaceItem that = (WorkspaceItem) o;
		return Objects.equals(fileName, that.fileName) &&
				Objects.equals(filePath, that.filePath) &&
				fileType == that.fileType;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(fileName, filePath, fileType);
	}
	
	/**
	 * Compares provided object with this object.
	 * It is used in sorting.
	 * Directories will be returned on top of the list.
	 * File names are sorted alphabetically with ignoring character case.
	 * @param o to be compared to
	 * @return -1, if provided object should be above this item, 0 if it is doesn't matter, 1 if should be below this item
	 */
	@Override
	public int compareTo(WorkspaceItem o) {
		if(fileType.equals(FileType.DIRECTORY) && not(o.fileType.equals(FileType.DIRECTORY))) {
			return -1;
		}
		if(not(fileType.equals(FileType.DIRECTORY)) && o.fileType.equals(FileType.DIRECTORY)) {
			return 1;
		}
		return stringComparator.compare(fileName, o.fileName);
	}
	
}
