package com.example.root.androidsampleapplicationpart2.model;

/**
 * Created by root on 8/16/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME ="myDatabase.db";
    private static final int DB_VERSION = 1;


    public static final String DEPARTMENT_TABLE = "tbl_departments";
    public static final String DEPARTMENT_NAME = "name";
    public static final String DEPARTMENT_ID = "department_id";

    public static final String USER_TABLE = "tbl_users";
    public static final String USER_ID = "user_id";
    public static final String USER_USERNAME = "username";
    public static final String USER_NAME = "name";
    public static final String USER_PASS = "pass";
    public static final String USER_PHONE = "phone";
    public static final String USER_EMAIL = "email";

    public static final String EMPLOYEE_TABLE = "tbl_employees";
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String EMPLOYEE_PIC = "picture";

    public static final String SUPERVISOR_TABLE = "tbl_supervisors";
    public static final String SUPERVISOR_ID = "supervisor_id";
    public static final String SUPERVISOR_PIC = "picture";

    public static final String CHAT_TABLE = "tbl_chat";
    public static final String CHAT_ID = "chat_id";
    public static final String CHAT_FROM_USER = "from_user";
    public static final String CHAT_MSG = "chat_msg";

    public static final String CHAT_RECEIPT_TABLE = "tbl_chat_receipt";
    public static final String CHAT_RECEIPT_ID = "chat_receipt_id";
    public static final String CHAT_RECEIPT_TO_USER = "chat_receipt_to_user";

    private static final String sqlCreateTableDepartment = "CREATE TABLE IF NOT EXISTS "
            + DEPARTMENT_TABLE + "("
            + DEPARTMENT_ID + " integer primary key autoincrement, "
            + DEPARTMENT_NAME + " text not null);";

    private static final String sqlCreateTableUsers = "CREATE TABLE IF NOT EXISTS "
            + USER_TABLE + "("
            + USER_ID + " integer primary key autoincrement, "
            + USER_USERNAME + " text not null, "
            + USER_NAME + " text not null, "
            + USER_PASS + " text not null, "
            + USER_PHONE + " text not null, "
            + USER_EMAIL + " text not null);";

    private static final String sqlCreateTableEmployees = "CREATE TABLE IF NOT EXISTS "
            + EMPLOYEE_TABLE + "("
            + EMPLOYEE_ID + " integer primary key autoincrement, "
            + SUPERVISOR_ID + " integer, "
            + USER_ID + " integer, "
            + DEPARTMENT_ID + " integer, "
            + EMPLOYEE_PIC + " blob not null, "
            + " FOREIGN KEY (" + SUPERVISOR_ID + ") REFERENCES "
            + SUPERVISOR_TABLE + "(" + SUPERVISOR_ID + ") "
            + " FOREIGN KEY (" + USER_ID + ") REFERENCES "
            + USER_TABLE + "(" + USER_ID + ") "
            + " FOREIGN KEY (" + DEPARTMENT_ID + ") REFERENCES "
            + DEPARTMENT_TABLE + "(" + DEPARTMENT_ID + "));";

    private static final String sqlCreateTableSupervisors = "CREATE TABLE IF NOT EXISTS "
            + SUPERVISOR_TABLE + "("
            + SUPERVISOR_ID + " integer primary key autoincrement, "
            + DEPARTMENT_ID + " integer, "
            + USER_ID + " integer, "
            + SUPERVISOR_PIC + " blob not null, "
            + " FOREIGN KEY (" + DEPARTMENT_ID + ") REFERENCES "
            + DEPARTMENT_TABLE + "(" + DEPARTMENT_ID + ") "
            + " FOREIGN KEY (" + USER_ID + ") REFERENCES "
            + USER_TABLE + "(" + USER_ID + "));";

    private static final String sqlCreateTableChat = "CREATE TABLE IF NOT EXISTS "
            + CHAT_TABLE + "("
            + CHAT_ID + " integer primary key autoincrement, "
            + CHAT_FROM_USER + " text not null, "
            + CHAT_MSG + " text not null);";

    private static final String sqlCreateTableChatReceipt = "CREATE TABLE IF NOT EXISTS "
            + CHAT_RECEIPT_TABLE + "("
            + CHAT_RECEIPT_ID + " integer primary key autoincrement, "
            + CHAT_ID + " integer, "
            + CHAT_RECEIPT_TO_USER + " text not null, "
            + " FOREIGN KEY (" + CHAT_ID + ") REFERENCES "
            + CHAT_TABLE + "(" + CHAT_ID + "));";

    public DBHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTableDepartment);
        db.execSQL(sqlCreateTableUsers);
        db.execSQL(sqlCreateTableSupervisors);
        db.execSQL(sqlCreateTableEmployees);
        db.execSQL(sqlCreateTableChat);
        db.execSQL(sqlCreateTableChatReceipt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}