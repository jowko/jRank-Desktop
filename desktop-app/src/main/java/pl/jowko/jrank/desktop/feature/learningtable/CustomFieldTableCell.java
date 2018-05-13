package pl.jowko.jrank.desktop.feature.learningtable;

import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import pl.jowko.jrank.feature.customfx.CustomTextField;
import pl.poznan.put.cs.idss.jrs.types.Field;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-11.
 */
abstract class CustomFieldTableCell<T> extends TableCell<T, Field> {
	
	CustomTextField textField;
	
	abstract void createTextField();
	
	@Override
	public void startEdit() {
		if(editableProperty().get() && not(isEmpty())){
			super.startEdit();
			createTextField();
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
			final TableView<T> table = getTableView();
			if (table != null) {
				TablePosition<T, Field> position = new TablePosition<>(getTableView(),
						getTableRow().getIndex(), getTableColumn());
				TableColumn.CellEditEvent<T, Field> editEvent = new TableColumn.CellEditEvent<>(table, position,
						TableColumn.editCommitEvent(), item);
				Event.fireEvent(getTableColumn(), editEvent);
			}
			updateItem(item, false);
			if (table != null) {
				table.edit(-1, null);
			}
		}
	}
	
	private void setTextFieldProperties() {
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		
		textField.setOnKeyPressed(key -> {
			if (key.getCode().equals(KeyCode.ESCAPE)) {
				cancelEdit();
			}
		});
	}
	
}
