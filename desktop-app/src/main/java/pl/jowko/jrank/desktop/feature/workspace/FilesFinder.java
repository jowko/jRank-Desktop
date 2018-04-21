package pl.jowko.jrank.desktop.feature.workspace;

import pl.jowko.jrank.logger.JRankLogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Piotr on 2018-04-21.
 */
class FilesFinder {
	
	private WorkspaceService workspaceService;
	
	FilesFinder() {
		workspaceService = WorkspaceService.getInstance();
	}
	
	List<WorkspaceItem> findAllDirectories() {
		List<WorkspaceItem> paths = null;
		String workspacePath = workspaceService.getWorkspacePath();
		
		try (Stream<Path> workspacePaths = Files.walk(Paths.get(workspacePath))) {
			
			paths = workspacePaths
					.filter(Files::isDirectory)
					.map(path -> mapPathToWorkspaceDirectory(path, workspacePath))
					.collect(Collectors.toList());
			
		} catch (IOException e) {
			JRankLogger.error("Error when reading workspace directory tree: ", e);
		}
		
		return paths;
	}
	
	List<WorkspaceItem> findAllFiles(String directoryPath) {
		List<WorkspaceItem> files = new ArrayList<>();
		
		try (Stream<Path> filesPath = Files.walk(Paths.get(directoryPath))) {
			
			files = filesPath
					.filter(Files::isRegularFile)
					.map(path -> mapPathToWorkspaceFile(path))
					.collect(Collectors.toList());
			
		} catch (IOException e) {
			JRankLogger.error("Error when reading directory tree: " + directoryPath + " ", e);
		}
		
		return files;
	}
	
	private WorkspaceItem mapPathToWorkspaceDirectory(Path path, String workspacePath) {
		FileType type;
		String absolutePath = path.toAbsolutePath().toString();
		
		if(workspacePath.equals(absolutePath)) {
			type = FileType.ROOT;
		} else {
			type = FileType.FOLDER;
		}
		
		return new WorkspaceItem(path.getFileName().toString(), absolutePath, type);
	}
	
	private WorkspaceItem mapPathToWorkspaceFile(Path path) {
		FileType type;
		String absolutePath = path.toAbsolutePath().toString();
		
		//TODO resolve file types later
		type = FileType.DATA_TABLE;
		
		return new WorkspaceItem(path.getFileName().toString(), absolutePath, type);
	}
	
}
