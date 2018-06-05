package pl.jowko.jrank.desktop.feature.properties.information;

import pl.poznan.put.cs.idss.jrs.ranking.PairOfIndices;

/**
 * Created by Piotr on 2018-06-04
 * This class wraps PairOfIndices class.
 * It adds relation variable to it.
 * In jRS, pair items and relations are stored in different variables.
 * This class enables storing this in one place.
 * @see PairOfIndices
 */
public class PairOfIndicesWrapper {
	
	private PairOfIndices pair;
	private double relation;
	
	/**
	 * Creates instance of this class
	 * @param pair containing object indexes
	 * @param relation between pair of objects
	 */
	public PairOfIndicesWrapper(PairOfIndices pair, double relation) {
		this.pair = pair;
		this.relation = relation;
	}
	
	public PairOfIndices getPair() {
		return pair;
	}
	
	public double getRelation() {
		return relation;
	}
	
}
