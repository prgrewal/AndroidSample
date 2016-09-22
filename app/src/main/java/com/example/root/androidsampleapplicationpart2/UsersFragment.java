package com.example.root.androidsampleapplicationpart2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.example.root.androidsampleapplicationpart2.adapter.UserDialog;
import com.example.root.androidsampleapplicationpart2.model.DBHelper;

import java.util.ArrayList;

/**
 * Created by root on 8/14/16.
 */
import com.example.root.androidsampleapplicationpart2.adapter.DbItemListAdapter;

public class UsersFragment extends Fragment {

    private DbItemListAdapter adapterUser;
    private DbItemListAdapter adapterPhone;

    ArrayList<String> usernameResults = new ArrayList<String>();
    ArrayList<String> phoneResults = new ArrayList<String>();

    public UsersFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_users,container, false);
        ListView list = (ListView) rootView.findViewById(R.id.list_username);
        ListView list2 = (ListView) rootView.findViewById(R.id.list_phone);


        openAndQueryDatabase();
        adapterUser = new DbItemListAdapter(getActivity(),
                usernameResults);
        list.setAdapter(adapterUser);
        adapterPhone = new DbItemListAdapter(getActivity(),
                phoneResults);
        list2.setAdapter(adapterPhone);

        Button dialogBtn = (Button) rootView.findViewById(R.id.useradd_btn);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                FragmentManager fm = getFragmentManager();
                UserDialog userDialog = new UserDialog();
                userDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        DbItemListAdapter adapterUser;
                        DbItemListAdapter adapterPhone;

                        ArrayList<String> usernameResults = new ArrayList<String>();
                        ArrayList<String> phoneResults = new ArrayList<String>();

                        ListView list = (ListView) getActivity().findViewById(R.id.list_username);
                        ListView list2 = (ListView) getActivity().findViewById(R.id.list_phone);

                        DBHelper helper = new DBHelper(getActivity());
                        SQLiteDatabase db = helper.getWritableDatabase();
                        Cursor cUser = db.rawQuery("SELECT username FROM tbl_users;", null);
                        Cursor cPhone = db.rawQuery("SELECT phone FROM tbl_users;", null);
                        if (cUser != null) {
                            if (cUser.moveToFirst()) {
                                do {
                                    String username = cUser.getString(cUser.getColumnIndex("username"));
                                    usernameResults.add(username);
                                } while (cUser.moveToNext());
                            }
                        }
                        if (cPhone != null) {
                            if (cPhone.moveToFirst()) {
                                do {
                                    String phone = cPhone.getString(cPhone.getColumnIndex("phone"));
                                    phoneResults.add(phone);
                                } while (cPhone.moveToNext());
                            }
                        }

                        adapterUser = new DbItemListAdapter(getActivity(),
                                usernameResults);
                        adapterUser.notifyDataSetChanged();
                        list.setAdapter(adapterUser);

                        adapterPhone = new DbItemListAdapter(getActivity(),
                                phoneResults);
                        adapterPhone.notifyDataSetChanged();
                        list2.setAdapter(adapterPhone);
                    }
                });
                userDialog.show(fm, "Add User");
            }
        });

        return rootView;
    }

    private void openAndQueryDatabase() {
        DBHelper helper = new DBHelper(getActivity());
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cUser = db.rawQuery("SELECT username FROM tbl_users;", null);
        Cursor cPhone = db.rawQuery("SELECT phone FROM tbl_users;", null);
        if (cUser != null) {
            if (cUser.moveToFirst()) {
                do {
                    String username = cUser.getString(cUser.getColumnIndex("username"));
                    usernameResults.add(username);
                } while (cUser.moveToNext());
            }
        }
        if (cPhone != null) {
            if (cPhone.moveToFirst()) {
                do {
                    String phone = cPhone.getString(cPhone.getColumnIndex("phone"));
                    phoneResults.add(phone);
                } while (cPhone.moveToNext());
            }
        }
    }
}
