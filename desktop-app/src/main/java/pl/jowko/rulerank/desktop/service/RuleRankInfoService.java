package pl.jowko.rulerank.desktop.service;

import pl.jowko.rulerank.desktop.feature.settings.RuleRankInfo;

import static java.util.Objects.isNull;

/**
 * Service for managing about dialog information.<br>
 * <br>
 * Created by Piotr on 2018-04-14.
 */
public class RuleRankInfoService {
	
	private static RuleRankInfoService instance;
	
	private RuleRankInfo info;
	
	private RuleRankInfoService() {}
	
	public static RuleRankInfoService getInstance() {
		if(isNull(instance)) {
			instance = new RuleRankInfoService();
			instance.loadInfo();
		}
		return instance;
	}
	
	public RuleRankInfo getInfo() {
		return info;
	}
	
	private void loadInfo() {
		info = ConfigFileManager.getInstance().readRuleRankInfo();
	}
	
}
