package pl.jowko.jrank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.core.ContainerFailureException;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Example;

/**
 * Created by Piotr on 2018-05-15.
 */
class MemoryContainerAssembler {
	
	static MemoryContainer assembleContainerFromTable(LearningTable table) throws ContainerFailureException {
		MemoryContainer container = new MemoryContainer();
		
		container.setAttributes(table.getAttributes().toArray(new Attribute[]{}));
		container.setFileInfo(table.getFileInfo());
		container.setMemoryContainerDescription(table.getMemoryContainerDescription());
		container.setId(table.getId());
		
		for(Example example: table.getExamples()) {
			container.addExample(example);
		}
		
		return container;
	}
	
}
