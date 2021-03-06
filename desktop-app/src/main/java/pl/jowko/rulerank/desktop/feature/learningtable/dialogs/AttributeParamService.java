package pl.jowko.rulerank.desktop.feature.learningtable.dialogs;

import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.types.Attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pl.jowko.rulerank.desktop.feature.internationalization.Labels.*;

/**
 * This class provides values for ComboBoxes in attribute form. <br>
 * It also helps to translate jRS values to appropriate format on attribute form. <br>
 *  <br>
 * Created by Piotr on 2018-05-13.
 */
class AttributeParamService {
	
	private LanguageService labels;
	private List<AttributeParam> kinds;
	private List<AttributeParam> preferences;
	
	AttributeParamService() {
		labels = LanguageService.getInstance();
		initializeKinds();
		initializePreferences();
	}
	
	List<AttributeParam> getKinds() {
		return kinds;
	}
	
	List<AttributeParam> getPreferences() {
		return preferences;
	}
	
	AttributeParam getDefaultKind() {
		return kinds.get(0);
	}
	
	/**
	 * Find kind param by specified jRS int value.
	 * @param value from jRS
	 * @return found param representing kind type
	 */
	AttributeParam getKindByValue(int value) {
		Optional<AttributeParam> param =  kinds.stream()
				.filter(kind -> kind.getValue() == value)
				.findFirst();
		
		if(param.isPresent()) {
			return param.get();
		}
		RuleRankLogger.warn("Value: " + value + " for kind type not found. Setting default value.");
		return kinds.get(0);
	}
	
	/**
	 * Find preference param by specified jRS int value.
	 * @param value from jRS
	 * @return found param representing kind type
	 */
	AttributeParam getPreferenceByValue(int value) {
		Optional<AttributeParam> param =  preferences.stream()
				.filter(preference -> preference.getValue() == value)
				.findFirst();
		
		if(param.isPresent()) {
			return param.get();
		}
		RuleRankLogger.warn("Value: " + value + " for preference type not found. Setting empty value.");
		return null;
	}
	
	private void initializeKinds() {
		kinds = new ArrayList<>();
		kinds.add(new AttributeParam(labels.get(ATT_DIALOG_CONDITION), Attribute.NONE));
		kinds.add(new AttributeParam(labels.get(ATT_DIALOG_DECISION), Attribute.DECISION));
		kinds.add(new AttributeParam(labels.get(ATT_DIALOG_DESCRIPTION), Attribute.DESCRIPTION));
	}
	
	private void initializePreferences() {
		preferences = new ArrayList<>();
		preferences.add(new AttributeParam(labels.get(ATT_DIALOG_NONE), Attribute.NONE));
		preferences.add(new AttributeParam(labels.get(ATT_DIALOG_GAIN), Attribute.GAIN));
		preferences.add(new AttributeParam(labels.get(ATT_DIALOG_COST), Attribute.COST));
	}
	
}
