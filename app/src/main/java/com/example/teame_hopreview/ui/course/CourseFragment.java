package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseFragment extends Fragment {

    private static final String TAG = "dbref: ";


    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    private CourseItem courseItem;
    protected ArrayList<CourseItem> myCourses;
    private CourseAdapter ca;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View myView = inflater.inflate(R.layout.frag_course, container, false);


        context = getActivity().getApplicationContext();

        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("Courses");
        System.out.println("Reached here");
        myList = (RecyclerView) myView.findViewById(R.id.myList);
        myCard = (CardView) myView.findViewById(R.id.course_card);
        myCourses = new ArrayList<CourseItem>();

        ca = new CourseAdapter(context, myCourses);

        myList.setAdapter(ca);
        myList.setLayoutManager(new LinearLayoutManager(context));

        dbref = FirebaseDatabase.getInstance().getReference();

        // TO DO: Firebase
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long count = snapshot.getChildrenCount();
                Log.d(TAG, "Children count: " + count);
                Log.d(TAG, "Course count: " + snapshot.child("courses").getChildrenCount());

                myCourses.clear();
                Iterable<DataSnapshot> courses = snapshot.child("courses").getChildren();


                for (DataSnapshot crs : courses) {
                    myCourses.add(crs.getValue(CourseItem.class));
                }

                ca.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        return myView;
    }

}