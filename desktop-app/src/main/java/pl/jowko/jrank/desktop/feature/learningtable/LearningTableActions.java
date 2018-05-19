package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2018-05-19.
 */
class LearningTableActions {
	
	private TableView<ObservableList<Field>> learningTable;
	private LearningTableHelper tableHelper;
	private List<Attribute> attributes;
	
	LearningTableActions(TableView<ObservableList<Field>> learningTable, List<Attribute> attributes) {
		this.learningTable = learningTable;
		this.tableHelper = new LearningTableHelper();
		this.attributes = attributes;
	}
	
	void addExampleAction() {
		if(learningTable.getColumns().size() == 0) {
			JRankLogger.info("No attributes in table. Add attributes first.");
		}
		ObservableList<Field> newFields = tableHelper.getEmptyExample(attributes);
		learningTable.getItems().add(newFields);
	}
	
	void removeSelectedExamplesAction() {
		ObservableList<ObservableList<Field>> selectedRows = learningTable.getSelectionModel().getSelectedItems();
		if(selectedRows.isEmpty()) {
			JRankLogger.info("No examples were selected. Select examples first.");
			return;
		}
		// we don't want to iterate on same collection on with we remove items
		ArrayList<ObservableList<Field>> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> learningTable.getItems().remove(row));
	}
	
	List<Attribute> getAttributes() {
		return attributes;
	}
	
}
