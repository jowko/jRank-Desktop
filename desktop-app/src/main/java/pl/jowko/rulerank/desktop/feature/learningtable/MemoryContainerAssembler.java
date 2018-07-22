package pl.jowko.rulerank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.core.ContainerFailureException;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Example;

/**
 * Created by Piotr on 2018-05-15.
 * This class helps to convert LearningTable to MemoryContainer.
 * Provided LearningTable should contain only jRS field types.
 * No wrapper fields are allowed.
 * @see pl.jowko.rulerank.desktop.feature.learningtable.wrappers.JRSFieldsReplacer
 * @see LearningTable
 * @see MemoryContainer
 */
public class MemoryContainerAssembler {
	
	private MemoryContainerAssembler() {}
	
	/**
	 * Creates MemoryContainer object from LearningTable object.
	 * Learning table should contain only jRS field types.
	 * No wrapper fields are allowed.
	 * @see pl.jowko.rulerank.desktop.feature.learningtable.wrappers.JRSFieldsReplacer
	 * @param table with raw jRS fields
	 * @return MemoryContainer created from provided table
	 * @throws ContainerFailureException when creating of container will fail
	 */
	public static MemoryContainer assembleContainerFromTable(LearningTable table) throws ContainerFailureException {
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
