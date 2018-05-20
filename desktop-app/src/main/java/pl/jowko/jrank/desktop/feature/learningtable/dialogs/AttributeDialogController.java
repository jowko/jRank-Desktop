package pl.jowko.jrank.desktop.feature.learningtable.dialogs;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.jowko.jrank.desktop.Main;
import pl.jowko.jrank.desktop.feature.learningtable.EnumListProvider;
import pl.jowko.jrank.desktop.feature.learningtable.LearningTableActions;
import pl.jowko.jrank.desktop.feature.learningtable.TableEnumField;
import pl.jowko.jrank.desktop.feature.settings.UserSettingsService;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.feature.customfx.CustomTextArea;
import pl.jowko.jrank.feature.customfx.StringTextField;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Field;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static javafx.collections.FXCollections.observableArrayList;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-19.
 * This class serve as controller for attributeActionDialog.fxml file.
 * It manages editing and adding attribute form.
 */
public class AttributeDialogController {
	
	@FXML
	ListView<String> attributesList;
	@FXML
	Label nameLabel;
	@FXML
	StringTextField nameField;
	@FXML
	Label activeLabel;
	@FXML
	CheckBox activeCheckBox;
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
	Button clearButton;
	
	private AttributeParamService paramService;
	private LearningTableActions actions;
	private List<Attribute> attributes;
	/**
	 * Currently edited attribute in form.
	 */
	private Attribute editedAttribute;
	private Stage stage;
	private boolean isAddAction;
	
	/**
	 * Initialize controller for edit action.
	 * @param actions object with is used as mediator between this controller and LearningDataController
	 * @param parent containing content of fxml file
	 * @param attributes list containing all current columns
	 */
	public void initializeEditAction(LearningTableActions actions, Parent parent, List<Attribute> attributes) {
		this.actions = actions;
		this.attributes = attributes;
		isAddAction = false;
		initializeAttributeForm();
		editedAttribute = attributes.get(0);
		initializeFieldsForEdit();
		initializeAttributesListView();
		initializeDialog(parent, "Customize attributes");
	}
	
	/**
	 * Initialize controller for adding action.
	 * @param actions object with is used as mediator between this controller and LearningDataController
	 * @param parent containing content of fxml file
	 */
	public void initializeAddAction(LearningTableActions actions, Parent parent) {
		this.actions = actions;
		isAddAction = true;
		attributesList.setVisible(false);
		attributesList.setMaxSize(0, 0);
		initializeAttributeForm();
		initializeDialog(parent, "Add attribute action");
	}
	
	/**
	 * Validates and save added/edited attributes. After successful action closes this dialog.
	 * Method called by saveButton.
	 */
	public void saveAction() {
		if(not(isFormValid())) {
			return;
		}
		
		if(isAddAction)
			saveNewAttribute();
		else
			saveEditedAttributes();
		
		stage.close();
	}
	
	/**
	 * Close this dialog without validation or saving.
	 * Method called by cancelButton.
	 */
	public void cancelAction() {
		stage.close();
	}
	
	/**
	 * Clear all editable fields on form.
	 * Method called by clearButton.
	 */
	public void clearFormAction() {
		nameField.clear();
		kindField.getSelectionModel().selectFirst();
		preferenceField.getSelectionModel().clearSelection();
		enumsField.clear();
		activeCheckBox.setSelected(true);
		
		if(isAddAction)
			typeField.getSelectionModel().clearSelection();
	}
	
	List<Attribute> getAttributes() {
		return attributes;
	}
	
	void setEditedAttribute(Attribute attribute) {
		editedAttribute = attribute;
	}
	
	/**
	 * Initialize form for edit action.
	 * It sets values from editedAttribute to UI fields.
	 */
	void initializeFieldsForEdit() {
		nameField.setText(editedAttribute.getName());
		activeCheckBox.setSelected(editedAttribute.getActive());
		typeField.setDisable(true);
		typeField.getSelectionModel().select(AttributeDialogHelper.getFieldTypeFromField(editedAttribute.getInitialValue()));
		kindField.getSelectionModel().select(paramService.getKindByValue(editedAttribute.getKind()));
		preferenceField.getSelectionModel().select(paramService.getPreferenceByValue(editedAttribute.getPreferenceType()));
		initializeEnumFieldForEdit(editedAttribute);
	}
	
	/**
	 * Validate if current data in form are valid.
	 * @return true if form is valid
	 */
	boolean isFormValid() {
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
	
	/**
	 * Extracts values from attribute form and create new Attribute object from them.
	 * @return attribute extracted from form
	 */
	Attribute createAttributeFromForm() {
		Field newField = AttributeDialogHelper.getFieldFromForm(typeField.getValue(), enumsField.getText());
		Attribute attribute = new Attribute(nameField.getText(), newField);
		attribute.setKind(kindField.getValue().getValue());
		attribute.setActive(activeCheckBox.isSelected());
		
		if(attribute.getKind() != 0)
			attribute.setPreferenceType(0);
		else
			attribute.setPreferenceType(preferenceField.getValue().getValue());
		
		return attribute;
	}
	
	/**
	 * Save new attribute from form and add it to Learning Data table
	 */
	private void saveNewAttribute() {
		Attribute attribute = createAttributeFromForm();
		actions.saveNewAttribute(attribute);
	}
	
	/**
	 * Save edited attributes from form to Learning Data table
	 */
	private void saveEditedAttributes() {
		int currentIndex = attributes.indexOf(editedAttribute);
		editedAttribute = createAttributeFromForm();
		attributes.set(currentIndex, editedAttribute);
		actions.saveEditedAttributes(attributes);
	}
	
	/**
	 * Fill enum field in UI with values extracted from attribute field.
	 * @param attribute with is currently edited
	 */
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
	
	/**
	 * Initialize Stage and creates modal dialog with content from parent variable.
	 * @param parent contains content of this dialog. Loaded from fxml file.
	 * @param title for dialog
	 */
	private void initializeDialog(Parent parent, String title) {
		stage = new Stage(StageStyle.DECORATED);
		stage.setScene(new Scene(parent));
		stage.setTitle(title);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.initOwner(Main.getScene().getWindow());
		stage.showAndWait();
	}
	
	/**
	 * Initialize attributes in ListView.
	 * It fills ListView component with attribute names values and select currently edited attribute.
	 */
	private void initializeAttributesListView() {
		List<String> values = attributes.stream().map(Attribute::getName).collect(Collectors.toList());
		attributesList.getItems().addAll(values);
		attributesList.getSelectionModel().select(editedAttribute.getName());
		attributesList.getSelectionModel().selectedItemProperty().addListener(new AttributeChangeListener(this));
	}
	
	/**
	 * Initialize attribute form. Common for edit and add action.
	 */
	private void initializeAttributeForm() {
		nameField.setPattern("[A-Za-z_][A-Za-z_0-9]*");
		enumsField.setPattern("([A-Za-z_0-9]+[,]?)*");
		activeCheckBox.setSelected(true);
		paramService = new AttributeParamService();
		typeField.getItems().addAll(FieldType.values());
		addFieldTypeListener();
		kindField.setItems(observableArrayList(paramService.getKinds()));
		kindField.getSelectionModel().selectFirst();
		preferenceField.setItems(observableArrayList(paramService.getPreferences()));
		initializeTooltips();
	}
	
	/**
	 * Create FieldType listener for managing enum TextArea.
	 */
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
	
}
