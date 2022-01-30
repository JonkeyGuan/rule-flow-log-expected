package com.example.rule_flow_log_expected;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class Result implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private java.lang.String bizLogicResult;
	private java.util.List<java.lang.String> track;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (String t : track) {
			sb.append(t.toString() + "\n");
		}
		return "Result [bizLogicResult is: " + bizLogicResult + ", \ntrack is below::\n" + sb.toString() + "]";
	}

	public Result() {
	}

	public java.lang.String getBizLogicResult() {
		return this.bizLogicResult;
	}

	public void setBizLogicResult(java.lang.String bizLogicResult) {
		this.bizLogicResult = bizLogicResult;
	}

	public java.util.List<java.lang.String> getTrack() {
		return this.track;
	}

	public void setTrack(java.util.List<java.lang.String> track) {
		this.track = track;
	}

	public Result(java.lang.String bizLogicResult,
			java.util.List<java.lang.String> track) {
		this.bizLogicResult = bizLogicResult;
		this.track = track;
	}

}