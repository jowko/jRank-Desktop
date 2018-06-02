package com.fxgraph.cells;

import com.fxgraph.graph.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by Piotr on 2018-06-02
 */
public class CircleCell extends Cell {
	
	public CircleCell(String cellId) {
		super(cellId);
		
		Circle circle = new Circle(25, 25, 25);
		circle.setStroke(Color.RED);
		circle.setFill(Color.RED);
		
		setView(circle);
	}
	
}
