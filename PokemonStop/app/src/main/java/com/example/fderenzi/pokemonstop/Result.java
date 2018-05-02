package com.example.fderenzi.pokemonstop;



public class Result {
    private String opponentName;
    private String result;

    public Result(String newOpponent, String newResult) {
        opponentName = newOpponent;
        result = newResult;
    }

    public String getOpponentName(){
        return opponentName;
    }

    public String getResult(){
        return result;
    }

    public void setOpponentName(String namePassed){
        opponentName = namePassed;
    }

    public void setResult(String resultPassed){
        result = resultPassed;
    }
}

