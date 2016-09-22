package com.example.root.androidsampleapplicationpart2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.root.androidsampleapplicationpart2.adapter.DbImageListAdapter;
import com.example.root.androidsampleapplicationpart2.adapter.DbItemListAdapter;
import com.example.root.androidsampleapplicationpart2.adapter.EmployeeDialog;
import com.example.root.androidsampleapplicationpart2.model.DBHelper;

import java.util.ArrayList;

/**
 * Created by root on 8/14/16.
 */
public class AllEmployeesFragment extends Fragment {
    private DbItemListAdapter adapterMUser;
    private DbItemListAdapter adapterMPhone;
    private DbImageListAdapter adapterMImage;

    private DbItemListAdapter adapterEUser;
    private DbItemListAdapter adapterEPhone;
    private DbImageListAdapter adapterEImage;

    ArrayList<String> usernameMResults = new ArrayList<String>();
    ArrayList<String> phoneMResults = new ArrayList<String>();
    ArrayList<Bitmap> imageMResults = new ArrayList<Bitmap>();

    ArrayList<String> usernameEResults = new ArrayList<String>();
    ArrayList<String> phoneEResults = new ArrayList<String>();
    ArrayList<Bitmap> imageEResults = new ArrayList<Bitmap>();

    public AllEmployeesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_allemployees,container, false);

        ListView list4 = (ListView) rootView.findViewById(R.id.list_empA_username);
        ListView list5 = (ListView) rootView.findViewById(R.id.list_empA_phone);
        ListView list6 = (ListView) rootView.findViewById(R.id.list_empA_image);

        ListView list = (ListView) rootView.findViewById(R.id.list_supA_username);
        ListView list2 = (ListView) rootView.findViewById(R.id.list_supA_phone);
        ListView list3 = (ListView) rootView.findViewById(R.id.list_supA_image);

        openAndQueryDatabase();
        adapterMUser = new DbItemListAdapter(getActivity(),
                usernameMResults);
        list.setAdapter(adapterMUser);
        adapterMPhone = new DbItemListAdapter(getActivity(),
                phoneMResults);
        list2.setAdapter(adapterMPhone);

        adapterMImage = new DbImageListAdapter(getActivity(),
                imageMResults);
        list3.setAdapter(adapterMImage);

        adapterEUser = new DbItemListAdapter(getActivity(),
                usernameEResults);
        list4.setAdapter(adapterEUser);
        adapterEPhone = new DbItemListAdapter(getActivity(),
                phoneEResults);
        list5.setAdapter(adapterEPhone);

        adapterEImage = new DbImageListAdapter(getActivity(),
                imageEResults);
        list6.setAdapter(adapterEImage);

        return rootView;
    }

    private void openAndQueryDatabase() {
        DBHelper helper = new DBHelper(getActivity());
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cEUser = db.rawQuery("SELECT username FROM tbl_users INNER JOIN tbl_employees ON tbl_users.user_id = tbl_employees.user_id;", null);
        Cursor cEPhone = db.rawQuery("SELECT phone FROM tbl_users INNER JOIN tbl_employees ON tbl_users.user_id = tbl_employees.user_id;", null);
        Cursor cEImage = db.rawQuery("SELECT picture FROM tbl_employees;", null);

        Cursor cMUser = db.rawQuery("SELECT username FROM tbl_users INNER JOIN tbl_supervisors ON tbl_users.user_id = tbl_supervisors.user_id;", null);
        Cursor cMPhone = db.rawQuery("SELECT phone FROM tbl_users INNER JOIN tbl_supervisors ON tbl_users.user_id = tbl_supervisors.user_id;", null);
        Cursor cMImage = db.rawQuery("SELECT picture FROM tbl_supervisors;", null);

        if (cMUser != null) {
            if (cMUser.moveToFirst()) {
                do {
                    String username = cMUser.getString(cMUser.getColumnIndex("username"));
                    usernameMResults.add(username);
                } while (cMUser.moveToNext());
            }
        }
        if (cMPhone != null) {
            if (cMPhone.moveToFirst()) {
                do {
                    String phone = cMPhone.getString(cMPhone.getColumnIndex("phone"));
                    phoneMResults.add(phone);
                } while (cMPhone.moveToNext());
            }
        }
        if (cMImage != null) {
            if (cMImage.moveToFirst()) {
                do {
                    byte[] image = cMImage.getBlob(cMImage.getColumnIndex("picture"));
                    Bitmap aImage = BitmapFactory.decodeByteArray(image, 0, image.length);
                    imageMResults.add(aImage);
                } while (cMImage.moveToNext());
            }
        }

        if (cEUser != null) {
            if (cEUser.moveToFirst()) {
                do {
                    String username = cEUser.getString(cEUser.getColumnIndex("username"));
                    usernameEResults.add(username);
                } while (cEUser.moveToNext());
            }
        }
        if (cEPhone != null) {
            if (cEPhone.moveToFirst()) {
                do {
                    String phone = cEPhone.getString(cEPhone.getColumnIndex("phone"));
                    phoneEResults.add(phone);
                } while (cEPhone.moveToNext());
            }
        }
        if (cEImage != null) {
            if (cEImage.moveToFirst()) {
                do {
                    byte[] image = cEImage.getBlob(cEImage.getColumnIndex("picture"));
                    Bitmap aImage = BitmapFactory.decodeByteArray(image, 0, image.length);
                    imageEResults.add(aImage);
                } while (cEImage.moveToNext());
            }
        }
    }
}
