package com.example.fderenzi.pokemonstop;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

// Database
// Keeps the history of all battles
public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "battleHistoryDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_HISTORY="History";
    private static final String OPPONENT = "Opponent_Name";
    private static final String RESULT = "Battle_Result";

    public DatabaseManager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String sqlCreateTable= "create table " + TABLE_HISTORY + "( " + OPPONENT + " text, " + RESULT + " text)";
        sqLiteDatabase.execSQL(sqlCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+ TABLE_HISTORY);
        onCreate(sqLiteDatabase);
    }

    public void insert( String opponentToInsert, String resultToInsert ) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_HISTORY + " values('" + opponentToInsert + "' ,'" + resultToInsert +"' )";

        db.execSQL(sqlInsert);
        db.close();
    }

    public ArrayList<Result> selectAll( ) {
        String sqlQuery = "select * from " + TABLE_HISTORY;

        SQLiteDatabase db = this.getWritableDatabase( );
        Cursor cursor = db.rawQuery( sqlQuery, null );

        ArrayList<Result> resultList = new ArrayList<Result>( );
        while( cursor.moveToNext( ) ) {
            Result currentResult
                    = new Result(cursor.getString(0),
                    cursor.getString( 1 ));
            resultList.add( currentResult );
        }
        db.close( );
        return resultList;
    }


}
