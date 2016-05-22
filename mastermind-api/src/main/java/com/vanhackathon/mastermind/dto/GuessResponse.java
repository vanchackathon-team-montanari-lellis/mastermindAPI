package com.vanhackathon.mastermind.dto;

public class GuessResponse {

	private boolean solved;

	private Result result;

	public Result getResult() {
		return result;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}
}
