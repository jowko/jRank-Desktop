package com.fxgraph.layout;

import com.fxgraph.graph.Cell;
import com.fxgraph.graph.Graph;

import java.util.List;
import java.util.Random;

/**
 * This code was copied from StackOverflow:
 * https://stackoverflow.com/a/30696075/8906826
 *<br>
 * This layout puts graph nodes in random positions on screen.
 */
public class RandomLayout extends Layout {
	
	Graph graph;
	
	Random rnd = new Random();
	
	public RandomLayout(Graph graph) {
		
		this.graph = graph;
		
	}
	
	public void execute() {
		
		List<Cell> cells = graph.getModel().getAllCells();
		
		for (Cell cell : cells) {
			
			double x = rnd.nextDouble() * 500;
			double y = rnd.nextDouble() * 500;
			
			cell.relocate(x, y);
			
		}
		
	}
	
}
