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
 * Created by root on 8/17/16.
 */
public class UserDialog extends DialogFragment {
    private DialogInterface.OnDismissListener onDismissListener;

    public UserDialog() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_users_dialog, new LinearLayout(getActivity()), false);
        final EditText rName = (EditText)view.findViewById(R.id.user_name);
        final EditText rUsername = (EditText)view.findViewById(R.id.user_username);
        final EditText rPass = (EditText)view.findViewById(R.id.user_pass);
        final EditText rPhone = (EditText)view.findViewById(R.id.user_phone);
        final EditText rEmail = (EditText)view.findViewById(R.id.user_email);

        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        ((Button) view.findViewById(R.id.okayuser_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = rName.getText().toString();
                String username = rUsername.getText().toString();
                String pass = rPass.getText().toString();
                String phone = rPhone.getText().toString();
                String email = rEmail.getText().toString();

                DBHelper helper = new DBHelper(getActivity());
                SQLiteDatabase db = helper.getWritableDatabase();
                String sql = "INSERT INTO tbl_users (username, name, pass, phone, email) VALUES ("
                        + "\"" + username + "\"" +", "
                        + "\"" + name + "\"" +", "
                        + "\"" + pass + "\"" +", "
                        + "\"" + phone + "\"" +", "
                        + "\"" + email + "\"" +")";
                SQLiteStatement statement = db.compileStatement(sql);
                statement.executeInsert();
                UserDialog.this.dismiss();
            }
        });
        ((Button) view.findViewById(R.id.canceluser_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserDialog.this.dismiss();
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
