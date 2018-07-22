package pl.jowko.rulerank.desktop.feature.learningtable;

import javafx.event.Event;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import pl.jowko.rulerank.feature.customfx.CustomTextField;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-11.
 * This class extends standard JavaFX TableColumn.
 * This class provides support for custom TextField in edited table cell.
 * Most of this code was copied from TableCell and related classes, because it doesn't support extending very well.
 * @see CustomTextField
 */
abstract class CustomFieldTableCell<T> extends TableCell<T, Field> {
	
	boolean escapePressed = false;
	CustomTextField textField;
	String valueBeforeEdit;
	
	abstract void createTextField();
	
	@Override
	public void startEdit() {
		if(editableProperty().get() && not(isEmpty())){
			super.startEdit();
			createTextField();
			valueBeforeEdit = getString();
			setTextFieldProperties();
			setText(null);
			setGraphic(textField);
			textField.requestFocus();
		}
	}
	
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText(getItem() != null ? getItem().toString() : null);
		setGraphic(null);
	}
	
	@Override
	public void updateItem(Field item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (textField != null) {
					textField.setText(getString());
					textField.selectAll();
				}
				setText(null);
				setGraphic(textField);
			} else {
				setText(getString());
				setGraphic(null);
			}
		}
	}
	
	String getString() {
		return getItem() == null ? "" : getItem().toString();
	}
	
	@Override
	public void commitEdit(Field item) {
		if (isEditing()) {
			super.commitEdit(item);
		} else {
			final TableView<T> table = handleCellEdit(item);
			updateItem(item, false);
			if (table != null) {
				table.edit(-1, null);
			}
		}
	}
	
	TableView<T> handleCellEdit(Field item) {
		TableView<T> table = getTableView();
		if (table != null) {
			TablePosition<T, Field> position = new TablePosition<>(getTableView(),
					getTableRow().getIndex(), getTableColumn());
			TableColumn.CellEditEvent<T, Field> editEvent = new TableColumn.CellEditEvent<>(table, position,
					TableColumn.editCommitEvent(), item);
			Event.fireEvent(getTableColumn(), editEvent);
		}
		return table;
	}
	
	private void setTextFieldProperties() {
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		
		textField.setOnKeyPressed(key -> {
			if (key.getCode().equals(KeyCode.ESCAPE)) {
				escapePressed = true;
				cancelEdit();
			}
			escapePressed = false;
		});
	}
	
}
