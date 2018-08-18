package com.fxgraph.layout;

import com.fxgraph.graph.Cell;
import com.fxgraph.graph.Graph;

import java.util.List;

/**
 * This layout draws all nodes in one circle.<br>
 * Created by Piotr on 2018-06-06
 */
public class CircularLayout extends Layout {
	
	private Graph graph;
	
	public CircularLayout(Graph graph) {
		this.graph = graph;
	}
	
	@Override
	public void execute() {
		List<Cell> cells = graph.getModel().getAllCells();
		if(cells.isEmpty())
			return;
		
		double circumference = 100 * cells.size();
		double radius = circumference / Math.PI;
		
		double angle = 360/cells.size();
		double currentAngle = 0;
		
		for(Cell cell : cells) {
			double x = 50 + radius + radius * Math.cos(currentAngle * Math.PI / 180F);
			double y = 50 + radius + radius * Math.sin(currentAngle * Math.PI / 180F);
			currentAngle += angle;
			cell.relocate(x, y);
		}
		
	}
	
}
