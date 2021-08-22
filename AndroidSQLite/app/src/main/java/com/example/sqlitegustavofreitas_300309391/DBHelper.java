package com.example.sqlitegustavofreitas_300309391;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlitegustavofreitas_300309391.model.UserContact;

import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context,"Userdata.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase dataBase) {
        dataBase.execSQL("CREATE TABLE Userdetails (name TEXT primary key, contact TEXT, dob TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase dataBase, int oldVersion, int newVersion) {
        dataBase.execSQL("DROP TABLE IF EXISTS Userdetails");
    }

    public Integer getTotalRecords(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Userdetails",null);

        return cursor.getCount();
    }

    public void addSampleData(List<UserContact> sampleData){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(int position = 0; position < sampleData.size(); position++){

            contentValues.put("name",sampleData.get(position).getName());
            contentValues.put("contact",sampleData.get(position).getContact());
            contentValues.put("dob",sampleData.get(position).getDateOfBirth());

            db.insert("Userdetails",null,contentValues);
        }
    }



    public Boolean insertUser(UserContact newUser){
        //Variable to check if the data was inserted into database
        Boolean validInsert = false;

        //Instanciate the database object that will perform queries
        SQLiteDatabase db = this.getWritableDatabase();
        //Instanciate Content Values object that will wrap the UserContact Object attributes
        ContentValues contentValues = new ContentValues();

        // Wrap/attaches each attribute from newUser (UserContact Object)
        contentValues.put("name",newUser.getName());
        contentValues.put("contact",newUser.getContact());
        contentValues.put("dob",newUser.getDateOfBirth());

        // Calls insert data method from the database object.
        // Attaches contentValues variable as a parameter
        // The method returns a long value if the data was inserted
        long result = db.insert("Userdetails",null,contentValues);

        // Check if the data was inserted
        // If the data was inserted into the database, return true
        if(result < 0){
            validInsert = false;
        } else {
            validInsert = true;
        }
        return validInsert;
    }

    public Boolean updateUser(UserContact newUser){
        //Variable to check if the data was inserted into database
        Boolean validUpdate = false;

        //Instanciate the database object that will perform queries
        SQLiteDatabase db = this.getWritableDatabase();
        //Instanciate Content Values object that will wrap the UserContact Object attributes
        ContentValues contentValues = new ContentValues();

        // Wrap/attaches each attribute from newUser (UserContact Object)
        contentValues.put("name",newUser.getName());
        contentValues.put("contact",newUser.getContact());
        contentValues.put("dob",newUser.getDateOfBirth());

        //In the sql statement '?' means the bind function. In other words, the next parameter
        //means an array of strings, where name is the only attribute which is going to be bind
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Userdetails WHERE name=?",
                new String[]{
                        newUser.getName()
                });

        if(cursor.getCount() > 0){

            // Calls insert data method from the database object.
            // Attaches contentValues variable as a parameter
            // The method returns a long value if the data was inserted
            long result = db.update("Userdetails",contentValues,"name=?",
                    new String[]{newUser.getName()});

            // Check if the data was inserted
            // If the data was inserted into the database, return true
            if(result < 0){
                validUpdate = false;
            } else {
                validUpdate = true;
            }

        }
        return validUpdate;
    }

    public Boolean deleteUser(String name){
        Boolean validDelete = false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);

        Cursor cursor = db.rawQuery("SELECT * FROM Userdetails WHERE name=?",
                new String[]{name});

        if(cursor.getCount() > 0){
            long result = db.delete("Userdetails","name=?",new String[]{name});

            if(result < 0){
                validDelete = false;
            } else {
                validDelete = true;
            }
        }

        return validDelete;
    }

    public Cursor getUser(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);

        //Dumbcursor
        Cursor dumbCursor = null;

        Cursor cursor = db.rawQuery("SELECT * FROM Userdetails WHERE name LIKE \"%"+name+"%\"",new String[]{});

        if(cursor.getCount() > 0){
            dumbCursor = cursor;
        }

        return dumbCursor;
    }

    public Cursor getAllUsers(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Userdetails ORDER BY name",null);
        return cursor;
    }
}
