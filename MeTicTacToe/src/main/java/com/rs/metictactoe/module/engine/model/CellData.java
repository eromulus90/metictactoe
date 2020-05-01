package com.rs.metictactoe.module.engine.model;

import com.rs.metictactoe.module.engine.enums.CellPosition;
import com.rs.metictactoe.module.engine.enums.CellValue;

public class CellData {
	private CellPosition position;
	private CellValue value;
	private String user;

	
	public CellData(CellValue value) {
		this.value = value;
	}

	public CellPosition getPosition() {
		return position;
	}

	public void setPosition(CellPosition position) {
		this.position = position;
	}

	public CellValue getValue() {
		return value;
	}

	public void setValue(CellValue value) {
		this.value = value;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
