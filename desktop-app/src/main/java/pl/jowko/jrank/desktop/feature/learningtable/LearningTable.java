package pl.jowko.jrank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainerDescription;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Example;
import pl.poznan.put.cs.idss.jrs.types.FileInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-05-08.
 */
public class LearningTable implements Serializable {
	
	private static final long serialVersionUID = 3110217394426748894L;
	
	private List<Attribute> attributes;
	private List<Example> examples;
	private FileInfo fileInfo;
	private MemoryContainerDescription memoryContainerDescription;
	private String id;
	
	public LearningTable(MemoryContainer container) {
		attributes = new ArrayList<>();
		examples = new ArrayList<>();
		
		if(isNull(container))
			return;
		
		attributes.addAll(Arrays.asList(container.getAttributes()));
		fileInfo = container.getFileInfo();
		memoryContainerDescription = container.getMemoryContainerDescription();
		id = container.getId();
		
		examples.addAll(container.getExamples());
	}
	
	public LearningTable(FileInfo fileInfo, MemoryContainerDescription description, String id) {
		attributes = new ArrayList<>();
		examples = new ArrayList<>();
		
		this.fileInfo = fileInfo;
		this.memoryContainerDescription = description;
		this.id = id;
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LearningTable table = (LearningTable) o;
		return Objects.equals(attributes, table.attributes) &&
				Objects.equals(examples, table.examples) &&
				Objects.equals(fileInfo, table.fileInfo) &&
				Objects.equals(memoryContainerDescription, table.memoryContainerDescription) &&
				Objects.equals(id, table.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(attributes, examples, fileInfo, memoryContainerDescription, id);
	}
	
}
