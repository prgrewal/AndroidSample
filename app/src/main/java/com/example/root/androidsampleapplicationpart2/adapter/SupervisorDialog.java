package com.example.root.androidsampleapplicationpart2.adapter;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
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
 * Created by root on 8/19/16.
 */
public class SupervisorDialog extends DialogFragment implements
        AdapterView.OnItemSelectedListener {
    private DialogInterface.OnDismissListener onDismissListener;
    private static final int CAMERA_REQUEST = 1888;


    Bitmap photo = null;

    public SupervisorDialog() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_supervisors_dialog, new LinearLayout(getActivity()), false);

        final Spinner sName = (Spinner)view.findViewById(R.id.spinner_username);
        final Spinner sDepartment = (Spinner)view.findViewById(R.id.spinner_department);


        Button photoButton = (Button) view.findViewById(R.id.add_sup_btn);

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

        // Creating adapter for spinner
        ArrayAdapter<String> userDataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, userLabels);
        ArrayAdapter<String> departmentDataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, departmentLabels);

        // Drop down layout style - list view with radio button
        userDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sName.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        userDataAdapter,
                        R.layout.username_spinner_row_nothing_selected,
                        getActivity()));
        sDepartment.setAdapter(new NothingSelectedSpinnerAdapter(
                departmentDataAdapter,
                R.layout.department_spinner_row_nothing_selected,
                getActivity()));

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.setContentView(view);

        ((Button) view.findViewById(R.id.okaysup_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String username = sName.getSelectedItem().toString();
                String department = sDepartment.getSelectedItem().toString();

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

                Cursor cDepartment = db.rawQuery(departmentIDQuery, null);
                if (cDepartment != null) {
                    if (cDepartment.moveToFirst()) {
                        do {
                            departmentID = cDepartment.getString(cDepartment.getColumnIndex("department_id"));
                        } while (cDepartment.moveToNext());
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
                cv.put("picture",   insPhoto);
                db.insert( "tbl_supervisors", null, cv );

                /*SQLiteStatement statement = db.compileStatement(supervisorInsert);
                statement.executeInsert();*/

                SupervisorDialog.this.dismiss();

            }
        });

        ((Button) view.findViewById(R.id.cancelsup_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SupervisorDialog.this.dismiss();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
