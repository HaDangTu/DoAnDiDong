package com.example.hadan.todolist;

import android.content.ContentValues;
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

    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        dbHelper = new MyDatabaseAdapter(this);
        db = dbHelper.getWritableDatabase();

        onClickBtnSave();
    }

    public void onClickBtnSave()
    {
        btnSave = findViewById(R.id.SaveBtn);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tileEdText = findViewById(R.id.TileEdText);
                contentEdText = findViewById(R.id.ContentEdText);

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
