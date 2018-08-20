package com.fxgraph.graph;

/**
 * This interface is used to perform mouse click action on graph node.<br>
 * Created by Piotr on 2018-06-24
 */
public interface MouseClickAction {
	
	/**
	 * Perform action when node is clicked
	 * @param cell with is clicked
	 */
	void performAction(Cell cell);
	
}
