package pl.jowko.jrank.desktop.feature.learningtable.dialogs;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<AttributeParam> getKinds() {
		return kinds;
	}
	
	public List<AttributeParam> getPreferences() {
		return preferences;
	}
	
	public AttributeParam getDefaultKind() {
		return kinds.get(0);
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
