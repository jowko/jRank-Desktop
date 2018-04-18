package pl.jowko.jrank.desktop.service;

import pl.jowko.jrank.logger.JRankLogger;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-04-18.
 */
public class WorkspaceService {
	
	private static WorkspaceService instance;
	
	private SettingsService settingsService;
	
	private WorkspaceService() {
		settingsService = SettingsService.getInstance();
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
			if(!Paths.get(path).isAbsolute()) {
				path =  new File("").getAbsolutePath() + path;
			}
			
			File f = new File(path);
			
			if(!f.exists() || !f.isDirectory()) {
				return false;
			}
		} catch (RuntimeException e) {
			return false;
		}
		
		return true;
	}
	
}
