package com.example.myapplication2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {
EditText nameBox;
EditText dateBox;
EditText descriptionBox;

Button delButton;
Button saveButton;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        nameBox = (EditText) findViewById(R.id.name);
        dateBox = (EditText) findViewById(R.id.date);
        descriptionBox = (EditText) findViewById(R.id.description);
        delButton = (Button) findViewById(R.id.deleteButton);
        saveButton = (Button) findViewById(R.id.saveButton);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            userId=extras.getLong("id");
        }
        if (userId>0){
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            nameBox.setText(userCursor.getString(1));
            dateBox.setText(String.valueOf(userCursor.getInt(2)));
            descriptionBox.setText(userCursor.getString(3));
            userCursor.close();
        }
        else{
            delButton.setVisibility(View.GONE);
        }

    }
    public void save(View view){
        ContentValues cv=new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME,nameBox.getText().toString());
        cv.put(DatabaseHelper.COLUMN_DATE,Integer.parseInt(dateBox.getText().toString()));
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION,descriptionBox.getText().toString());

        if (userId>0){
            db.update(DatabaseHelper.TABLE,cv,
                    DatabaseHelper.COLUMN_ID+"="+String.valueOf(userId),null);
        }else{
            db.insert(DatabaseHelper.TABLE,null,cv);
        }
        goHome();
    }
    public void delete(View view){
        db.delete(DatabaseHelper.TABLE,"_id = ?",new String[]{String.valueOf(userId)});
        goHome();

    }
    public void goHome(){
        db.close();
        Intent intent=new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}