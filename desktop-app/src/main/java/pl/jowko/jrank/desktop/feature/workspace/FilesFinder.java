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
 * This is helper class, with finds workspace items in workspace main directory.
 * It is used to construct workspace tree.
 * @see TreeBuilder
 */
public class FilesFinder {
	
	private WorkspaceService workspaceService;
	
	public FilesFinder() {
		workspaceService = WorkspaceService.getInstance();
	}
	
	/**
	 * Find all directories in workspace directory(workspace path).
	 * This methods also return workspace directory in results list.
	 * @see WorkspaceService
	 * @return list of directories from workspace directory, also contains main workspace directory
	 */
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
	
	/**
	 * Finds all files for provided directory path.
	 * All files from provided directory will be returned as result list.
	 * @param directoryPath from with extract files data.
	 * @return list of files in directory
	 */
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
	
	/**
	 * Find default properties file.
	 * It is assumed, that this file have name: "default.properties" and is placed directly in workspace main directory(workspace path).
	 * @see WorkspaceService
	 * @return WorkspaceItem for default.properties file
	 */
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
	
	/**
	 * Converts provided path to workspace directory item.
	 * This items represents catalog items in workspace tree.
	 * It also checks, if provided path represents main workspace directory.
	 * If it is, root workspace item is returned instead of directory
	 * @see FileType
	 * @param path to directory
	 * @param workspacePath of main workspace directory
	 * @return WorkspaceItem for provided directory
	 */
	private WorkspaceItem mapPathToWorkspaceDirectory(Path path, String workspacePath) {
		FileType type;
		String absolutePath = path.toAbsolutePath().toString();
		
		if(workspacePath.equals(absolutePath)) {
			type = FileType.ROOT;
		} else {
			type = FileType.DIRECTORY;
		}
		
		return new WorkspaceItem(path.getFileName().toString(), absolutePath, type);
	}
	
	/**
	 * Convert provided path to workspace file directory.
	 * It returns workspace item with correct FileType.
	 * @see FileType
	 * @param path of file to convert
	 * @return WorkspaceItem for provided file path
	 */
	private WorkspaceItem mapPathToWorkspaceFile(Path path) {
		FileType type = resolveFileType(path.toString());
		String absolutePath = path.toAbsolutePath().toString();
		
		return new WorkspaceItem(path.getFileName().toString(), absolutePath, type);
	}
	
	/**
	 * Checks file path extension and return correct enum for this file type.
	 * This enum is used to differentiate different workspace item types later.
	 * @see FileType
	 * @see pl.jowko.jrank.desktop.feature.tabs.upper.UpperTabsController
	 * @param path from with file type info will be extracted
	 * @return FileType enum for provided file type
	 */
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
			return FileType.APPROXIMATION;
		if(path.endsWith("_partialPCT.isf"))
			return FileType.COMPARISION_TABLE;
		if(path.endsWith(".isf"))
			return FileType.LEARNING_TABLE;
		if(path.endsWith(".txt"))
			return FileType.TEXT;
		
		return FileType.UNKNOWN;
	}
	
}
