package pl.jowko.rulerank.desktop.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static pl.jowko.rulerank.desktop.utils.FileExtensionExtractor.getExtension;
import static pl.jowko.rulerank.desktop.utils.FileExtensionExtractor.getFileName;

/**
 * Created by Piotr on 2018-06-05
 */
class FileExtensionExtractorTest {
	
	@ParameterizedTest
	@CsvSource({"isf,test.isf", "isf,test.some.isf", "isf,C:\\some\\test.isf", ".,test."})
	void shouldExtract(String extension, String filePath) {
		String result = getExtension(filePath);
		assertEquals(extension, result);
	}
	
	@Test
	void shouldNotExtractExtension() {
		String result = getExtension("test");
		assertNull(result);
	}
	
	@ParameterizedTest
	@CsvSource({"test,test.isf", "test,test"})
	void shouldExtractFileName(String name, String fullFileName) {
		String result = getFileName(fullFileName);
		assertEquals(name, result);
	}
	
}
