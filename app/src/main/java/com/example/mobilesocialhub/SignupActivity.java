package com.example.mobilesocialhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilesocialhub.databinding.ActivitySignupBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding mBinding;
    EditText username;
    EditText password;
    EditText confirmPassword;
    Button signup;
    FirebaseDatabase database;
    TextView goToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_signup);
        username = mBinding.signupUsername;
        password = mBinding.signupPassword;
        confirmPassword=mBinding.confirmPassword;
        signup = mBinding.signUpbutton;
        goToLogin=mBinding.loginButton;

        database= FirebaseDatabase.getInstance();
        DatabaseReference userSignupRef = database.getReference().child("Users");

        String TAG="Login";

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check two password are matched
                String typePassword = password.getText().toString();
                if(typePassword.equals(confirmPassword.getText().toString())){

                    userSignupRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String typeUsername = username.getText().toString();

                            //check if username already existed
                           if(snapshot.child(typeUsername).exists()){
                               Toast.makeText(view.getContext(),"Username already existed", Toast.LENGTH_LONG).show();
                           }else{
                               // put username and password in the database
                               userSignupRef.child(typeUsername).setValue(typePassword);

                               //jump to the main page and pass the username
                               Intent intent = new Intent(view.getContext(), TestActivity.class);
                               intent.putExtra("username",typeUsername);
                               view.getContext().startActivity(intent);
                           }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }

                    });

                }else{
                    Toast.makeText(view.getContext(),"Password not match, try again!", Toast.LENGTH_LONG).show();
                }
            }
        });




    }
}