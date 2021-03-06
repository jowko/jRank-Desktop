package pl.jowko.rulerank.desktop.feature.learningtable;

import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Example;
import pl.poznan.put.cs.idss.jrs.types.Field;

import java.util.List;

/**
 * In isf table, only decision attribute can have unknown field values in experiment. <br>
 * But isf supports setting all fields to unknown values, so user should have such options. <br>
 * This class validates, if any non decision field in Learning table has unknown value set. <br>
 *  <br>
 * Created by Piotr on 2018-05-27
 */
public class UnknownFieldValidator {
	
	private boolean isValid;

	public UnknownFieldValidator(LearningTable table) {
		isValid = true;
		validate(table);
	}
	
	public boolean isValid() {
		return isValid;
	}
	
	private void validate(LearningTable table) {
		List<Attribute> attributes = table.getAttributes();
		List<Example> examples = table.getExamples();
		for (Example example : examples) {
			if (isExampleInvalid(example.getFields(), attributes)) {
				isValid = false;
				break;
			}
		}
	}
	
	/**
	 * Check if fields from example have unknown value. <br>
	 * Decision attributes are excluded from validation, because they can contain unknown values.
	 * @param fields to validate with are extracted from example
	 * @param attributes with are used for validation
	 * @return true if example is invalid, false otherwise
	 */
	private boolean isExampleInvalid(Field[] fields, List<Attribute> attributes) {
		for(int i=0; i<attributes.size(); i++) {
			if(attributes.get(i).getKind() == Attribute.DECISION)
				continue;
			
			if(fields[i].isUnknown() == 0) {
				return true;
			}
		}
		return false;
	}

}
