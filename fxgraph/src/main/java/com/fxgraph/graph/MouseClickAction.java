package com.fxgraph.graph;

/**
 * Created by Piotr on 2018-06-24
 * This interface is used to perform mouse click action on graph node.
 */
public interface MouseClickAction {
	
	/**
	 * Perform action when node is clicked
	 * @param cell with is clicked
	 */
	void performAction(Cell cell);
	
}
