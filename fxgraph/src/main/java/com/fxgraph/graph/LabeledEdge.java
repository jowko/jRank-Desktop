package com.fxgraph.graph;

import javafx.scene.paint.Color;

import static java.util.Objects.nonNull;

/**
 * Edge containing label inside, with can be extracted later. <br>
 * <br>
 * Created by Piotr on 2018-08-22
 */
public class LabeledEdge extends Edge {
	
	private String label;
	private String secondLabel;
	
	public LabeledEdge(Cell source, Cell target, String label, String secondLabel, Color color) {
		super(source, target, color);
		this.label = label;
		this.secondLabel = secondLabel;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getSecondLabel() {
		return secondLabel;
	}
	
	public boolean hasSecondLabel() {
		return nonNull(secondLabel) && !secondLabel.isEmpty();
	}
	
}
