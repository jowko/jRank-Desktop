package pl.jowko.rulerank.desktop.utils;

import pl.jowko.rulerank.logger.RuleRankLogger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class provides utils methods related with cloning.<br>
 * All cloned objects should implement Serializable interface<br>
 * <br>
 * Created by Piotr on 2018-05-04.
 * @see java.io.Serializable
 */
public class Cloner {
	
	private Cloner() {}
	
	/**
	 * This method makes a "deep clone" of any Java object it is given. <br>
	 * All provided objects should implement Serializable interface
	 * @see java.io.Serializable
	 * https://alvinalexander.com/java/java-deep-clone-example-source-code
	 */
	public static Object deepClone(Object object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		}
		catch (Exception e) {
			RuleRankLogger.error("Error when making deep copy of object: " + e);
			return null;
		}
	}
	
}
