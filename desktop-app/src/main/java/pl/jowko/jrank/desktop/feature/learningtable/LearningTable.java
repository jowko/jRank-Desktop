package pl.jowko.jrank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainerDescription;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Example;
import pl.poznan.put.cs.idss.jrs.types.FileInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Piotr on 2018-05-08.
 */
class LearningTable {
	
	private List<Attribute> attributes;
	private List<Example> examples;
	private FileInfo fileInfo;
	private MemoryContainerDescription memoryContainerDescription;
	private String id;
	
	LearningTable(MemoryContainer container) {
		attributes = new ArrayList<>();
		examples = new ArrayList<>();
		
		attributes.addAll(Arrays.asList(container.getAttributes()));
		fileInfo = container.getFileInfo();
		memoryContainerDescription = container.getMemoryContainerDescription();
		id = container.getId();
		
		examples.addAll(container.getExamples());
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public List<Example> getExamples() {
		return examples;
	}
	
	public void setExamples(List<Example> examples) {
		this.examples = examples;
	}
	
	public FileInfo getFileInfo() {
		return fileInfo;
	}
	
	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}
	
	public MemoryContainerDescription getMemoryContainerDescription() {
		return memoryContainerDescription;
	}
	
	public void setMemoryContainerDescription(MemoryContainerDescription memoryContainerDescription) {
		this.memoryContainerDescription = memoryContainerDescription;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
}
