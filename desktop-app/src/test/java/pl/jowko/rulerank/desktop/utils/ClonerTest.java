package pl.jowko.rulerank.desktop.utils;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Piotr on 2018-05-04.
 */
class ClonerTest {
	
	@Test
	void shouldMakeValidCopy() {
		NestedObject nestedObject = new NestedObject("someName");
		ClonedObject clonedObject = new ClonedObject(33d, 1, nestedObject);
		
		ClonedObject deepCopy = (ClonedObject) Cloner.deepClone(clonedObject);
		
		assertEquals(clonedObject, deepCopy);
	}
	
	@Test
	void shouldMakeDeepCopy() {
		NestedObject nestedObject = new NestedObject("someName");
		ClonedObject clonedObject = new ClonedObject(33d, 1, nestedObject);
		
		ClonedObject deepCopy = (ClonedObject) Cloner.deepClone(clonedObject);
		clonedObject.value = 22d;
		clonedObject.primitive = 2;
		clonedObject.nestedObject = new NestedObject("someOtherName");
		
		assertEquals(deepCopy.value, Double.valueOf(33d));
		assertEquals(deepCopy.nestedObject.name, "someName");
	}
	
	private static class ClonedObject implements Serializable {
		private Double value;
		private int primitive;
		private NestedObject nestedObject;
		
		private ClonedObject(Double value, int primitive, NestedObject nestedObject) {
			this.value = value;
			this.primitive = primitive;
			this.nestedObject = nestedObject;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			ClonedObject that = (ClonedObject) o;
			return Objects.equals(value, that.value) &&
					primitive == that.primitive &&
					Objects.equals(nestedObject, that.nestedObject);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(value, primitive, nestedObject);
		}
	}
	
	private static class NestedObject implements Serializable {
		private String name;
		
		private NestedObject(String name) {
			this.name = name;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			NestedObject that = (NestedObject) o;
			return Objects.equals(name, that.name);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(name);
		}
	}
	
}
