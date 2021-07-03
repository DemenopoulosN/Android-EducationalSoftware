package com.unipi.p17019p17024.educationalsoftware;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherAdapter extends FirebaseRecyclerAdapter<
        Students, TeacherAdapter.TeacherViewholder> {

    //private Context myContext;
    private Context context;

    //User Authentication
    public FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private DatabaseReference studentsRef;

    public TeacherAdapter(
            //Context context,
            @NonNull Activity context,
            @NonNull FirebaseRecyclerOptions<Students> options)
    {
        super(options);
        //myContext = context;
        this.context = context;
        //super(context, options);
        //super(options);
    }


    @Override
    protected void
    onBindViewHolder(@NonNull TeacherAdapter.TeacherViewholder holder,
                     int position, @NonNull Students model)
    {
        //User Authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        studentsRef = FirebaseDatabase.getInstance().getReference().child("Students");

        String studentsIDs = getRef(position).getKey();

        studentsRef.child(studentsIDs).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String sName = dataSnapshot.child("name").getValue().toString();
                    String sEmail = dataSnapshot.child("email").getValue().toString();

                    holder.studentName.setText(sName);
                    holder.studentEmail.setText(sEmail);


                    //
                    //On Click
                    //
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent studentIntent = new Intent(context, StudentsDataActivity.class);

                            studentIntent.putExtra("studentID", studentsIDs);
                            studentIntent.putExtra("studentName", sName);
                            studentIntent.putExtra("studentEmail", sEmail);

                            context.startActivity(studentIntent);
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


    @NonNull
    @Override
    public TeacherViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.students, parent, false);
        return new TeacherAdapter.TeacherViewholder(view);
    }


    class TeacherViewholder extends RecyclerView.ViewHolder {
        TextView studentName, studentEmail;

        public TeacherViewholder(@NonNull View itemView)
        {
            super(itemView);

            studentName = itemView.findViewById(R.id.textViewStudentName);
            studentEmail = itemView.findViewById(R.id.textViewStudentEmail);
        }
    }


}
