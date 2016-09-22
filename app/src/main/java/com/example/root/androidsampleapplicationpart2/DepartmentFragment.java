package com.example.root.androidsampleapplicationpart2;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.root.androidsampleapplicationpart2.adapter.DbItemListAdapter;
import com.example.root.androidsampleapplicationpart2.adapter.DepartmentDialog;
import com.example.root.androidsampleapplicationpart2.model.DBHelper;

import java.util.ArrayList;

/**
 * Created by root on 8/14/16.
 */
public class DepartmentFragment extends Fragment {

    ArrayList<String> departmentResults = new ArrayList<String>();

    public DepartmentFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_department, container, false);

       ListView list = (ListView) rootView.findViewById(R.id.list_department);

        openAndQueryDatabase();
        DbItemListAdapter adapterDepartment;

        adapterDepartment = new DbItemListAdapter(getActivity(),
                departmentResults);
        adapterDepartment.notifyDataSetChanged();

        list.setAdapter(adapterDepartment);


        Button dialogBtn = (Button) rootView.findViewById(R.id.departmentadd_btn);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                FragmentManager fm = getFragmentManager();
                DepartmentDialog departmentDialog = new DepartmentDialog();
                departmentDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        ArrayList<String> departmentResults = new ArrayList<String>();
                        ListView list = (ListView) getActivity().findViewById(R.id.list_department);
                        DBHelper helper = new DBHelper(getActivity());
                        SQLiteDatabase db = helper.getWritableDatabase();
                        Cursor cDepartment = db.rawQuery("SELECT name FROM tbl_departments;", null);
                        if (cDepartment != null) {
                            if (cDepartment.moveToFirst()) {
                                do {
                                    String username = cDepartment.getString(cDepartment.getColumnIndex("name"));
                                    departmentResults.add(username);
                                } while (cDepartment.moveToNext());
                            }

                        }
                        DbItemListAdapter adapterDepartment;
                        adapterDepartment = new DbItemListAdapter(getActivity(),
                                departmentResults);
                        adapterDepartment.notifyDataSetChanged();

                        list.setAdapter(adapterDepartment);


                    }
                });
                departmentDialog.show(fm, "Add Department");
            }
        });

        return rootView;

    }

    private void openAndQueryDatabase() {
        DBHelper helper = new DBHelper(getActivity());
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cDepartment = db.rawQuery("SELECT name FROM tbl_departments;", null);
        if (cDepartment != null) {
            if (cDepartment.moveToFirst()) {
                do {
                    String username = cDepartment.getString(cDepartment.getColumnIndex("name"));
                    departmentResults.add(username);
                } while (cDepartment.moveToNext());
            }

        }
    }

}
