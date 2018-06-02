package com.fxgraph.cells;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import com.fxgraph.graph.Cell;

/**
 * This code was copied from StackOverflow:
 * https://stackoverflow.com/a/30696075/8906826
 */
public class TriangleCell extends Cell {
	
	public TriangleCell( String id) {
		super( id);
		
		double width = 50;
		double height = 50;
		
		Polygon view = new Polygon( width / 2, 0, width, height, 0, height);
		
		view.setStroke(Color.RED);
		view.setFill(Color.RED);
		
		setView( view);
		
	}
	
}
