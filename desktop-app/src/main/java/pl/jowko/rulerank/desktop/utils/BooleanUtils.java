package pl.jowko.rulerank.desktop.utils;

/**
 * This class contains all utils methods related with boolean values.<br>
 * <br>
 * Created by Piotr on 2018-04-18.
 */
public class BooleanUtils {
	
	private BooleanUtils() {}
	
	/**
	 * Return negation of current value. <br>
	 * It can be used like this: <br>
	 * if(not(someCondition))
	 * @param condition to negate
	 * @return negated condition
	 */
	public static boolean not(boolean condition) {
		return !condition;
	}
	
}
