package pl.jowko.rulerank.desktop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jowko.rulerank.desktop.MasterTest;
import pl.jowko.rulerank.desktop.feature.settings.JRankInfo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Piotr on 2018-05-02.
 */
class JRankInfoServiceTest extends MasterTest {
	
	private JRankInfo info;
	
	@BeforeEach
	void setUpEach() {
		info = JRankInfoService.getInstance().getInfo();
	}
	
	@Test
	void shouldGetJRankInfo() {
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
