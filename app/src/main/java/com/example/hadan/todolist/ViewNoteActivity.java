package com.example.hadan.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.EditText;
import java.util.Date;

public class ViewNoteActivity extends AppCompatActivity {

    EditText viewTileED;
    EditText viewContentED;

    String tempTile;
    String tempContent;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        viewTileED = findViewById(R.id.viewTile);
        viewContentED = findViewById(R.id.viewContent);

        Intent intent = getIntent();

        viewTileED.setText(intent.getStringExtra("Tile"));
        viewContentED.setText(intent.getStringExtra("Content"));
        id = intent.getIntExtra("ID", 0);

        tempTile = viewTileED.getText().toString();
        tempContent = viewContentED.getText().toString();

    }

    @Override
    public void onBackPressed(){
        if (!viewTileED.getText().toString().equals(tempTile)||
                !viewContentED.getText().toString().equals(tempContent)){
            AlertDialog.Builder saveDialogBuilder = new AlertDialog.Builder(this);

            saveDialogBuilder.setTitle("Do you want to save changes ?");
            saveDialogBuilder.setCancelable(false);

            saveDialogBuilder
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            MyDatabaseAdapter mDbAdapter;
                            mDbAdapter = new MyDatabaseAdapter(ViewNoteActivity.this);

                            if(mDbAdapter.Update(id,
                                    viewTileED.getText().toString(),
                                    viewContentED.getText().toString(),
                                    new Date()) > 0)

                            Toast.makeText(ViewNoteActivity.this, "Successfull",
                                    Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(ViewNoteActivity.this, "Fail",
                                        Toast.LENGTH_LONG).show();

                            tempTile = viewTileED.getText().toString();
                            tempContent = viewContentED.getText().toString();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            AlertDialog saveDialog = saveDialogBuilder.create();
            saveDialog.show();
        }
        else finish();
    }
}
