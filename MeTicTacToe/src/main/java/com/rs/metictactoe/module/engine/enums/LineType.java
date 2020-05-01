package com.rs.metictactoe.module.engine.enums;

/**
 * represents the different lines on the board. 9 total.
 * 
 * @author eromu_000
 *
 */
public enum LineType {
	default_line,
	/// <summary>
	/// topleft to bottom
	/// </summary>
	A1_A2_A3,
	/// <summary>
	/// centertop to bottom
	/// </summary>
	B1_B2_B3,
	/// <summary>
	/// topright to bottom
	/// </summary>
	C1_C2_C3,

	/// <summary>
	/// topleft to right
	/// </summary>
	A1_B1_C1,
	/// <summary>
	/// centerleft to right
	/// </summary>
	A2_B2_C2,
	/// <summary>
	/// bottomleft to right
	/// </summary>
	A3_B3_C3,

	/// <summary>
	/// topleft to bottomright
	/// </summary>
	A1_B2_C3,
	/// <summary>
	/// bottomleft to topright
	/// </summary>
	A3_B2_C1
}
