package com.example.root.androidsampleapplicationpart2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by root on 8/14/16.
 */
public class HomeFragment extends Fragment {
    Button usersBtn;
    Button departmentsBtn;
    Button employeesBtn;
    Button supervisorsBtn;
    Button allemployeesBtn;
    Button messagesBtn;
    private String[] navMenuTitles;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container, false);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        usersBtn = (Button) rootView.findViewById(R.id.users_btn);
        usersBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment fragment = new UsersFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                setTitle(navMenuTitles[1]);
            }
        });

        departmentsBtn = (Button) rootView.findViewById(R.id.departments_btn);
        departmentsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment fragment = new DepartmentFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                setTitle(navMenuTitles[2]);
            }
        });

        employeesBtn = (Button) rootView.findViewById(R.id.employees_btn);
        employeesBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment fragment = new EmployeesFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                setTitle(navMenuTitles[3]);
            }
        });

        messagesBtn = (Button) rootView.findViewById(R.id.message_btn);
        messagesBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment fragment = new MessageFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                setTitle(navMenuTitles[5]);
            }
        });

        supervisorsBtn = (Button) rootView.findViewById(R.id.supervisors_btn);
        supervisorsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment fragment = new SupervisorsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                setTitle(navMenuTitles[4]);
            }
        });

        allemployeesBtn = (Button) rootView.findViewById(R.id.allemployees_btn);
        allemployeesBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment fragment = new AllEmployeesFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                setTitle(navMenuTitles[6]);
            }
        });

        return rootView;
    }


    public void setTitle(CharSequence title) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
    }


}
