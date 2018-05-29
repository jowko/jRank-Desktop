package pl.jowko.jrank.desktop.feature.properties.information;

import pl.jowko.jrank.feature.customfx.AbstractDialogForm;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

/**
 * Created by Piotr on 2018-05-29
 * This class contains base functionality used both in ranking and pairs controllers.
 * Both controllers are used in modal window on with learning information is configured.
 * @see PropertiesPairsController
 * @see PropertiesRankingController
 */
public class AbstractInformationController extends AbstractDialogForm {
	
	protected MemoryContainer container;
	
	public void initializeForm(MemoryContainer container) {
		this.container = container;
	}
	
}
