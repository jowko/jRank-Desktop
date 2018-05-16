package pl.jowko.jrank.desktop.feature.learningtable.dialogs;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.jowko.jrank.desktop.Main;
import pl.jowko.jrank.desktop.feature.learningtable.EnumListProvider;
import pl.jowko.jrank.desktop.feature.learningtable.LearningTableController;
import pl.jowko.jrank.desktop.feature.learningtable.TableEnumField;
import pl.jowko.jrank.desktop.feature.settings.UserSettingsService;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.feature.customfx.CustomTextArea;
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
	CustomTextArea enumsField;
	@FXML
	Button saveButton;
	@FXML
	Button cancelButton;
	@FXML
	Button clearFormButton;
	
	private AttributeParamService paramService;
	private LearningTableController controller;
	private Stage stage;
	private boolean isAddAction;
	private Attribute oldAttribute;
	
	public void initializeAddAction(LearningTableController controller, Parent parent) {
		this.controller = controller;
		isAddAction = true;
		initializeAddAttributeForm();
		initializeDialog(parent, "Add attribute action");
	}
	
	public void initializeEditAction(LearningTableController controller, Parent parent, Attribute attribute) {
		this.controller = controller;
		oldAttribute = attribute;
		isAddAction = false;
		initializeAddAttributeForm();
		initializeFieldsForEdit(attribute);
		initializeDialog(parent, "Edit attribute " + attribute.getName());
	}
	
	public void saveAction() {
		if(not(isFormValid())) {
			return;
		}
		
		if(isAddAction)
			saveNewColumn();
		else
			saveEditedColumn();
		
		stage.close();
	}
	
	public void cancelAction() {
		stage.close();
	}
	
	public void clearFormAction() {
		nameField.clear();
		typeField.getSelectionModel().clearSelection();
		kindField.getSelectionModel().selectFirst();
		preferenceField.getSelectionModel().clearSelection();
		enumsField.clear();
	}
	
	private void saveNewColumn() {
		Attribute attribute = createAttributeFromForm();
		controller.createNewColumn(attribute);
		
		ObservableList<ObservableList<Field>> list = controller.getLearningTable().getItems();
		list.forEach(row -> row.add(getFieldFromForm()));
	}
	
	private void saveEditedColumn() {
		Attribute attribute = createAttributeFromForm();
		controller.editAttribute(oldAttribute, attribute);
	}
	
	private void initializeFieldsForEdit(Attribute attribute) {
		nameField.setText(attribute.getName());
		typeField.setDisable(true);
		typeField.getSelectionModel().select(getFieldTypeFromField(attribute.getInitialValue()));
		kindField.getSelectionModel().select(paramService.getKindByValue(attribute.getKind()));
		preferenceField.getSelectionModel().select(paramService.getPreferenceByValue(attribute.getPreferenceType()));
		initializeEnumFieldForEdit(attribute);
	}
	
	private void initializeEnumFieldForEdit(Attribute attribute) {
		if(attribute.getInitialValue() instanceof TableEnumField) {
			TableEnumField field = (TableEnumField)attribute.getInitialValue();
			StringBuilder builder = new StringBuilder();
			for(String value : new EnumListProvider(field).getValues()) {
				builder.append(value);
				builder.append(',');
			}
			
			enumsField.setText(builder.toString());
			enumsField.setDisable(false);
		}
	}
	
	private void initializeDialog(Parent parent, String title) {
		stage = new Stage(StageStyle.DECORATED);
		stage.setScene(new Scene(parent));
		stage.setTitle(title);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.initOwner(Main.getScene().getWindow());
		stage.showAndWait();
	}
	
	private void initializeAddAttributeForm() {
		nameField.setPattern("[A-Za-z_][A-Za-z_0-9]*");
		enumsField.setPattern("([A-Za-z_0-9]+[,]?)*");
		paramService = new AttributeParamService();
		typeField.getItems().addAll(FieldType.values());
		addFieldTypeListener();
		kindField.setItems(observableArrayList(paramService.getKinds()));
		kindField.getSelectionModel().selectFirst();
		addKindFieldListener();
		preferenceField.setItems(observableArrayList(paramService.getPreferences()));
		initializeTooltips();
	}
	
	private void addKindFieldListener() {
		kindField.valueProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals(paramService.getDefaultKind())) {
				preferenceField.setDisable(false);
			} else {
				preferenceField.getSelectionModel().clearSelection();
				preferenceField.setDisable(true);
			}
		});
	}
	
	private void addFieldTypeListener() {
		typeField.valueProperty().addListener((observable, oldValue, newValue) -> {
			if(FieldType.ENUM_FIELD.equals(typeField.getValue())) {
				enumsField.setDisable(false);
			} else {
				enumsField.setDisable(true);
				enumsField.clear();
			}
		});
	}
	
	private void initializeTooltips() {
		boolean isTooltipsEnabled = UserSettingsService.getInstance().getUserSettings().isTooltipsEnabled();
		if(isTooltipsEnabled)
			enumsField.setTooltip(new Tooltip("Write cardinal values here separated by coma."));
	}
	
	private boolean isFormValid() {
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
		if(FieldType.ENUM_FIELD.equals(typeField.getValue()) && enumsField.getText().isEmpty()) {
			errorsMsg += "Enum values should not be empty\n";
		}
		
		if(not(errorsMsg.isEmpty())) {
			DialogsService.showValidationFailedDialog("There are errors on form", errorsMsg);
		}
		
		return errorsMsg.isEmpty();
	}
	
	private Attribute createAttributeFromForm() {
		Field newField = getFieldFromForm();
		Attribute attribute = new Attribute(nameField.getText(), newField);
		attribute.setKind(kindField.getValue().getValue());
		
		if(attribute.getKind() != 0)
			attribute.setPreferenceType(0);
		else
			attribute.setPreferenceType(preferenceField.getValue().getValue());
		
		return attribute;
	}
	
	private Field getFieldFromForm() {
		switch (typeField.getValue()) {
			case STRING_FIELD:
				return new StringField();
			case INTEGER_FIELD:
				return new IntegerField();
			case CARDINAL_FIELD:
				return new CardinalField();
			case DECIMAL_FIELD:
				return new FloatField();
			case ENUM_FIELD:
				return createEnumFromForm();
		}
		JRankLogger.warn("Field type was not recognized. Setting default StringField.");
		
		return new StringField();
	}
	
	private FieldType getFieldTypeFromField(Field field) {
		if(field instanceof StringField)
			return FieldType.STRING_FIELD;
		if(field instanceof CardinalField)
			return FieldType.CARDINAL_FIELD;
		if(field instanceof IntegerField)
			return FieldType.INTEGER_FIELD;
		if(field instanceof FloatField)
			return FieldType.DECIMAL_FIELD;
		if(field instanceof TableEnumField)
			return FieldType.ENUM_FIELD;
		
		return FieldType.STRING_FIELD;
	}
	
	private TableEnumField createEnumFromForm() {
		String[] enums = enumsField.getText().split(",");
		EnumDomain domain = new EnumDomain(enums);
		return new TableEnumField(enums[0], domain);
	}
}
