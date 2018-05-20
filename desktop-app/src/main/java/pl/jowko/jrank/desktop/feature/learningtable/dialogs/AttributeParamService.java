package pl.jowko.jrank.desktop.feature.learningtable.dialogs;

import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.types.Attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Piotr on 2018-05-13.
 */
class AttributeParamService {
	
	private List<AttributeParam> kinds;
	private List<AttributeParam> preferences;
	
	AttributeParamService() {
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
	
	AttributeParam getKindByValue(int value) {
		Optional<AttributeParam> param =  kinds.stream()
				.filter(kind -> kind.getValue() == value)
				.findFirst();
		
		if(param.isPresent()) {
			return param.get();
		}
		JRankLogger.warn("Value: " + value + " for kind type not found. Setting default value.");
		return kinds.get(0);
	}
	
	AttributeParam getPreferenceByValue(int value) {
		Optional<AttributeParam> param =  preferences.stream()
				.filter(preference -> preference.getValue() == value)
				.findFirst();
		
		if(param.isPresent()) {
			return param.get();
		}
		JRankLogger.warn("Value: " + value + " for preference type not found. Setting empty value.");
		return null;
	}
	
	private void initializeKinds() {
		kinds = new ArrayList<>();
		kinds.add(new AttributeParam("Condition", Attribute.NONE));
		kinds.add(new AttributeParam("Decision", Attribute.DECISION));
		kinds.add(new AttributeParam("Description", Attribute.DESCRIPTION));
	}
	
	private void initializePreferences() {
		preferences = new ArrayList<>();
		preferences.add(new AttributeParam("None", Attribute.NONE));
		preferences.add(new AttributeParam("Gain", Attribute.GAIN));
		preferences.add(new AttributeParam("Cost", Attribute.COST));
	}
	
}
