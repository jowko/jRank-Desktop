package pl.jowko.jrank.desktop.feature.workspace;

/**
 * Created by Piotr on 2018-04-21.
 */
public class WorkspaceItem {
	
	private String fileName;
	private String filePath;
	private FileType fileType;
	
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
