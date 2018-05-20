package pl.jowko.jrank.desktop.feature.internationalization;

/**
 * Created by Piotr on 2018-05-20.
 * This interface is used as marker for classes related with translating UI components.
 * Add it to any class that perform translation of UI components.
 */
@FunctionalInterface
public interface Translator {
	
	/**
	 * This method should translate all necessary UI components.
	 * All fields or controller should be passed in setters or constructor.
	 */
	void translateFields();
	
}
