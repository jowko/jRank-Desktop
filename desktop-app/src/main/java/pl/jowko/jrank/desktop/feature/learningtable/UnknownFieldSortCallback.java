package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import pl.poznan.put.cs.idss.jrs.types.Field;

import java.util.Comparator;

/**
 * Created by Piotr on 2018-05-25
 * This class is used to change sort behavior in learning data table.
 * When table contains unknown fields, sorting results in exception.
 * jRS fields in compareTo method throws exception, when at least one of field has unknown value.
 * This class "wraps" sorting of this fields to fix this issue.
 * It checks if any of compared fields is unknown.
 * When one of the fields is unknown, object will be compared manually in this class.
 * If values for this fields are known, this class uses comparators from jRS fields.
 * @see LearningTableController
 */
class UnknownFieldSortCallback implements Callback<TableView<ObservableList<Field>>, Boolean> {
	
	private ObservableList<ObservableList<Field>> tableItems;
	
	/**
	 * Creates instance of this class.
	 * @param tableItems from learning table with will be sorted
	 */
	UnknownFieldSortCallback(ObservableList<ObservableList<Field>> tableItems) {
		this.tableItems = tableItems;
	}
	
	@Override
	public Boolean call(TableView<ObservableList<Field>> param) {
		Comparator<ObservableList<Field>> comparator = (f1, f2) -> {
			if (param.getComparator() == null) {
				return 0;
			}
			
			for(TableColumn column : param.getSortOrder()) {
				int index = ((AttributeTableColumn)column).getIndex();
				Field field1 = f1.get(index);
				Field field2 = f2.get(index);
				int sortType;
				
				if(TableColumn.SortType.ASCENDING.equals(column.getSortType()))
					sortType = -1;
				else
					sortType = 1;
				
				if(field1.isUnknown() == 0 && field2.isUnknown() == 0)
					return 0;
				if(field1.isUnknown() == 0 && field2.isUnknown() != 0)
					return sortType;
				if(field1.isUnknown() != 0 && field2.isUnknown() == 0)
					return -sortType;
			}
			
			return param.getComparator().compare(f1, f2);
		};
		FXCollections.sort(tableItems, comparator);
		return true;
	}
	
}
