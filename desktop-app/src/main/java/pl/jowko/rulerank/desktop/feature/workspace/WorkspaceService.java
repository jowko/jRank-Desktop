package pl.jowko.rulerank.desktop.feature.workspace;

import pl.jowko.rulerank.desktop.feature.settings.UserSettingsService;

import java.io.File;
import java.util.Objects;

import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.utils.PathUtils.getAbsolute;

/**
 * Created by Piotr on 2018-04-18.
 * This class helps manage workspace items in workspace tree.
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
	
	/**
	 * Checks, if workspace path is valid.
	 * When path doesn't exists, it is assumed that path is invalid.
	 * @param path from user settings form or userSettings.json file
	 * @return true if path is valid, false otherwise
	 */
	public boolean isWorkspacePathValid(String path) {
		if(Objects.isNull(path) || path.isEmpty()) {
			return false;
		}
		
		try {
			path = getAbsolute(path);
			File f = new File(path);
			
			if(!f.exists() || !f.isDirectory()) {
				return false;
			}
		} catch (RuntimeException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Gets absolute workspace path from user settings
	 * @return absolute workspace path
	 */
	public String getWorkspacePath() {
		String workspacePath = userSettingsService.getUserSettings().getWorkspacePath();
		return getAbsolute(workspacePath);
	}
	
}
