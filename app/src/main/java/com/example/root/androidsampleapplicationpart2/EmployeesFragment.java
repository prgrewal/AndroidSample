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
import com.example.root.androidsampleapplicationpart2.adapter.SupervisorDialog;
import com.example.root.androidsampleapplicationpart2.model.DBHelper;

import java.util.ArrayList;

/**
 * Created by root on 8/14/16.
 */
public class EmployeesFragment extends Fragment {

    private DbItemListAdapter adapterUser;
    private DbItemListAdapter adapterPhone;
    private DbImageListAdapter adapterImage;

    ArrayList<String> usernameResults = new ArrayList<String>();
    ArrayList<String> phoneResults = new ArrayList<String>();
    ArrayList<Bitmap> imageResults = new ArrayList<Bitmap>();

    public EmployeesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_employees,container, false);

        ListView list = (ListView) rootView.findViewById(R.id.list_emp_username);
        ListView list2 = (ListView) rootView.findViewById(R.id.list_emp_phone);
        ListView list3 = (ListView) rootView.findViewById(R.id.list_emp_image);

        openAndQueryDatabase();
        adapterUser = new DbItemListAdapter(getActivity(),
                usernameResults);
        list.setAdapter(adapterUser);
        adapterPhone = new DbItemListAdapter(getActivity(),
                phoneResults);
        list2.setAdapter(adapterPhone);

        adapterImage = new DbImageListAdapter(getActivity(),
                imageResults);
        list3.setAdapter(adapterImage);

        Button dialogBtn = (Button) rootView.findViewById(R.id.employeeadd_btn);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                FragmentManager fm = getFragmentManager();
                EmployeeDialog employeeDialog = new EmployeeDialog();
                employeeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        DbItemListAdapter adapterUser;
                        DbItemListAdapter adapterPhone;
                        DbImageListAdapter adapterImage;


                        ArrayList<String> usernameResults = new ArrayList<String>();
                        ArrayList<String> phoneResults = new ArrayList<String>();
                        ArrayList<Bitmap> imageResults = new ArrayList<Bitmap>();


                        ListView list = (ListView) getActivity().findViewById(R.id.list_emp_username);
                        ListView list2 = (ListView) getActivity().findViewById(R.id.list_emp_phone);
                        ListView list3 = (ListView) getActivity().findViewById(R.id.list_emp_image);


                        DBHelper helper = new DBHelper(getActivity());
                        SQLiteDatabase db = helper.getWritableDatabase();
                        Cursor cUser = db.rawQuery("SELECT username FROM tbl_users INNER JOIN tbl_employees ON tbl_users.user_id = tbl_employees.user_id;", null);
                        Cursor cPhone = db.rawQuery("SELECT phone FROM tbl_users INNER JOIN tbl_employees ON tbl_users.user_id = tbl_employees.user_id;", null);
                        Cursor cImage = db.rawQuery("SELECT picture FROM tbl_employees;", null);
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
                        if (cImage != null) {
                            if (cImage.moveToFirst()) {
                                do {
                                    byte[] image = cImage.getBlob(cImage.getColumnIndex("picture"));
                                    Bitmap aImage = BitmapFactory.decodeByteArray(image, 0, image.length);
                                    imageResults.add(aImage);
                                } while (cImage.moveToNext());
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


                        adapterImage = new DbImageListAdapter(getActivity(),
                                imageResults);
                        adapterImage.notifyDataSetChanged();
                        list3.setAdapter(adapterImage);
                    }
                });
                employeeDialog.show(fm, "Add Employee");
            }
        });

        return rootView;
    }

    private void openAndQueryDatabase() {
        DBHelper helper = new DBHelper(getActivity());
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cUser = db.rawQuery("SELECT username FROM tbl_users INNER JOIN tbl_employees ON tbl_users.user_id = tbl_employees.user_id;", null);
        Cursor cPhone = db.rawQuery("SELECT phone FROM tbl_users INNER JOIN tbl_employees ON tbl_users.user_id = tbl_employees.user_id;", null);
        Cursor cImage = db.rawQuery("SELECT picture FROM tbl_employees;", null);

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
        if (cImage != null) {
            if (cImage.moveToFirst()) {
                do {
                    byte[] image = cImage.getBlob(cImage.getColumnIndex("picture"));
                    Bitmap aImage = BitmapFactory.decodeByteArray(image, 0, image.length);
                    imageResults.add(aImage);
                } while (cImage.moveToNext());
            }
        }
    }
}
