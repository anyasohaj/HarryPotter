package com.bagirapp.harrypotterquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class QuestionDatabase extends SQLiteAssetHelper {

    private static final String TAG = "QuestionDatabase";
    private static final String DATABASE_NAME = "harry_potter.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "harry_potter";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_QUESTION = "QUESTION";
    public static final String COLUMN_RIGHT = "RIGHT_ANSWER";
    public static final String COLUMN_WRONG1 = "WRONG1";
    public static final String COLUMN_WRONG2 = "WRONG2";
    public static final String COLUMN_WRONG3 = "WRONG3";
    public static final String COLUMN_LEVEL = "LEVEL";
    public static final int BEGINNER_LEVEL = 1;
    public static final int ADVANCED_LEVEL = 2;
    public static final int CRAZY_LEVEL = 3;
    public static final int AKASZTOFA = 4;

    public QuestionDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Question getQuestionById(int id) {
        Question question = new Question();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                QuestionDatabase.TABLE, null, QuestionDatabase.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String ques = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION));
            String rightA = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RIGHT));
            String wrong1 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WRONG1));
            String wrong2 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WRONG2));
            String wrong3 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WRONG3));

            question = new Question(ques, rightA, wrong1, wrong2, wrong3);
            Log.v(TAG, question.toString());
            cursor.close();
        }

        return question;
    }

    public ArrayList<Integer> getIndecies(int category){
        ArrayList<Integer> IDs = new ArrayList<>();
        String[] args = new String[1];
        switch (category){
            case BEGINNER_LEVEL: args[0] = "1";
                break;
            case ADVANCED_LEVEL: args[0] = "2";
                break;
            case CRAZY_LEVEL:   args[0] = "3";
                break;
            case AKASZTOFA:     args[0] = "4";
                break;
            default: args[0]= "1";
        }

        SQLiteDatabase database = getReadableDatabase();
        String[] columns = {COLUMN_ID};
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COLUMN_LEVEL + " =?", args);
                //query(QuestionDatabase.TABLE, columns, QuestionDatabase.COLUMN_LEVEL + "=?", args, null, null, null);
            cursor.moveToFirst();
            while (cursor.moveToNext()){
            int ID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            IDs.add(ID);
            Log.v(TAG, "Hozzáadott egy ID-t, mégpedig: " + ID);
             }
            cursor.close();
             return IDs;
    }
}
