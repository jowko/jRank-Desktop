package pl.jowko.jrank.desktop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jowko.jrank.desktop.MasterTest;
import pl.jowko.jrank.desktop.feature.settings.UserSettingsService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static pl.jowko.jrank.desktop.feature.settings.Labels.MENU_FILE;

/**
 * Created by Piotr on 2018-05-02.
 */
class LanguageServiceTest extends MasterTest {
	
	private LanguageService labels;
	private UserSettingsService userSettingsService;
	
	@BeforeEach
	void setUpEach() {
		labels = LanguageService.getInstance();
		userSettingsService = UserSettingsService.getInstance();
	}
	
	@AfterEach
	void setEngLanguage() {
		userSettingsService.setLanguage("ENG");
	}
	
	@Test
	void shouldGetLanguages() {
		Map<String, String> languages = labels.getLanguages();
		
		assertNotNull(languages);
		assertTrue(languages.size() > 0);
	}
	
	@Test
	void shouldGetLabels() {
		Map<String, Map<String, String>> labelsMap = labels.getLabels();
		
		assertNotNull(labelsMap);
		assertTrue(labelsMap.size() > 0);
		assertTrue(labelsMap.get("ENG").size() > 0);
	}
	
	@Test
	void shouldGetEngCode() {
		userSettingsService.setLanguage("ENG");
		String label = labels.get(MENU_FILE);
		assertEquals("File", label);
	}
	
	@Test
	void shouldGetPolCode() {
		userSettingsService.setLanguage("POL");
		String label = labels.get(MENU_FILE);
		assertEquals("Plik", label);
	}
	
	@Test
	void shouldNotFindLanguage() {
		userSettingsService.setLanguage("HAHA");
		String label = labels.get(MENU_FILE);
		assertEquals("[HAHA:MENU_FILE]", label);
	}
	
	@Test
	void shouldNotFindCode() {
		String label = labels.get("SOME_CODE");
		assertEquals("[ENG:SOME_CODE]", label);
	}
	
	@Test
	void shouldWriteLogWhenNotFoundLanguage() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		userSettingsService.setLanguage("HAHA");
		labels.get(MENU_FILE);
		
		assertTrue(outContent.toString().contains("WARN") && outContent.toString().contains("HAHA"));
		
		System.setOut(System.out);
	}
	
	@Test
	void shouldWriteLogWhenNotFoundCode() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		labels.get("SOME_CODE");
		
		assertTrue(outContent.toString().contains("WARN") && outContent.toString().contains("SOME_CODE"));
		
		System.setOut(System.out);
	}
	
}
