package com.rs.metictactoe.module.engine.viewmodel;

import java.util.List;
import java.util.Random;

import com.rs.metictactoe.module.engine.component.GameEngine;
import com.rs.metictactoe.module.engine.enums.CellPosition;
import com.rs.metictactoe.module.engine.enums.WinningStatus;
import com.rs.metictactoe.module.engine.model.CellData;
import com.rs.metictactoe.module.engine.model.LineData;

public class GameViewModel {

	private GameEngine _engine = null;

	private String _player1 = "X";
	private String _player2 = "O";
	private TurnStates _currentState;
	private LevelStates _currentLevel;

	private boolean _playComputer;
	//
	// property values to display in the cells
	//
	private String leftTop;
	private String leftMiddle;
	private String leftBottom;
	private String rightTop;
	private String rightMiddle;
	private String rightBottom;
	private String middleTop;
	private String middleCenter;
	private String middleBottom;

	public GameEngine get_engine() {
		return _engine;
	}

	public void set_engine(GameEngine _engine) {
		this._engine = _engine;
	}

	public String get_player1() {
		return _player1;
	}

	public void set_player1(String _player1) {
		this._player1 = _player1;
	}

	public String get_player2() {
		return _player2;
	}

	public void set_player2(String _player2) {
		this._player2 = _player2;
	}

	public TurnStates get_currentState() {
		return _currentState;
	}

	public void set_currentState(TurnStates _currentState) {
		this._currentState = _currentState;
	}

	public LevelStates get_currentLevel() {
		return _currentLevel;
	}

	public void set_currentLevel(LevelStates _currentLevel) {
		this._currentLevel = _currentLevel;
	}

	public boolean is_playComputer() {
		return _playComputer;
	}

	public void set_playComputer(boolean _playComputer) {
		this._playComputer = _playComputer;
	}

	public String getLeftTop() {
		return leftTop;
	}

	public void setLeftTop(String leftTop) {
		this.leftTop = leftTop;
	}

	public String getLeftMiddle() {
		return leftMiddle;
	}

	public void setLeftMiddle(String leftMiddle) {
		this.leftMiddle = leftMiddle;
	}

	public String getLeftBottom() {
		return leftBottom;
	}

	public void setLeftBottom(String leftBottom) {
		this.leftBottom = leftBottom;
	}

	public String getRightTop() {
		return rightTop;
	}

	public void setRightTop(String rightTop) {
		this.rightTop = rightTop;
	}

	public String getRightMiddle() {
		return rightMiddle;
	}

	public void setRightMiddle(String rightMiddle) {
		this.rightMiddle = rightMiddle;
	}

	public String getRightBottom() {
		return rightBottom;
	}

	public void setRightBottom(String rightBottom) {
		this.rightBottom = rightBottom;
	}

	public String getMiddleTop() {
		return middleTop;
	}

	public void setMiddleTop(String middleTop) {
		this.middleTop = middleTop;
	}

	public String getMiddleCenter() {
		return middleCenter;
	}

	public void setMiddleCenter(String middleCenter) {
		this.middleCenter = middleCenter;
	}

	public String getMiddleBottom() {
		return middleBottom;
	}

	public void setMiddleBottom(String middleBottom) {
		this.middleBottom = middleBottom;
	}

	public void playLeffTop() throws Exception {
		//
		// player 1 just played, now it's player2's turn
		//
		String temp = this.updateCellValue();
		this.leftTop = temp;
		this.CheckWinningStatus(CellPosition.A1, temp);
	}

	public void playLeftMiddle() throws Exception {
		String temp = this.updateCellValue();

		this.leftMiddle = temp;
		this.CheckWinningStatus(CellPosition.A2, temp);
	}

	public void playLeftBottom() throws Exception {
		String temp = this.updateCellValue();

		this.leftBottom = temp;
		this.CheckWinningStatus(CellPosition.A3, temp);
	}

	public void playRightTop() throws Exception {
		String temp = this.updateCellValue();

		this.rightTop = temp;
		this.CheckWinningStatus(CellPosition.C1, temp);
	}

	public void playRightMiddle() throws Exception {
		String temp = this.updateCellValue();

		this.rightMiddle = temp;
		this.CheckWinningStatus(CellPosition.C2, temp);
	}

	public void playRightBottom() throws Exception {
		String temp = this.updateCellValue();

		this.rightBottom = temp;
		this.CheckWinningStatus(CellPosition.C3, temp);
	}

	public void playMiddleTop() throws Exception {
		String temp = this.updateCellValue();
		this.middleTop = temp;
		this.CheckWinningStatus(CellPosition.B1, temp);
	}

	public void playMiddleCenter() throws Exception {
		String temp = this.updateCellValue();

		this.middleCenter = temp;
		this.CheckWinningStatus(CellPosition.B2, temp);
	}

	public void playMiddleBottom() throws Exception {
		String temp = this.updateCellValue();
		this.middleBottom = temp;
		this.CheckWinningStatus(CellPosition.B3, temp);
	}

/// <summary>
///
/// </summary>
/// <param name="cellPosition"></param>
/// <param name="player"></param>
	public String CheckWinningStatus(CellPosition cellPosition, String player) throws Exception {
		if (_currentState == TurnStates.Initial)
			return null;
		//
		// format the name to notify with
		//
		String playerName = "Player 1";
		if (player == _player2)
			playerName = "Player 2";

		//
		// check the winner status
		//
		_engine.UpdateCell(cellPosition, player); // update the value for the user
		WinningStatus status = _engine.GetWinningStatus(player);// check if the user is a winner, once the value is
																// update
		if (status == WinningStatus.WINNER) {
			LineData winnerLine = _engine.GetWinnerLine(player);

			if (winnerLine != null) {
				_currentState = TurnStates.Initial;
				return playerName + " won!!!";
			}

		}
		//
		// check for draw
		//
		else if (status == WinningStatus.GAMEOVER) {
			_currentState = TurnStates.Initial;
			return "It's draw!!!";
		}
		//
		// playing against the computer
		//
		else if (this._playComputer && _currentState == TurnStates.Player_2) {
			MakeAnAutomatedMove();
		}
		return null;
	}

/// <summary>
/// Play dumb
/// This is a simple computer engine
/// It makes a random move after the user has made a move
/// Pretty dumb computer, it can only win by luck
/// Even when the play is not very good
/// </summary>
	private CellData EasyComputerMakeYourMove() throws Exception {
		//
		// Return if all the commands have already been executed
		//
		List<CellData> availableCells = _engine.GetAvailableCells();
		if (availableCells.size() == 0)
			return null;
		//
		// Get a random index between on an the number of commands left
		// find that command in the dictionary and execute it
		//
		Random ran = new Random();
		int ranIndex = ran.nextInt(availableCells.size());
		return availableCells.get(ranIndex);
	}

	/// <summary>
	/// play with standard strategy
	/// </summary>
	private CellData NormalComputerMakeYourMove() throws Exception { //
		// Return if all the commands have already been executed
		//
		List<CellData> availableCells = _engine.GetAvailableCells();
		if (availableCells.size() == 0)
			return null;
		//
		// if the computer has the winning possibility use the slot
		// if the player has the winning possibilty block the slot
		// if none of the above, do a random move.
		//
		CellPosition position = _engine.GetWinningCellPosition(_player2);
		if (position == CellPosition.None)
			position = _engine.GetWinningCellPosition(_player1);

		if (position == CellPosition.None) {
			//
			// check whether we should win or block
			//
			Random ran = new Random();
			int ranIndex = ran.nextInt(availableCells.size());
			return availableCells.get(ranIndex);
		} else {
			//
			// find the win
			//
			CellPosition winningCellPosition = position;
			CellData winningCell = availableCells.stream().filter(temp -> temp.getPosition() == winningCellPosition)
					.findFirst().orElse(null);
			return winningCell;
		}
	}

	/// <summary>
	/// Play with maximum strategy
	/// </summary>
	private CellData HardComputerMakeYourMove() throws Exception {
		//
		// Return if all the commands have already been executed
		//
		List<CellData> availableCells = _engine.GetAvailableCells();
		if (availableCells.size() == 0)
			return null;
		//
		// if the computer has the winning possibility use the slot
		// if the player has the winning possibilty block the slot
		// if none of the above, do a random move.
		//
		CellPosition position = _engine.GetWinningCellPosition(_player2);
		if (position == CellPosition.None)
			position = _engine.GetWinningCellPosition(_player1);
		///
		// no threat
		CellData targetCell = null;
		if (position != CellPosition.None) {
			//
			// win or play defense
			//
			CellPosition winningCellPosition = position;
			targetCell = availableCells.stream().filter(temp -> temp.getPosition() == winningCellPosition).findFirst()
					.orElse(null);
			return targetCell;
		}
		//
		// CellPosition.B2
		//
		// use strategic cell if available
		// use the middle cell if available
		targetCell = availableCells.stream().filter(temp -> temp.getPosition() == CellPosition.B2).findFirst()
				.orElse(null);
		if (targetCell != null)
			return targetCell;
		//
		// use the middle top cell if available
		// CellPosition.A1
		targetCell = availableCells.stream().filter(temp -> temp.getPosition() == CellPosition.A1).findFirst()
				.orElse(null);
		if (targetCell != null)
			return targetCell;
		//
		// CellPosition.C1
		targetCell = availableCells.stream().filter(temp -> temp.getPosition() == CellPosition.C1).findFirst()
				.orElse(null);
		if (targetCell != null)
			return targetCell;

		//
		// Get dumb and use a random cell
		// random
		Random ran = new Random();
		int ranIndex = ran.nextInt(availableCells.size());
		return availableCells.get(ranIndex);

	}

	/// <summary>
	///
	/// </summary>
	private CellData HardestComputerMakeYourMove() throws Exception {
		//
		// Return if all the commands have already been executed
		//
		List<CellData> availableCells = _engine.GetAvailableCells();
		if (availableCells.size() == 0)
			return null;
		//
		// if the computer has the winning possibility use the slot
		// if the player has the winning possibilty block the slot
		// if none of the above, do a random move.
		//
		CellPosition position = _engine.GetWinningCellPosition(_player2);
		if (position == CellPosition.None)
			position = _engine.GetWinningCellPosition(_player1);
		///
		// no threat
		CellData targetCell = null;
		if (position != CellPosition.None) {
			//
			// win or play defense
			//
			CellPosition winningCellPosition = position;
			targetCell = availableCells.stream().filter(temp -> temp.getPosition() == winningCellPosition).findFirst()
					.orElse(null);
			return targetCell;
		}
		//
		// CellPosition.B2
		//
		// use strategic cell if available
		// use the middle cell if available
		targetCell = availableCells.stream().filter(temp -> temp.getPosition() == CellPosition.B2).findFirst()
				.orElse(null);
		if (targetCell != null) {
			return targetCell;
		}
		//
		// defend the exterior double header move
		//
		else {

			CellData leftBottomCell = _engine.getCellData(CellPosition.A3);
			CellData rightTopCell = _engine.getCellData(CellPosition.C1);
			CellData leftTopCell = _engine.getCellData(CellPosition.A1);
			CellData rightBottomCell = _engine.getCellData(CellPosition.C3);

			// guard against lyon's move
			if (leftBottomCell.getUser().equals(_player1) && rightTopCell.getUser().equals(_player1)) {
				//
				// use the middle top cell if available
				targetCell = availableCells.stream().filter(temp -> temp.getPosition() == CellPosition.C2).findFirst()
						.orElse(null);
				if (targetCell != null)
					return targetCell;
			}
			//
			// check the other favorite move
			//
			if (leftTopCell.getUser().equals(_player1) && rightBottomCell.getUser().equals(_player1)) {
				//
				// use the middle top cell if available
				targetCell = availableCells.stream().filter(temp -> temp.getPosition() == CellPosition.C2).findFirst()
						.orElse(null);
				if (targetCell != null)
					return targetCell;
			}

		}

		//
		// use the middle top cell if available
		// CellPosition.A1
		targetCell = availableCells.stream().filter(temp -> temp.getPosition() == CellPosition.A1).findFirst()
				.orElse(null);
		if (targetCell != null)
			return targetCell;
		//
		// CellPosition.C1
		targetCell = availableCells.stream().filter(temp -> temp.getPosition() == CellPosition.C1).findFirst()
				.orElse(null);
		if (targetCell != null)
			return targetCell;

		//
		// CellPosition.A3
		targetCell = availableCells.stream().filter(temp -> temp.getPosition() == CellPosition.A3).findFirst()
				.orElse(null);
		if (targetCell != null)
			return targetCell;

		//
		// CellPosition.C3
		targetCell = availableCells.stream().filter(temp -> temp.getPosition() == CellPosition.C3).findFirst()
				.orElse(null);
		if (targetCell != null)
			return targetCell;

		//
		// Get dumb and use a random cell
		// random
		Random ran = new Random();
		int ranIndex = ran.nextInt(availableCells.size());
		return availableCells.get(ranIndex);
	}

	/// <summary>
	/// Make an automated move using one of the computers
	/// </summary>
	private CellData MakeAnAutomatedMove() throws Exception {

		//
		// make a move based on the level of difficulty
		//
		switch (_currentLevel) {
		case Easy:
			return this.EasyComputerMakeYourMove();

		case Normal:
			return this.NormalComputerMakeYourMove();
		case Hard:
			return this.HardComputerMakeYourMove();
		case Hardest:
			return this.HardestComputerMakeYourMove();
		}

		return null;

	}

	/// <summary>
	///
	/// </summary>
	public void ClearData() {
		this.leftTop = null;
		this.leftMiddle = null;
		this.leftBottom = null;
		this.rightTop = null;
		this.rightMiddle = null;
		this.rightBottom = null;
		this.middleTop = null;
		this.middleCenter = null;
		this.middleBottom = null;
		this._engine = null;

		this._currentState = TurnStates.Initial;
	}

	/// <summary>
	/// Start a new game
	/// </summary>
	/// <param name="state"></param>
	public void StartGame(TurnStates state) throws Exception {
		this.ClearData();
		//
		// create a new processor
		//
		this._engine = new GameEngine();

		if (state == TurnStates.Player_2) {
			_currentState = TurnStates.Player_2;
			MakeAnAutomatedMove();
		} else
			_currentState = TurnStates.Player_1;

	}

	private String updateCellValue() {
		if (_currentState == TurnStates.Player_1) {
			//
			// update the value for player one and set player two to active

			_currentState = TurnStates.Player_2;
			return _player1;

		} else if (_currentState == TurnStates.Player_2) {
			//
			// update the value for player two and set player one to active
			//
			_currentState = TurnStates.Player_1;
			return _player2;
		} else {
			_currentState = TurnStates.Initial;
			return "";
		}

	}

}
