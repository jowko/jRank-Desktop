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
public class FilesFinder {
	
	private WorkspaceService workspaceService;
	
	public FilesFinder() {
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
	
	public List<WorkspaceItem> findAllFiles(String directoryPath) {
		List<WorkspaceItem> files = new ArrayList<>();
		
		try (Stream<Path> filesPath = Files.walk(Paths.get(directoryPath))) {
			
			files = filesPath
					.filter(Files::isRegularFile)
					.map(this::mapPathToWorkspaceFile)
					.collect(Collectors.toList());
			
		} catch (IOException e) {
			JRankLogger.error("Error when reading directory tree: " + directoryPath + " ", e);
		}
		
		return files;
	}
	
	WorkspaceItem findDefaultProperties() {
		String workspacePath = workspaceService.getWorkspacePath();
		
		try {
			Path path = Paths.get(workspacePath + "\\default.properties");
			return mapPathToWorkspaceFile(path);
		} catch (Exception e) {
			JRankLogger.error("Error when reading default.properties file: ", e);
		}
		
		return null;
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
		FileType type = resolveFileType(path.toString());
		String absolutePath = path.toAbsolutePath().toString();
		
		return new WorkspaceItem(path.getFileName().toString(), absolutePath, type);
	}
	
	private FileType resolveFileType(String path) {
		if(path.endsWith(".properties"))
			return FileType.JRANK_SETTINGS;
		if(path.endsWith(".graph"))
			return FileType.GRAPH;
		if(path.endsWith(".ranking"))
			return FileType.RANKING;
		if(path.endsWith(".rules"))
			return FileType.RULES;
		if(path.endsWith(".apx"))
			return FileType.DOMINANCE;
		if(path.endsWith("_partialPCT.isf"))
			return FileType.COMPARISION_TABLE;
		if(path.endsWith(".isf"))
			return FileType.LEARNING_TABLE;
		if(path.endsWith(".txt"))
			return FileType.TEXT;
		
		return FileType.UNKNOWN;
	}
	
}
