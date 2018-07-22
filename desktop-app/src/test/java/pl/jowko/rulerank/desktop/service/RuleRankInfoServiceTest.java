package pl.jowko.rulerank.desktop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jowko.rulerank.desktop.MasterTest;
import pl.jowko.rulerank.desktop.feature.settings.RuleRankInfo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Piotr on 2018-05-02.
 */
class RuleRankInfoServiceTest extends MasterTest {
	
	private RuleRankInfo info;
	
	@BeforeEach
	void setUpEach() {
		info = RuleRankInfoService.getInstance().getInfo();
	}
	
	@Test
	void shouldGetRuleRankInfo() {
		assertNotNull(info);
	}
	
	@Test
	void shouldGetVersion() {
		assertNotNull(info.getVersion());
	}
	
	@Test
	void shouldGetReleaseDate() {
		assertNotNull(info.getReleaseDate());
	}
	
}
