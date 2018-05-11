package pl.jowko.jrank.desktop.service;

import pl.jowko.jrank.desktop.feature.settings.JRankInfo;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-04-14.
 */
public class JRankInfoService {
	
	private static JRankInfoService instance;
	
	private JRankInfo info;
	
	private JRankInfoService() {}
	
	public static JRankInfoService getInstance() {
		if(isNull(instance)) {
			instance = new JRankInfoService();
			instance.loadInfo();
		}
		return instance;
	}
	
	public JRankInfo getInfo() {
		return info;
	}
	
	private void loadInfo() {
		info = FileManager.getInstance().readJRankInfo();
	}
	
}
