package com.example.shin.action_game;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Database extends AppCompatActivity {
    DataBaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        myDb = new DataBaseHelper(this);

        addData("0");
        viewAll();
        updateData("0");
        deleteData();    }

    public void addData(String score) {
        boolean isInserted = myDb.insertData(score);
        if (!isInserted) Toast.makeText(Database.this, "Data not inserted", Toast.LENGTH_LONG).show();
    }

    public int viewAll() {
        int sc = 0;
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
            return sc;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            String s = res.getString(1);
            buffer.append("score:" + s + "\n\n");
            sc = Integer.parseInt(s);
        }

        // show all data
        showMessage("Data", buffer.toString());
        return sc;
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void updateData(String score) {
        boolean isupdated = myDb.updateData("0", score);
        Toast.makeText(Database.this,  "Your High score is" + score, Toast.LENGTH_LONG).show();
    }

    public void deleteData() {
    }

}

