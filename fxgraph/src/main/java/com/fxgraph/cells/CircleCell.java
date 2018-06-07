package com.fxgraph.cells;

import com.fxgraph.graph.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by Piotr on 2018-06-02
 */
public class CircleCell extends Cell {
	
	private String text;
	
	public CircleCell(String cellId) {
		this(cellId, null, Color.RED);
	}
	
	public CircleCell(String cellId, String text, Color color) {
		super(cellId);
		
		Circle circle = new Circle(25, 25, 25);
		circle.setStroke(color);
		circle.setFill(color);
		
		setView(circle);
	}
	
}
