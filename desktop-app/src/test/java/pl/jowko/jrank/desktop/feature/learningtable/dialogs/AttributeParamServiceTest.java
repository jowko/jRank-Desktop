package pl.jowko.jrank.desktop.feature.learningtable.dialogs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Created by Piotr on 2018-05-16.
 */
class AttributeParamServiceTest {
	
	private AttributeParamService service;
	
	@BeforeEach
	void setUpEach() {
		service = new AttributeParamService();
	}
	
	@Test
	void shouldGetKinds() {
		List<AttributeParam> params = service.getKinds();
		assertNotNull(params);
	}
	
	@Test
	void shouldGetPreferences() {
		List<AttributeParam> params = service.getPreferences();
		assertNotNull(params);
	}
	
	@Test
	void shouldGetDefaultKind() {
		AttributeParam param = service.getDefaultKind();
		assertEquals(0, param.getValue());
	}
	
	@Test
	void shouldFindKindByValue() {
		AttributeParam param = service.getKindByValue(1);
		assertNotNull(param);
	}
	
	@Test
	void shouldNotFindKindByValueAndGetDefault() {
		AttributeParam param = service.getKindByValue(15);
		assertEquals(0, param.getValue());
	}
	
	@Test
	void shouldFindPreferenceByValue() {
		AttributeParam param = service.getPreferenceByValue(1);
		assertNotNull(param);
	}
	
	@Test
	void shouldNotFindPreferenceByValue() {
		AttributeParam param = service.getPreferenceByValue(15);
		assertNull(param);
	}
	
}