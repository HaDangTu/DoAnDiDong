package com.example.hadan.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class MyDatabaseAdapter extends SQLiteOpenHelper {

    //Ten bang
    public static final String TABLE_NAME = "NOTE";
    //Ten database
    public static final String DB_NAME = "NOTEPAD.db";
    //Ten cot
    public static final String ID = "ID";
    public static final String Tile = "Tile";
    public static final String Content = "Content";
    public static final String Date = "DateCreate";

    static final int DB_VERSION = 1;

    //Lenh tao bang
    private static final String SQL = "CREATE TABLE " + TABLE_NAME
            + "("+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Tile + " TEXT, " + Content + " TEXT, " + Date + " DATE)";

    public MyDatabaseAdapter(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public List<Note> SelectAll(SQLiteDatabase database)
    {
        String[] projection = {ID, Tile, Content,
                Date};

        Cursor cursor = database.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<Note> listNote = new ArrayList<Note>();
        String tmpTile;
        String tmpContent;
        long tmpId;
        java.util.Date tmpdate;

        while(cursor.moveToNext())
        {
            tmpId = cursor.getLong(cursor.getColumnIndexOrThrow(ID));
            tmpTile = cursor.getString(cursor.getColumnIndexOrThrow(Tile));
            tmpContent = cursor.getString(cursor.getColumnIndexOrThrow(Content));

            /*SimpleDateFormat sdf = new SimpleDateFormat("dd//MM//yyyy hh:mm:ss");
            String dateInString = cursor.getString(cursor.getColumnIndexOrThrow(Date));
            try {
                tmpdate = sdf.parse(dateInString);
            }catch(ParseException e){
                e.printStackTrace();
                return null;
            }*/

            Note note = new Note((int) tmpId, tmpTile, tmpContent);
            listNote.add(note);
        }

        cursor.close();
        return listNote;
    }

    public int Update (int ID, String tile, String content, Date dateCreate){
        SQLiteDatabase database;
        database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Tile, tile);
        values.put(Content, content);

        String whereClause = this.ID + " = " + ID;

        return  database.update(
                TABLE_NAME,
                values,
                whereClause,
                null
        );
    }
}
