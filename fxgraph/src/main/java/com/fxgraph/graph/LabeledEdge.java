package com.fxgraph.graph;

import javafx.scene.paint.Color;

/**
 * Edge containing label inside, with can be extracted later. <br>
 * <br>
 * Created by Piotr on 2018-08-22
 */
public class LabeledEdge extends Edge {
	
	private String label;
	
	public LabeledEdge(Cell source, Cell target, String label, Color color) {
		super(source, target, color);
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
}
