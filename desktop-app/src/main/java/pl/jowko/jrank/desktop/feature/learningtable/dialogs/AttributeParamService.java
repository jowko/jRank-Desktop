package pl.jowko.jrank.desktop.feature.learningtable.dialogs;

import pl.jowko.jrank.logger.JRankLogger;

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
		kinds.add(new AttributeParam("Field", 0));
		kinds.add(new AttributeParam("Decision", 1));
		kinds.add(new AttributeParam("Description", 2));
	}
	
	private void initializePreferences() {
		preferences = new ArrayList<>();
		preferences.add(new AttributeParam("Gain", 1));
		preferences.add(new AttributeParam("Cost", 2));
	}
	
}
