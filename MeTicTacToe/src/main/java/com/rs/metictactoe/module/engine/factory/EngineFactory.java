package com.rs.metictactoe.module.engine.factory;

import java.util.Arrays;

import com.rs.metictactoe.module.engine.enums.CellPosition;
import com.rs.metictactoe.module.engine.enums.CellValue;
import com.rs.metictactoe.module.engine.enums.LineType;
import com.rs.metictactoe.module.engine.model.CellData;
import com.rs.metictactoe.module.engine.model.LineData;

public class EngineFactory {
	/// <summary>
	/// Create a new line based on line type specified
	/// </summary>
	/// <param name="lineType"></param>
	/// <returns></returns>
	public static LineData CreateLine(LineType lineType) {
		//
		// every line has 3 cell data.
		// position and value vary based on the line type
		//
		LineData lineData = new LineData(lineType);
		CellData firstCellData = new CellData(CellValue.First);
		CellData secondCellData = new CellData(CellValue.Second);
		CellData thirdCellData = new CellData(CellValue.Third);

		switch (lineType) {
		case A1_A2_A3:
			//
			// first cell, second and third cell
			//
			firstCellData.setPosition(CellPosition.A1);
			secondCellData.setPosition(CellPosition.A2);
			thirdCellData.setPosition(CellPosition.A3);
			break;
		case B1_B2_B3:
			//
			// first cell, second and third cell
			//
			firstCellData.setPosition(CellPosition.B1);
			secondCellData.setPosition(CellPosition.B2);
			thirdCellData.setPosition(CellPosition.B3);
			break;
		case C1_C2_C3:
			//
			// first cell, second and third cell
			//
			firstCellData.setPosition(CellPosition.C1);
			secondCellData.setPosition(CellPosition.C2);
			thirdCellData.setPosition(CellPosition.C3);
			break;
		case A1_B1_C1:
			//
			// first cell, second and third cell
			//
			firstCellData.setPosition(CellPosition.A1);
			secondCellData.setPosition(CellPosition.B1);
			thirdCellData.setPosition(CellPosition.C1);
			break;
		case A2_B2_C2:
			//
			// first cell, second and third cell
			//
			firstCellData.setPosition(CellPosition.A2);
			secondCellData.setPosition(CellPosition.B2);
			thirdCellData.setPosition(CellPosition.C2);
			break;

		case A3_B3_C3:
			firstCellData.setPosition(CellPosition.A3);
			secondCellData.setPosition(CellPosition.B3);
			thirdCellData.setPosition(CellPosition.C3);
			break;
		case A1_B2_C3:
			//
			// first cell, second and third cell
			//
			firstCellData.setPosition(CellPosition.A1);
			secondCellData.setPosition(CellPosition.B2);
			thirdCellData.setPosition(CellPosition.C3);
			break;
		case A3_B2_C1:
			//
			// first cell, second and third cell
			//
			firstCellData.setPosition(CellPosition.A3);
			secondCellData.setPosition(CellPosition.B2);
			thirdCellData.setPosition(CellPosition.C1);
			break;
		default:
			return null;

		}
		//
		// add the list of cell and return the line data
		//
		lineData.getCellDataList().addAll(Arrays.asList(firstCellData, secondCellData, thirdCellData));
		return lineData;
	}
}
