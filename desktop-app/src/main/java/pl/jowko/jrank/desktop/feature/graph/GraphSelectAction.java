package pl.jowko.jrank.desktop.feature.graph;

import com.fxgraph.cells.CircleCell;
import com.fxgraph.graph.Cell;
import com.fxgraph.graph.MouseClickAction;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import static java.util.Objects.nonNull;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-06-24
 * This class perform select action on graph node when node is clicked.
 */
public class GraphSelectAction implements MouseClickAction {
	
	private CircleCell lastSelected;
	
	@Override
	public void performAction(Cell cell) {
		if(not(cell instanceof CircleCell))
			return;
		
		removeSelectionFromPreviousNode();
		selectNode(cell);
	}
	
	private void removeSelectionFromPreviousNode() {
		if(nonNull(lastSelected)) {
			Paint circleColor = lastSelected.getCircle().getFill();
			lastSelected.getCircle().setStroke(circleColor);
		}
	}
	
	private void selectNode(Cell cell) {
		lastSelected = (CircleCell) cell;
		lastSelected.getCircle().setStroke(Color.BLUE);
	}
	
}
