package pl.jowko.rulerank.desktop.feature.internationalization;

/**
 * This interface is used as marker for classes related with translating UI components. <br>
 * Add it to any class that perform translation of UI components. <br>
 *  <br>
 * Created by Piotr on 2018-05-20.
 */
@FunctionalInterface
public interface Translator {
	
	/**
	 * This method should translate all necessary UI components. <br>
	 * All fields or controller should be passed in setters or constructor.
	 */
	void translateFields();
	
}
