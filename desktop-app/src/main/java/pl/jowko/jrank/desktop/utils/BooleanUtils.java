package pl.jowko.jrank.desktop.utils;

/**
 * Created by Piotr on 2018-04-18.
 * This class contains all utils methods related with boolean values.
 */
public class BooleanUtils {
	
	private BooleanUtils() {}
	
	/**
	 * Return negation of current value.
	 * It can be used like this:
	 * if(not(someCondition))
	 * @param condition to negate
	 * @return negated condition
	 */
	public static boolean not(boolean condition) {
		return !condition;
	}
	
}
