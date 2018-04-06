package com.example.prateek.testmp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class student_list_fragment extends Fragment {


    DBManager dbManager = new DBManager();

    ArrayList<User> userArrayList = new ArrayList<>();

    ProgressDialog progressDialog;



    public student_list_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        progressDialog = new ProgressDialog(getContext());

        try {
            methodF();
        }catch(Exception e){
            e.printStackTrace();
        }

        return inflater.inflate(R.layout.fragment_student_list_fragment, container, false);
    }

    public void methodF(){

        progressDialog.setMessage("Loading List");
        progressDialog.show();

//        Intent intent = getIntent();
//        final String userType = intent.getStringExtra("userType");
//        //Log.i("UserType**********",userType);
//
//        // String[] items = {"Shree", "Shrayans", "abc", "kamlesh", "vaidik", "raju"};
//        Log.i("UserTypeValueFrom****", userType);


        try {
            dbManager.getUserListFromDB("Student", new MyCallback() {
                @Override
                public void onCallback(ArrayList<User> value) {

                    userArrayList = value;

                    callMe();
                    progressDialog.dismiss();
                }

            });


            // Log.i("ArraySize", userArrayList.size() + "");

            // Log.i("UserTypeValue-----", userArrayList.get(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void callMe() {

        try {
//
//            Log.i("Size Of Array",userArrayList.size()+"");
//            final User user = userArrayList.get(0);
//
//            Log.i("FullNAME", user.Full_Name);
//            Log.i("ProfileImage",user.profileImage);


            ArrayAdapter arrayAdapter = new CustomAdapter(getContext(), userArrayList);

            ListAdapter shreeAdapter = new CustomAdapter(getContext(), userArrayList);
            ListView shreeListView = (ListView) getView().findViewById(R.id.userListView);

            shreeListView.setAdapter(shreeAdapter);

            shreeListView.setOnItemClickListener(

                    new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            User user1=(User) adapterView.getItemAtPosition(i);
                            String item = user1.Full_Name;
                            //Toast.makeText(UserListActivity.this, item, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getContext(),ProfileActivity.class);
                            intent.putExtra("userObject",userArrayList.get(i));
                            startActivity(intent);
                        }
                    }

            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
