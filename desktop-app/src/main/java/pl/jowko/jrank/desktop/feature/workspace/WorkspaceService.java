package pl.jowko.jrank.desktop.feature.workspace;

import pl.jowko.jrank.desktop.service.UserSettingsService;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-04-18.
 */
public class WorkspaceService {
	
	private static WorkspaceService instance;
	
	private UserSettingsService userSettingsService;
	
	private WorkspaceService() {
		userSettingsService = UserSettingsService.getInstance();
	}
	
	public static WorkspaceService getInstance() {
		if(isNull(instance)) {
			instance = new WorkspaceService();
		}
		return instance;
	}
	
	public boolean isWorkspacePathValid(String path) {
		if(Objects.isNull(path) || path.isEmpty()) {
			return false;
		}
		
		try {
			path = getAbsolutePath(path);
			File f = new File(path);
			
			if(!f.exists() || !f.isDirectory()) {
				return false;
			}
		} catch (RuntimeException e) {
			return false;
		}
		
		return true;
	}
	
	public String getWorkspacePath() {
		String workspacePath = userSettingsService.getUserSettings().getWorkspacePath();
		return getAbsolutePath(workspacePath);
	}
	
	private String getAbsolutePath(String path) {
		if(!Paths.get(path).isAbsolute()) {
			return new File("").getAbsolutePath() + path;
		}
		return path;
	}
	
}
