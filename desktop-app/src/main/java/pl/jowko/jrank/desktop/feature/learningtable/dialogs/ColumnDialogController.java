package pl.jowko.jrank.desktop.feature.learningtable.dialogs;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.jowko.jrank.desktop.Main;
import pl.jowko.jrank.desktop.feature.learningtable.LearningTableController;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.feature.customfx.StringTextField;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.types.*;

import static java.util.Objects.isNull;
import static javafx.collections.FXCollections.observableArrayList;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-13.
 */
public class ColumnDialogController {
	
	@FXML
	Label nameLabel;
	@FXML
	StringTextField nameField;
	@FXML
	Label typeLabel;
	@FXML
	ComboBox<FieldType> typeField;
	@FXML
	Label kindLabel;
	@FXML
	ComboBox<AttributeParam> kindField;
	@FXML
	Label preferenceLabel;
	@FXML
	ComboBox<AttributeParam> preferenceField;
	@FXML
	Label enumsLabel;
	@FXML
	TextArea enumsField;
	@FXML
	Button saveButton;
	@FXML
	Button cancelButton;
	@FXML
	Button clearFormButton;
	
	private AttributeParamService paramService;
	private LearningTableController controller;
	private Stage stage;
	
	public void initializeDialog(LearningTableController controller, Parent parent) {
		this.controller = controller;
		initializeAddAttributeForm();
		
		stage = new Stage(StageStyle.DECORATED);
		stage.setScene(new Scene(parent));
		stage.setTitle("Add new attribute");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.initOwner(Main.getScene().getWindow());
		stage.showAndWait();
	}
	
	public void addNewColumnAction() {
		if(not(isAddNewColumnFormValid())) {
			return;
		}
		Attribute attribute = createAttributeFromForm();
		controller.createNewColumn(attribute);
		
		ObservableList<ObservableList<Field>> list = controller.getLearningTable().getItems();
		list.forEach(row -> row.add(getFieldTypeFromForm()));
		stage.close();
	}
	
	public void cancelAction() {
		stage.close();
	}
	
	public void clearFormAction() {
		nameField.setText("");
		typeField.getSelectionModel().clearSelection();
		kindField.getSelectionModel().selectFirst();
		preferenceField.getSelectionModel().clearSelection();
		enumsField.setText("");
	}
	
	private void initializeAddAttributeForm() {
		nameField.setPattern("[A-Za-z_][A-Za-z_0-9]*");
		paramService = new AttributeParamService();
		typeField.getItems().addAll(FieldType.values());
		kindField.setItems(observableArrayList(paramService.getKinds()));
		kindField.getSelectionModel().selectFirst();
		addKindFieldListener();
		preferenceField.setItems(observableArrayList(paramService.getPreferences()));
	}
	
	private void addKindFieldListener() {
		kindField.valueProperty().addListener((observable, oldValue, newValue) -> {
			if(not(newValue.equals(paramService.getDefaultKind()))) {
				preferenceField.getSelectionModel().clearSelection();
				preferenceField.setDisable(true);
			} else {
				preferenceField.setDisable(false);
			}
		});
	}
	
	private boolean isAddNewColumnFormValid() {
		String errorsMsg = "";
		if(nameField.getText().trim().isEmpty()) {
			errorsMsg += "Name for attribute should not be empty\n";
		}
		if(isNull(typeField.getValue())) {
			errorsMsg += "Field type should be set\n";
		}
		if(isNull(preferenceField.getValue()) && kindField.getValue().getValue() == 0) {
			errorsMsg += "Preference type should be set\n";
		}
		
		if(not(errorsMsg.isEmpty())) {
			new DialogsService().showValidationFailedDialog("There are errors on add new attribute form", errorsMsg);
		}
		
		return errorsMsg.isEmpty();
	}
	
	private Attribute createAttributeFromForm() {
		Field newField = getFieldTypeFromForm();
		Attribute attribute = new Attribute(nameField.getText(), newField);
		attribute.setKind(kindField.getValue().getValue());
		
		if(attribute.getKind() != 0)
			attribute.setPreferenceType(0);
		else
			attribute.setPreferenceType(preferenceField.getValue().getValue());
		
		return attribute;
	}
	
	private Field getFieldTypeFromForm() {
		switch (typeField.getValue()) {
			case STRING_FIELD:
				return new StringField();
			case INTEGER_FIELD:
				return new IntegerField();
			case DECIMAL_FIELD:
				return new FloatField();
			case ENUM_FIELD:
				return new StringField(); //TODO finish him
		}
		JRankLogger.warn("Field type was not recognized. Setting default StringField.");
		
		return new StringField();
	}
}
