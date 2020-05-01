package com.rs.metictactoe.module.engine.component;

import java.util.ArrayList;
import java.util.List;

import com.rs.metictactoe.module.engine.enums.CellPosition;
import com.rs.metictactoe.module.engine.enums.LineType;
import com.rs.metictactoe.module.engine.enums.WinningStatus;
import com.rs.metictactoe.module.engine.factory.EngineFactory;
import com.rs.metictactoe.module.engine.model.CellData;
import com.rs.metictactoe.module.engine.model.LineData;

//********************************************************************************
//|
//COPYRIGHT (c) 2014                                     |
//Romusoft, BILLINGS,MONTANA                                         |
//ALL RIGHTS RESERVED                                    |
//|
//THIS SOFTWARE IS FURNISHED UNDER A LICENSE AND MAY BE USED AND  COPIED        |
//ONLY  IN  ACCORDANCE  WITH  THE  TERMS  OF  SUCH  LICENSE AND WITH THE        |
//INCLUSION OF THE ABOVE COPYRIGHT NOTICE.  THIS SOFTWARE OR  ANY  OTHER        |  
//COPIES  THEREOF MAY NOT BE PROVIDED OR OTHERWISE MADE AVAILABLE TO ANY        |
//OTHER PERSON.  NO TITLE TO AND OWNERSHIP OF  THE  SOFTWARE  IS  HEREBY        |
//TRANSFERRED.                                                                  |
//|
//--------------------------------------------------------------------------------
//DESCRIPTION  :  This is processor engine for the program.
//It exposes 2 methods and a property:
//1. IsGameOver to indicate a win or a draw.
//2. CheckWinningStatus on every move, to determine if there is a winner
//3. GetWinningCellPosition to determine a line that has a potential win or threat 
//
//PROGRAM      :  Tic tac toe
//MODULE       :  TTTEngine.cs
//AUTHOR       :  Author: Emmanuel Romulus, 09/20/2014
//FUNCTION     :  
//
//ENVIRONMENT  :  C#
//NOTES        :
//
//
//MODIFICATION 
//HISTORY: 
//
//********************************************************************************
public class GameEngine {

	// TAG: fields
	private static final int MAX_REQUEST = 9; // the max number of moves on the board
	private static final int WINNER = 7; // this determine a winner, it's the total for the 3 cells' bitmask

	private int requestCount; //
	private boolean gameOver; // game is over if there are no more moves or a user wins on a line
	private List<LineData> listOfLines = new ArrayList<LineData>(); // store the line for easier iteration

	// TAG-END: fields

	// TAG: constructor
	public GameEngine() {
		InitializeLines();
	}
	// TAG-END: constructors

	// TAG-END: properties
	/// <summary>
	///
	/// </summary>
	public boolean IsGameOver() {
		return gameOver;
	}
	// TAG-END: properties

	// TAG-END: private methods
	/// <summary>
	///
	/// </summary>
	private void InitializeLines() {
		//
		// Create the lines using the createline method factory
		//
		LineData A1_A2_A3 = EngineFactory.CreateLine(LineType.A1_A2_A3); // topleft to bottom
		LineData B1_B2_B3 = EngineFactory.CreateLine(LineType.B1_B2_B3); // centertop to bottom
		LineData C1_C2_C3 = EngineFactory.CreateLine(LineType.C1_C2_C3); // topright to bottom

		LineData A1_B1_C1 = EngineFactory.CreateLine(LineType.A1_B1_C1); // topleft to right
		LineData A2_B2_C2 = EngineFactory.CreateLine(LineType.A2_B2_C2); // centerleft to right
		LineData A3_B3_C3 = EngineFactory.CreateLine(LineType.A3_B3_C3); // bottomleft to right

		LineData A1_B2_C3 = EngineFactory.CreateLine(LineType.A1_B2_C3); // topleft to bottomright
		LineData A3_B2_C1 = EngineFactory.CreateLine(LineType.A3_B2_C1); // bottomleft to topright
		//
		// adding the items to a list
		// will allow us to loop for a winning status
		//
		listOfLines.add(A1_A2_A3);
		listOfLines.add(B1_B2_B3);
		listOfLines.add(C1_C2_C3);

		listOfLines.add(A1_B1_C1);
		listOfLines.add(A2_B2_C2);
		listOfLines.add(A3_B3_C3);

		listOfLines.add(A1_B2_C3);
		listOfLines.add(A3_B2_C1);

	}// TAG-END:

	// TAG: public methods

	/// <summary>
	///
	/// </summary>
	/// <param name="pos"></param>
	/// <param name="userId"></param>
	public void UpdateCell(CellPosition pos, String userId) {
		//
		// find the lines that have the target position
		// for the target user and update them
		//
		List<LineData> lineSharedByTheCell = new ArrayList<LineData>();
		for (LineData line : listOfLines) {
			CellData tempCell = line.getCellDataList().stream().filter(temp -> temp.getPosition() == pos).findFirst()
					.orElse(null);
			if (tempCell == null)
				continue;
			lineSharedByTheCell.add(line);
		}
		//
		// go through all the shared lines and update their value
		// based on the cell position
		//
		for (LineData line : lineSharedByTheCell) {
			if (userId == null || userId.isEmpty())
				continue;
			//
			// check that the user id is coming in for the first time
			//
			if (line.getUserId() == null || line.getUserId().isEmpty())
				line.setUserId(userId);
			//
			// indicate that more than one user has used this line
			//
			if (line.getUserId().equals(userId) == false)
				line.setIsShared(true);
			//
			// if the user id is different
			// do not add, some body claimed the cell already
			//
			CellData targetCellData = line.getCellDataList().stream().filter(temp -> temp.getPosition() == pos)
					.findFirst().orElse(null);
			if (targetCellData == null)
				continue;
			if (line.getUserId().equals(userId)) {
				int value = line.getValue();
				value |= (int) targetCellData.getValue().getValue();
				line.setValue(value);
			}
			//
			// update the user value on the targetcell
			//
			targetCellData.setUser(userId);
		}
		//
		// see how many times we have taken turns
		//
		this.requestCount++;
	}

	/// <summary>
	///
	/// </summary>
	/// <param name="cell"></param>
	/// <param name="userId"></param>
	public void UpdateCell(CellData cell, String userId) {
		if (cell == null)
			return;
		UpdateCell(cell.getPosition(), userId);
	}

	/**
	 * return a cell by position
	 * 
	 * @param position
	 * @return
	 * @throws Exception
	 */
	public CellData getCellData(CellPosition position) throws Exception {
		for (LineData line : listOfLines) {
			if (line.getCellDataList().size() != 3)
				throw new Exception("The number of cells per line must be three");
			//
			// get cell that has no value assigned to them yet
			//
			for (CellData cell : line.getCellDataList()) {
				if (cell.getPosition() == position)
					return cell;
			}
		}
		return null;
	}

	/// <summary>
	/// Return all available cell commands after a user has made his/her move
	/// This routine can be use for a simple random computer move or a more
	/// intelligent computer move
	/// </summary>
	/// <returns></returns>
	public List<CellData> GetAvailableCells() throws Exception {
		List<CellData> list = new ArrayList<CellData>();
		for (LineData line : listOfLines) {
			if (line.getCellDataList().size() != 3)
				throw new Exception("The number of cells per line must be three");
			//
			// get cell that has no value assigned to them yet
			//
			for (CellData cell : line.getCellDataList()) {
				if (cell.getUser() != null && cell.getUser().isEmpty() == false)
					continue;
				list.add(cell);
			}
		}
		return list;
	}

	/// <summary>
	/// This routine returns the cell position that is about to win.
	/// use to win or block the opponent
	/// </summary>
	/// <param name="user"></param>
	/// <returns></returns>
	public CellData GetWinningCell(String user) throws Exception {
		for (LineData line : listOfLines) {
			if (line.isIsShared())
				continue;
			if (line.getUserId().equals(user) == false)
				continue;
			if (line.getCellDataList().size() != 3)
				throw new Exception("The number of cells per line must be three");
			//
			// the pattern is simple
			// if the first and second cell is populated return the third cell in the
			// corresponding line
			// if the first and third cell is populated return the second cell in the
			// corresponding line
			// if the second and third cell is populated return the first cell in the
			// corresponding line
			//
			CellData firstCell = line.getCellDataList().get(0);
			CellData secondCell = line.getCellDataList().get(1);
			CellData thirdCell = line.getCellDataList().get(2);
			//
			// return the third cell
			int tempValue = 0;
			tempValue = (int) firstCell.getValue().getValue() | (int) secondCell.getValue().getValue();
			if (line.getValue() == tempValue)
				return thirdCell;
			//
			// return the second cell
			tempValue = (int) firstCell.getValue().getValue() | (int) thirdCell.getValue().getValue();
			if (line.getValue() == tempValue)
				return secondCell;
			//
			// return the first cell
			tempValue = (int) secondCell.getValue().getValue() | (int) thirdCell.getValue().getValue();
			if (line.getValue() == tempValue)
				return firstCell;
		}
		return null;
	}

	/// <summary>
	/// This routine return the cell position that is about to win
	/// use to win or block the opponent
	/// </summary>
	/// <param name="user"></param>
	/// <returns></returns>
	public CellPosition GetWinningCellPosition(String user) throws Exception {
		CellData cell = this.GetWinningCell(user);
		if (cell != null)
			return cell.getPosition();
		else
			return CellPosition.None;
	}

	/// <summary>
	///
	/// </summary>
	/// <param name="pos"></param>
	/// <param name="userId"></param>
	/// <returns></returns>
	public WinningStatus GetWinningStatus(String userId) {
		//
		// get the winning status
		//
		LineData winningLine = listOfLines.stream().filter(
				temp -> temp.getValue() == WINNER && userId.equals(temp.getUserId()) && temp.isIsShared() == false)
				.findFirst().orElse(null);
		if (winningLine != null)
			return WinningStatus.WINNER;
		//
		// check and see if that was the last request
		//
		if (this.requestCount >= MAX_REQUEST) {
			this.gameOver = true;
			return WinningStatus.GAMEOVER;
		}

		return WinningStatus.ONGOING;
	}

	public LineData GetWinnerLine(String userId) {
		LineData winningLine = listOfLines.stream().filter(
				temp -> temp.getValue() == WINNER && userId.equals(temp.getUserId()) && temp.isIsShared() == false)
				.findFirst().orElse(null);
		return winningLine;
	}
	// TAG-END: public methods

}
