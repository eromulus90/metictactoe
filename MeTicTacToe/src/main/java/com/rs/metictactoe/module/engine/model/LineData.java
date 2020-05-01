package com.rs.metictactoe.module.engine.model;

import java.util.ArrayList;
import java.util.List;

import com.rs.metictactoe.module.engine.enums.LineType;

public class LineData {

	/// <summary>
	/// The of the line
	/// a bit mask value from 1,2, or 4
	/// max value of 7 indicates a win
	/// </summary>
	private int Value;

	/// <summary>
	/// List of cell data
	/// It has to equal to three since every line has tree cells
	/// </summary>
	private final List<CellData> CellDataList = new ArrayList<CellData>(3);

	/// <summary>
	/// The type of line
	/// </summary>
	private LineType LineType;

	/// <summary>
	/// The user who updated the line value
	/// </summary>
	private String UserId;

	/// <summary>
	/// Check if a line is shared with users
	/// </summary>
	private boolean IsShared;

	public LineData(LineType lineType) {
		this.LineType = lineType;
	}

	public int getValue() {
		return Value;
	}

	public void setValue(int value) {
		Value = value;
	}

	public LineType getLineType() {
		return LineType;
	}

	public void setLineType(LineType lineType) {
		LineType = lineType;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public boolean isIsShared() {
		return IsShared;
	}

	public void setIsShared(boolean isShared) {
		IsShared = isShared;
	}

	public List<CellData> getCellDataList() {
		return CellDataList;
	}

}
