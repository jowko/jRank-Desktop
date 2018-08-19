package pl.jowko.rulerank.desktop.feature.workspace;

import pl.jowko.rulerank.logger.RuleRankLogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * This is helper class, with finds workspace items in workspace main directory. <br>
 * It is used to construct workspace tree. <br>
 *  <br>
 * Created by Piotr on 2018-04-21.
 * @see TreeBuilder
 */
public class FilesFinder {
	
	private WorkspaceService workspaceService;
	
	public FilesFinder() {
		workspaceService = WorkspaceService.getInstance();
	}
	
	/**
	 * Find all files in provided directory. <br>
	 * Files are sorted before return.
	 * @see WorkspaceService
	 * @return list of files in provided directory
	 */
	List<WorkspaceItem> findFilesInDirectory(String directoryPath) {
		List<WorkspaceItem> paths = null;
		
		try (Stream<Path> workspacePaths = Files.walk(Paths.get(directoryPath), 1)) {
			
			paths = workspacePaths
					.filter(path -> not(directoryPath.equals(path.toString())))
					.map(this::mapPathToWorkspaceFile)
					.collect(Collectors.toList());
			
		} catch (IOException e) {
			RuleRankLogger.error("Error when reading workspace tree: ", e);
		}
		
		Collections.sort(paths);
		
		return paths;
	}
	
	/**
	 * Finds all files for provided directory path. <br>
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
			RuleRankLogger.error("Error when reading directory tree: " + directoryPath + " ", e);
		}
		
		return files;
	}
	
	/**
	 * Convert provided path to workspace item <br>
	 * It returns workspace item with correct FileType.
	 * @see FileType
	 * @param path of file to convert
	 * @return WorkspaceItem for provided file path
	 */
	WorkspaceItem mapPathToWorkspaceFile(Path path) {
		FileType type = resolveFileType(path);
		String absolutePath = path.toAbsolutePath().toString();
		
		return new WorkspaceItem(path.getFileName().toString(), absolutePath, type);
	}
	
	/**
	 * Checks file path extension and return correct enum for this file type. <br>
	 * This enum is used to differentiate different workspace item types later.
	 * @see FileType
	 * @see pl.jowko.rulerank.desktop.feature.tabs.upper.UpperTabsController
	 * @param filePath from with file type info will be extracted
	 * @return FileType enum for provided file type
	 */
	private FileType resolveFileType(Path filePath) {
		String path = filePath.toString();
		if(path.endsWith(".properties"))
			return FileType.PROPERTIES;
		if(path.endsWith(".graph"))
			return FileType.GRAPH;
		if(path.endsWith(".ranking"))
			return FileType.RANKING;
		if(path.endsWith(".rules"))
			return FileType.RULES;
		if(path.endsWith(".apx"))
			return FileType.APPROXIMATION;
		if(path.endsWith(".isf"))
			return FileType.ISF_TABLE;
		if(path.endsWith(".txt"))
			return FileType.TEXT;
		
		String absolutePath = filePath.toAbsolutePath().toString();
		String workspacePath = workspaceService.getWorkspacePath();
		
		if(workspacePath.equals(absolutePath)) {
			return FileType.ROOT;
		} else if(Files.isDirectory(filePath)) {
			return FileType.DIRECTORY;
		}
		
		return FileType.UNKNOWN;
	}
	
}
