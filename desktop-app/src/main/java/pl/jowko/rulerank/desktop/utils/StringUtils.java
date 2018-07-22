package pl.jowko.rulerank.desktop.utils;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-05.
 * This class contains utils method related with String processing.
 */
public class StringUtils {
	
	private StringUtils() {}
	
	/**
	 * Checks if provided String is not null and not empty.
	 * It treats String with whitespaces only as empty.
	 * @param value to check
	 * @return true if string is not null and empty, false otherwise
	 */
	public static boolean isNotNullOrEmpty(String value) {
		return nonNull(value) && not(value.isEmpty()) && not(value.trim().isEmpty());
	}
	
	/**
	 * Checks if provided String is null or empty.
	 * it treats String with whitespaces only as empty
	 * @param value to check
	 * @return true if string is null or empty, false otherwise
	 */
	public static boolean isNullOrEmpty(String value) {
		return isNull(value) || value.isEmpty() || value.trim().isEmpty();
	}
	
}
