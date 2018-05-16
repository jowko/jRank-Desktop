package pl.jowko.jrank.desktop.feature.learningtable;

import org.junit.jupiter.api.Test;
import pl.poznan.put.cs.idss.jrs.core.ContainerFailureException;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Piotr on 2018-05-16.
 */
class MemoryContainerAssemblerTest {
	
	@Test
	void shouldAssembleContainerFromLearningTable() throws ContainerFailureException {
		LearningTable table = LearningTableDataProvider.createLearningTable();
		MemoryContainer container = MemoryContainerAssembler.assembleContainerFromTable(table);
		
		List<Attribute> attributes = Arrays.asList(container.getAttributes());
		assertTrue(table.getAttributes().containsAll(attributes));
		assertTrue(table.getExamples().containsAll(container.getExamples()));
	}
	
}
