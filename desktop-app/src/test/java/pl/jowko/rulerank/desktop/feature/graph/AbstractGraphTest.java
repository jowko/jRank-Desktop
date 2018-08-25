package pl.jowko.rulerank.desktop.feature.graph;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Piotr on 2018-08-25
 */
abstract class AbstractGraphTest {
	
	static GraphDto getGraph(String content) {
		GraphReader reader = new GraphReader(content);
		return reader.extractGraph();
	}
	
	static String getFileContent(String path) throws IOException {
		return IOUtils.toString(GraphReaderTest.class.getResourceAsStream(path), Charset.defaultCharset());
	}
	
}
