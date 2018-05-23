package pl.jowko.jrank.desktop.feature.workspace;

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
	
	@Override
	public String toString() {
		return "WorkspaceItem{" +
				"fileName='" + fileName + '\'' +
				", filePath='" + filePath + '\'' +
				", fileType=" + fileType +
				'}';
	}
	
}
