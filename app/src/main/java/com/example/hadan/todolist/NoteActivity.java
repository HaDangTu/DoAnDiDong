package com.example.hadan.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    EditText tileEdText;
    EditText contentEdText;
    Date dateCreate;
    SQLiteDatabase db;
    MyDatabaseAdapter dbHelper;

    String tmpTile;
    String tmpContent;

    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        dbHelper = new MyDatabaseAdapter(this);
        db = dbHelper.getWritableDatabase();

        tileEdText = findViewById(R.id.TileEdText);
        contentEdText = findViewById(R.id.ContentEdText);
        btnSave = findViewById(R.id.SaveBtn);

        onClickBtnSave();

        tmpTile = tileEdText.getText().toString();
        tmpContent = contentEdText.getText().toString();


    }

    @Override
    public void onBackPressed(){
        if (!tileEdText.getText().toString().equals(tmpTile) ||
                !contentEdText.getText().toString().equals(tmpContent)){
            AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(this);

            //set tile for dialog
            alerDialogBuilder.setTitle("Do you want to save changes ?");
            alerDialogBuilder.setCancelable(false);

            //set button yes
            alerDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dateCreate = new Date();
                    InsertDatabase(tileEdText.getText().toString(), contentEdText.getText().toString(),
                            dateCreate);

                }
            });

            //set button no
            alerDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    /*Intent intent  = new Intent(NoteActivity.this, MainActivity.class);
                    startActivity(intent);*/
                    finish();
                }
            });

            AlertDialog alertDialog = alerDialogBuilder.create();
            alertDialog.show();
        }
        else finish();

    }

    public void onClickBtnSave()
    {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmpTile = tileEdText.getText().toString();
                tmpContent = contentEdText.getText().toString();

                dateCreate = new Date();
                //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                InsertDatabase(tileEdText.getText().toString(), contentEdText.getText().toString(),
                        dateCreate);
            }
        });
    }

    public void InsertDatabase(String tile, String content, Date dateCreate)
    {
        ContentValues values=new ContentValues();
        values.put(dbHelper.Tile, tile);
        values.put(dbHelper.Content, content);
        values.put(dbHelper.Date, dateCreate.toString());
        String mgs="";
        if(db.insert(dbHelper.TABLE_NAME, null, values) == -1)
        {
            mgs = "FAIL";
        }
        else mgs = "SUCCESSFULL";
        Toast.makeText(this, mgs, Toast.LENGTH_LONG).show();
    }

}
