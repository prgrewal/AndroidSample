package com.example.root.androidsampleapplicationpart2;

/**
 * Created by root on 8/9/16.
 */

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.root.androidsampleapplicationpart2.model.DBHelper;

public class CompanyRegister extends AppCompatActivity {

    public static final int EXTRA_REQUEST = 0;


    private String username, name, pass, phone, email;

    EditText eUsername, eName, ePass, ePhone, eEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companyregister_layout);

    }

    private void setUsername() {
        eUsername = (EditText)findViewById(R.id.rusername);

        username = eUsername.getText().toString();
    }
    private void setName() {
        eName = (EditText)findViewById(R.id.rname);

        name = eName.getText().toString();
    }
    private void setPass() {
        ePass = (EditText)findViewById(R.id.rpassword);

        pass = ePass.getText().toString();
    }
    private void setPhone() {
        ePhone = (EditText)findViewById(R.id.rphone);

        phone = ePhone.getText().toString();
    }
    private void setEmail() {
        eEmail = (EditText)findViewById(R.id.remail);

        email = eEmail.getText().toString();
    }

    public void handleRegisterClick(View view) { startMenu();}

    private void startMenu () {
        Intent intent = new Intent(CompanyRegister.this, MenuActivity.class);
        setUsername();
        setName();
        setPass();
        setPhone();
        setEmail();

        addUser(username,name,pass,phone,email);
        startActivityForResult(intent, MenuActivity.EXTRA_REQUEST);
    }

    private void addUser (String username, String name, String pass, String phone, String email) {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "INSERT INTO tbl_users (username, name, pass, phone, email) VALUES ("
                + "\"" + username + "\"" +", "
                + "\"" + name + "\"" + ", "
                + "\"" + pass + "\"" + ", "
                + "\"" + phone + "\"" + ", "
                + "\"" + email + "\"" +")";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.executeInsert();

    }
}