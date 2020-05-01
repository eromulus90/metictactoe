package com.rs.metictactoe.module.engine.enums;

/// <summary>
/// The value of the cells
/// a total of 7 indicates a win for a given user
/// </summary>
public enum CellValue {

	First(1),

	Second(2),

	Third(4);

	private final int value;

	CellValue(int value) {
		this.value = value;
	}

	/**
	 * get the current value of the enum
	 * @return
	 */
	public int getValue() {
		return this.value;
	}
	

}
