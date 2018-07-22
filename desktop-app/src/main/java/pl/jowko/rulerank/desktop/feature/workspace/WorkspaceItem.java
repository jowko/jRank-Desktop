package pl.jowko.rulerank.desktop.feature.workspace;

import java.nio.file.Paths;
import java.util.Objects;

/**
 * Created by Piotr on 2018-04-21.
 * This class represents one position in workspace tree.
 */
public class WorkspaceItem {
	
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
			return filePath + "\\";
		return Paths.get(filePath).getParent().toString() + "\\";
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
	
}
