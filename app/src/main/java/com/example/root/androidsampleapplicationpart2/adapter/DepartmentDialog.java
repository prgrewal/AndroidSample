package com.example.root.androidsampleapplicationpart2.adapter;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.example.root.androidsampleapplicationpart2.R;
import com.example.root.androidsampleapplicationpart2.model.DBHelper;

/**
 * Created by root on 8/16/16.
 */
public class DepartmentDialog extends DialogFragment {

   private DialogInterface.OnDismissListener onDismissListener;

    public DepartmentDialog() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_department_dialog, new LinearLayout(getActivity()), false);
        final EditText eName = (EditText)view.findViewById(R.id.department_name);

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        ((Button) view.findViewById(R.id.okaydep_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = eName.getText().toString();
                DBHelper helper = new DBHelper(getActivity());
                SQLiteDatabase db = helper.getWritableDatabase();
                String sql = "INSERT INTO tbl_departments (name) VALUES ("
                        + "\"" + name + "\"" +")";
                SQLiteStatement statement = db.compileStatement(sql);
                statement.executeInsert();
                DepartmentDialog.this.dismiss();

            }
        });
        ((Button) view.findViewById(R.id.canceldep_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DepartmentDialog.this.dismiss();
            }
        });



        return builder;
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
}
