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

import com.example.mobilesocialhub.databinding.ActivityLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding mBinding;
    EditText username;
    EditText password;
    Button loginButton;
    FirebaseDatabase database;
    TextView goToSignup;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        username = mBinding.LoginUserName;
        password = mBinding.LoginPassword;
        loginButton = mBinding.loginButton;
        goToSignup = mBinding.signUp;

        //Connect to Database
        database= FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference().child("Users");
        String TAG="Login";


        // Click Login and check if there exists matched username and password
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d(TAG,snapshot.toString());
                        System.out.println(usernameText);

                        // check username exists in the database
                        if(snapshot.child(usernameText).exists()){
                            if(passwordText.equals(snapshot.child(usernameText).child("password").getValue())){
                                Intent intent = new Intent(view.getContext(), TestActivity.class);
                                intent.putExtra("username",usernameText);
                                view.getContext().startActivity(intent);
                            }else{
                                Toast.makeText(view.getContext(), "Password seems not right TAT", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(view.getContext(),"Username seems not exist, maybe signup?", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }

                });
            }
        });

        //Go to signup page
        goToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(view.getContext(), SignupActivity.class);
                view.getContext().startActivity(intent2);
            }
        });



    }
}