package com.example.sqlitegustavofreitas_300309391;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlitegustavofreitas_300309391.model.UserContact;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textResult;
    Button btnAdd, btnUpdate, btnDelete, btnSearch, btnSeeAll;
    EditText editTextName, editTextContact, editTextDoB;
    DBHelper newDatabase;

    List<UserContact> userContactsSample = Arrays.asList(new UserContact[]{
            new UserContact("Arlie Cunradi", "624-675-9413", "09/11/1974"),
            new UserContact("Neville Keeton", "488-679-9773", "02/26/1989"),
            new UserContact("Mickie Minthorpe", "262-824-4288", "06/07/1982"),
            new UserContact("Janel Taffe", "669-233-0393", "06/09/1995"),
            new UserContact("Craggie Kerwick", "265-592-6573", "05/25/1981"),
            new UserContact("Rogers Hooks","921-204-1522","07/26/1997"),
            new UserContact("Norbie Newarte", "770-138-8779","05/17/1962"),
            new UserContact("Karlik Treherne","215-802-7791","10/30/1966"),
            new UserContact("Robb Boteman","542-878-5504","11/14/1953")
    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newDatabase = new DBHelper(this);

        if(newDatabase.getTotalRecords() < 1){
            newDatabase.addSampleData(userContactsSample);
        }

        //Text View Results
        textResult = findViewById(R.id.textViewResult);

        //EditTextForm
        editTextName = findViewById(R.id.editTextName);
        editTextContact = findViewById(R.id.editTextContact);
        editTextDoB = findViewById(R.id.editTextDoB);

        //Buttons
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnSearch = findViewById(R.id.btnView);
        btnSeeAll = findViewById(R.id.btnSeeAll);

        btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = newDatabase.getAllUsers();

                if(result == null){
                    Toast.makeText(MainActivity.this,"There are no contacts",Toast.LENGTH_LONG).show();
                } else {
                    displayContactList();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserContact newUser = new UserContact(
                        editTextName.getText().toString(),
                        editTextContact.getText().toString(),
                        editTextDoB.getText().toString()
                );

                if(newUser.getName().equals("") || newUser.getContact().equals("") || newUser.getDateOfBirth().equals("")){
                    Toast.makeText(MainActivity.this,
                            "User not Inserted\nData must be all filled",
                            Toast.LENGTH_LONG).show();
                } else {
                    Boolean insert = newDatabase.insertUser(newUser);
                    if(insert){
                        Toast.makeText(MainActivity.this,"New User Inserted",Toast.LENGTH_LONG).show();
                        displayContactList();
                    } else {
                        Toast.makeText(MainActivity.this,"New User Not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserContact newUser = new UserContact(
                        editTextName.getText().toString(),
                        editTextContact.getText().toString(),
                        editTextDoB.getText().toString()
                );

                Boolean insert = newDatabase.updateUser(newUser);

                if(insert){
                    Toast.makeText(MainActivity.this,"User Updated",Toast.LENGTH_LONG).show();
                    displayContactList();
                } else {
                    Toast.makeText(MainActivity.this,"User not found",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                Boolean insert = newDatabase.deleteUser(name);

                if(insert){
                    Toast.makeText(MainActivity.this,"User Deleted",Toast.LENGTH_LONG).show();
                    displayContactList();
                } else {
                    Toast.makeText(MainActivity.this,"User not found",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                Cursor result = newDatabase.getUser(name);

                if(result == null){
                    Toast.makeText(MainActivity.this,"User not found",Toast.LENGTH_LONG).show();
                } else {
                    StringBuffer buffer = new StringBuffer();

                    while(result.moveToNext()){
                        //The parameter inside of the result.getString(i) 'i'
                        //is the column index of the table created
                        // e.g. (name,contact,dateOfBirth) = (0,1,2)
                        buffer.append("Name: "+result.getString(0)+"\n");
                        buffer.append("Contact: "+result.getString(1)+"\n");
                        buffer.append("Date of Birth: "+result.getString(2));
                    }

                    /*
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("User Entities");
                    builder.setMessage(buffer.toString());
                    builder.show();
                    */

                    textResult.setText(buffer.toString());
                }
            }
        });
    }

    public void displayContactList(){
        StringBuffer buffer = new StringBuffer();
        Cursor result = newDatabase.getAllUsers();
        while (result.moveToNext()) {
            //The parameter inside of the result.getString(i) 'i'
            //is the column index of the table created
            // e.g. (name,contact,dateOfBirth) = (0,1,2)
            buffer.append("Name: " + result.getString(0) + "\n");
            buffer.append("Contact: " + result.getString(1) + "\n");
            buffer.append("Date of Birth: " + result.getString(2) + "\n" +
                    "-------------------------------\n");
        }
        textResult.setText(buffer.toString());
    }
}