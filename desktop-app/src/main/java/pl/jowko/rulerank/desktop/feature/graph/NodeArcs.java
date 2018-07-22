package pl.jowko.rulerank.desktop.feature.graph;

/**
 * Created by Piotr on 2018-07-07
 * This class contains data displayed in Arcs tab.
 * @see ArcsTab
 * @see NodeArcsAssembler
 */
class NodeArcs {
	
	private String nodeId;
	private String outS;
	private String outSc;
	private String inS;
	private String inSc;
	
	/**
	 * Creates instance of this class.
	 * This class is used to display data in ArcsTab
	 * @param nodeId with is used in tab header
	 * @param outS representing outgoing relation S row
	 * @param outSc representing outgoing relation Sc row
	 * @param inS representing ingoing relation S row
	 * @param inSc representing ingoing relation Sc row
	 */
	NodeArcs(String nodeId, String outS, String outSc, String inS, String inSc) {
		this.nodeId = nodeId;
		this.outS = outS;
		this.outSc = outSc;
		this.inS = inS;
		this.inSc = inSc;
	}
	
	String getNodeId() {
		return nodeId;
	}
	
	String getOutS() {
		return outS;
	}
	
	String getOutSc() {
		return outSc;
	}
	
	String getInS() {
		return inS;
	}
	
	String getInSc() {
		return inSc;
	}
	
}
