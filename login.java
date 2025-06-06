package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class login extends AppCompatActivity {

    EditText LoginEmail,Loginpass;
    Button loginbtn;
    TextView createBtn,forgetpass;

    FirebaseAuth mAuth;
    public  static  final  String Shared_Prefs="SharedPrefs";

    private CheckBox RememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LoginEmail= findViewById(R.id.email);
        Loginpass= findViewById(R.id.password);
        loginbtn=findViewById(R.id.loginBtn);
        createBtn= findViewById(R.id.createbtn);
        forgetpass=findViewById(R.id.forgetpasstextview);

       // RememberMe= findViewById(R.id.RememberMeChk);
       // Paper.init(this);

        mAuth=FirebaseAuth.getInstance();



        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), register.class));
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = LoginEmail.getText().toString().trim();
                String password = Loginpass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    LoginEmail.setError("Email is required");
                } else if (TextUtils.isEmpty(password)) {
                    Loginpass.setError("Password is required");
                } else {
                    AllowAccessToAccount(email, password);
                }
            }
        });
    }

    /*private  void CheckBox(){

        SharedPreferences sharedPreferences=getSharedPreferences(Shared_Prefs,MODE_PRIVATE);
        String check=sharedPreferences.getString("name","");
    }*/

    private void AllowAccessToAccount (final String email, final String password)
    {

       /* SharedPreferences sharedPreferences=getSharedPreferences(Shared_Prefs,MODE_PRIVATE);
        String check=sharedPreferences.getString("remember_me","");  // Use a descriptive key

        SharedPreferences sharedPreference=getSharedPreferences(Shared_Prefs,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreference.edit();
        if (RememberMe.isChecked()) {  // Check if checkbox is checked before storing
            editor.putString("remember_me","true");
        } else {
            //editor.putString("remember_me","f");  // Store empty string if not checked
        }
        editor.apply();
        */

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Log in successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Navigation_Activity.class));
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Email  doesn't exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

   /* private void handleSuccessfulLogin(String check) {
        if (check.equals("true")) {
            Toast.makeText(getApplicationContext(), "Log in successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Navigation_Activity.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), login.class));
            finish(); // Optionally, handle login success even if "remember me" is not checked (navigate to same activity but don't skip login screen)
        }
        }*/
}

