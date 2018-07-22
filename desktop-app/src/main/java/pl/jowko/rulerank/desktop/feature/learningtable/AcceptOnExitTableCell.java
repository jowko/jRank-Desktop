package pl.jowko.rulerank.desktop.feature.learningtable;

import javafx.scene.control.TableView;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-18.
 * This class fixes bug related with not committing value after loosing focus in field in JavaFX.
 * See: https://stackoverflow.com/a/33475309/8906826
 * Its extend standard JavaFX table column and CustomFieldTableCell to provide custom fields functionality.
 * @see CustomFieldTableCell
 */
abstract class AcceptOnExitTableCell <T> extends CustomFieldTableCell<T> {
	
	protected abstract Field getField();

	@Override
	public void startEdit() {
		if (! isEditable()
				|| ! getTableView().isEditable()
				|| ! getTableColumn().isEditable()) {
			return;
		}
		super.startEdit();

		if (isEditing()) {
			if (textField == null) {
				createTextField();
			}
			escapePressed = false;
		}
	}
	
	@Override
	public void commitEdit(Field newValue) {
		if (not(isEditing()))
			return;

		final TableView<T> table = handleCellEdit(newValue);
		// we need to setEditing(false):
		super.cancelEdit();
		
		updateItem(newValue, false);
		if (table != null) {
			table.edit(-1, null);
		}
	}

	@Override
	public void cancelEdit() {
		if(escapePressed) {
			super.cancelEdit();
			setText(valueBeforeEdit); // restore the original text in the view
		}
		else {
			// this is not a cancel event after escape key
			// we interpret it as commit.
			this.commitEdit(getField());
		}
		setGraphic(null); // stop editing with TextField
	}
	
}
