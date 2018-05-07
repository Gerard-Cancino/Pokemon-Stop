package com.example.fderenzi.pokemonstop;



public class Result {
    private String opponentName;
    private String result;
    private String winningMove;

    public Result(String newOpponent, String newResult, String newWinningMove) {
        opponentName = newOpponent;
        result = newResult;
        winningMove = newWinningMove;
    }

    public String getOpponentName(){
        return opponentName;
    }

    public String getResult(){
        return result;
    }

    public String getWinningMove(){
        return winningMove;
    }
}

