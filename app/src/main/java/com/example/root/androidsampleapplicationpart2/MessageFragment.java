package com.example.root.androidsampleapplicationpart2;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.root.androidsampleapplicationpart2.adapter.NothingSelectedSpinnerAdapter;
import com.example.root.androidsampleapplicationpart2.model.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 8/14/16.
 */

public class MessageFragment extends Fragment {
    public MessageFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_message,container, false);

        final Spinner sfUser = (Spinner)rootView.findViewById(R.id.spinner_Fchat_username);
        final Spinner stUser = (Spinner)rootView.findViewById(R.id.spinner_Tchat_username);

        Button sendBtn = (Button)rootView.findViewById(R.id.chatsend_btn);


        List<String> userLabels = getUsernames();

        ArrayAdapter<String> userDataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, userLabels);

        userDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sfUser.setAdapter( new NothingSelectedSpinnerAdapter(
                userDataAdapter,
                R.layout.username_spinner_row_nothing_selected,
                getActivity()));
        stUser.setAdapter( new NothingSelectedSpinnerAdapter(
                userDataAdapter,
                R.layout.username_spinner_row_nothing_selected,
                getActivity()));
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText eMsg = (EditText) rootView.findViewById(R.id.chat_msg);

                String msg = eMsg.getText().toString();

                String fUser = sfUser.getSelectedItem().toString();
                String tUser = stUser.getSelectedItem().toString();

                String insert_tbl_chat = "INSERT INTO tbl_chat (from_user, chat_msg) VALUES ("
                        + "\"" + fUser + "\"" + ", "
                        + "\"" + msg + "\"" + ");";

                DBHelper helper = new DBHelper(getActivity());
                SQLiteDatabase db = helper.getWritableDatabase();
                SQLiteStatement statement = db.compileStatement(insert_tbl_chat);
                statement.executeInsert();

                String msgIDQuery = "SELECT chat_id FROM tbl_chat WHERE chat_msg = '" + msg + "';";
                String msgID = null;

                Cursor cMsg = db.rawQuery(msgIDQuery, null);
                if (cMsg != null) {
                    if (cMsg.moveToFirst()) {
                        do {
                            msgID = cMsg.getString(cMsg.getColumnIndex("chat_id"));
                        } while (cMsg.moveToNext());
                    }
                }

                ContentValues cv = new ContentValues();
                cv.put("chat_id", msgID);
                cv.put("chat_receipt_to_user", tUser);
                db.insert( "tbl_chat_receipt", null, cv );
                eMsg.setText("");

            }
        });
        return rootView;
    }

    private List<String> getUsernames() {
        DBHelper dbhelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        List<String> userLabels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT username FROM tbl_users;";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String userName = cursor.getString(cursor.getColumnIndex("username"));
                userLabels.add(userName);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return userLabels;
    }
}
