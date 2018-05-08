/**
 * 
 */
package com.agent.domain;

/**
 * @author Vanja
 *
 */
public enum Level {
	INFORMATION(4),
	WARNING(2),
	ERROR(1);

	private int val;
	
	private Level(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}
}
