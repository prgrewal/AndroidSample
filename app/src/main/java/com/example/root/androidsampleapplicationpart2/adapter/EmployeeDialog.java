package com.example.root.androidsampleapplicationpart2.adapter;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.root.androidsampleapplicationpart2.R;
import com.example.root.androidsampleapplicationpart2.model.DBHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 8/23/16.
 */
public class EmployeeDialog extends DialogFragment implements
        AdapterView.OnItemSelectedListener {
    private DialogInterface.OnDismissListener onDismissListener;
    private static final int CAMERA_REQUEST = 1888;


    Bitmap photo = null;

    public EmployeeDialog() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_employees_dialog, new LinearLayout(getActivity()), false);

        final Spinner sName = (Spinner)view.findViewById(R.id.spinner_emp_username);
        final Spinner sDepartment = (Spinner)view.findViewById(R.id.spinner_emp_department);
        final Spinner sSupervisor = (Spinner)view.findViewById(R.id.spinner_emp_supervisor);


        Button photoButton = (Button) view.findViewById(R.id.add_emp_btn);

        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        /*ImageView imageView = (ImageView)view.findViewById(R.id.sup_image);
        imageView.setImageBitmap(photo);*/

        // Spinner Drop down elements
        List<String> userLabels = getUsernames();
        List<String> departmentLabels = getDepartments();
        List<String> supervisorLabels = getSupervisors();

        // Creating adapter for spinner
        ArrayAdapter<String> userDataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, userLabels);
        ArrayAdapter<String> departmentDataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, departmentLabels);
        ArrayAdapter<String> supervisorDataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, supervisorLabels);

        // Drop down layout style - list view with radio button
        userDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supervisorDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sName.setAdapter(new NothingSelectedSpinnerAdapter(
                userDataAdapter,
                R.layout.username_spinner_row_nothing_selected,
                getActivity()));
        sDepartment.setAdapter(new NothingSelectedSpinnerAdapter(
                departmentDataAdapter,
                R.layout.department_spinner_row_nothing_selected,
                getActivity()));
        sSupervisor.setAdapter(new NothingSelectedSpinnerAdapter(
                supervisorDataAdapter,
                R.layout.supervisor_spinner_row_nothing_selected,
                getActivity()));

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        ((Button) view.findViewById(R.id.okayemp_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String username = sName.getSelectedItem().toString();
                String department = sDepartment.getSelectedItem().toString();
                String supervisor = sSupervisor.getSelectedItem().toString();

                DBHelper helper = new DBHelper(getActivity());
                SQLiteDatabase db = helper.getWritableDatabase();

                String userIDQuery = "SELECT user_id FROM tbl_users WHERE username = '"+ username + "';";
                String departmentIDQuery = "SELECT department_id FROM tbl_departments WHERE name = '" + department + "';";

                String usernameID = null;
                String departmentID = null;

                Cursor cUser = db.rawQuery(userIDQuery, null);
                if (cUser != null) {
                    if (cUser.moveToFirst()) {
                        do {
                            usernameID = cUser.getString(cUser.getColumnIndex("user_id"));
                        } while (cUser.moveToNext());
                    }
                }

                String supervisorIDQuery = "SELECT supervisor_id from tbl_supervisors WHERE user_id = '" + usernameID + "';";
                String supervisorID = null;

                Cursor cDepartment = db.rawQuery(departmentIDQuery, null);
                if (cDepartment != null) {
                    if (cDepartment.moveToFirst()) {
                        do {
                            departmentID = cDepartment.getString(cDepartment.getColumnIndex("department_id"));
                        } while (cDepartment.moveToNext());
                    }
                }

                Cursor cSupervisor = db.rawQuery(supervisorIDQuery, null);
                if (cSupervisor != null) {
                    if (cSupervisor.moveToFirst()) {
                        do {
                            supervisorID = cSupervisor.getString(cSupervisor.getColumnIndex("supervisor_id"));
                        } while (cSupervisor.moveToNext());
                    }
                }

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 0, stream);
                byte[] insPhoto = stream.toByteArray();




                /*String supervisorInsert = "INSERT INTO tbl_supervisors (department_id, user_id, picture) VALUES ("
                        + departmentID + ", " + usernameID + ");" ;*/

                ContentValues cv = new ContentValues();
                cv.put("department_id",    departmentID);
                cv.put("user_id",    usernameID);
                cv.put("supervisor_id", supervisorID);
                cv.put("picture",   insPhoto);
                db.insert( "tbl_employees", null, cv );

                /*SQLiteStatement statement = db.compileStatement(supervisorInsert);
                statement.executeInsert();*/

                EmployeeDialog.this.dismiss();

            }
        });

        ((Button) view.findViewById(R.id.cancelemp_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EmployeeDialog.this.dismiss();
            }
        });



        return builder;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
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

    private List<String> getDepartments() {
        DBHelper dbhelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        List<String> departmentLabels = new ArrayList<String>();

        String selectQuery = "SELECT name FROM tbl_departments;";


        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                departmentLabels.add(name);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return departmentLabels;
    }

    private List<String> getSupervisors() {
        DBHelper dbhelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        List<String> supervisorLabels = new ArrayList<String>();

        String selectQuery = "SELECT username FROM tbl_users INNER JOIN tbl_supervisors ON tbl_users.user_id = tbl_supervisors.user_id;";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("username"));
                supervisorLabels.add(name);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return supervisorLabels;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();



    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}

