/**
 * This module enables graph visualisation in JavaFX technology.<br>
 *<br>
 * Most of the code from this project was copied from StackOverflow:
 * https://stackoverflow.com/a/30696075/8906826
 *<br>
 * Created by Piotr on 2018-06-02
 */
module fxgraph {
	
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	
	opens com.fxgraph;
	
	exports com.fxgraph.cells;
	exports com.fxgraph.graph;
	exports com.fxgraph.layout;
}