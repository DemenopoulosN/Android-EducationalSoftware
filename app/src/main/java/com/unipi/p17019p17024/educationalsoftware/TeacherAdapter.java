package com.unipi.p17019p17024.educationalsoftware;

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


    //User Authentication
    public FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private DatabaseReference userIDRef;

    public TeacherAdapter(
            @NonNull FirebaseRecyclerOptions<Students> options)
    {
        super(options);
    }


    @Override
    protected void
    onBindViewHolder(@NonNull TeacherAdapter.TeacherViewholder holder,
                     int position, @NonNull Students model)
    {
        //User Authentication
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        String userID = currentUser.getUid();



        userIDRef = FirebaseDatabase.getInstance().getReference().child("Students").child(userID);

        //String productIDs = getRef(position).getKey();

        userIDRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    //String pImage = snapshot.child("image").getValue().toString();

                    //Picasso.get().load(pImage).placeholder(R.drawable.ic_product_image).into(holder.productImage);

                    String sName = snapshot.child("name").getValue().toString();
                    String sEmail = snapshot.child("email").getValue().toString();

                    holder.studentName.setText(sName);
                    holder.studentEmail.setText(sEmail);

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
        //CircleImageView studentImage;

        public TeacherViewholder(@NonNull View itemView)
        {
            super(itemView);

            studentName = itemView.findViewById(R.id.textViewStudentName);
            studentEmail = itemView.findViewById(R.id.textViewStudentEmail);
            //studentImage = itemView.findViewById(R.id.student_profile_image);
        }
    }


}
