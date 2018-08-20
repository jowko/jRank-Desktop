package pl.jowko.rulerank.desktop.feature.tabs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * This class adds change listener to any text input. <br>
 * If any change in fields occurs, this class marks tab as edited. <br>
 * After this listener removes itself. <br>
 * <br>
 * Created by Piotr on 2018-08-08
 */
public class RemovableChangeListener <T> implements ChangeListener<T> {
	
	private RuleRankTab tab;
	private ObservableValue<T> value;
	
	/**
	 * Create instance of this class
	 * @param tab with will be marked as edited when change event occurs
	 * @param observableValue on with this listener will be added
	 */
	public RemovableChangeListener(RuleRankTab tab, ObservableValue<T> observableValue) {
		this.tab = tab;
		this.value = observableValue;
	}
	
	@Override
	public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
		tab.setTabEdited(true);
		value.removeListener(this);
	}
	
}
