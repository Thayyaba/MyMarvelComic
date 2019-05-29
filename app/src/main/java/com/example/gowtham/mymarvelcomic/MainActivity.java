package com.example.gowtham.mymarvelcomic;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder dialog;
    @InjectView(R.id.email)
    EditText email;
    @InjectView(R.id.password)
    EditText password;

    @InjectView(R.id.forgott)
    TextView forgot;
    @InjectView(R.id.create_account)
    TextView create;

    FirebaseAuth firebaseAuth;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        firebaseAuth =FirebaseAuth.getInstance();

    }

    public void signIn(View view) {
        showProgress();

        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login Success...", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                            startActivity(new Intent(MainActivity.this,ListActivity1.class));
                        }
                    }
                });
    }

    public void register(View view) {

        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Registering User..");
        View v = LayoutInflater.from(this).inflate(R.layout.edit_texts,null,false);
        final EditText e,p;
        e = v.findViewById(R.id.email);
        p = v.findViewById(R.id.password);

        dialog.setView(v);
        dialog.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registerUser(e.getText().toString(),p.getText().toString());
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    private void registerUser(String e,String p) {
        showProgress();
        firebaseAuth.createUserWithEmailAndPassword(e,p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Success...", Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }
                    }
                });
    }

    public void forgot(View view) {
        dialog  = new AlertDialog.Builder(this);
        dialog.setTitle("Forgot password.");
        View  view1 = LayoutInflater.from(this).inflate(R.layout.single_edittext,null,false);
        final EditText editText = view1.findViewById(R.id.forgotpassword);
        dialog.setView(view1);
        dialog.setPositiveButton("Send Mail", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showProgress();
                sendRecoveryMail(editText.getText().toString());
            }
        });
        dialog.show();
    }

    private void sendRecoveryMail(String s) {
        firebaseAuth.sendPasswordResetEmail(s)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Recovery Email has been sent...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        progress.dismiss();
    }
    public void showProgress(){
        progress = new ProgressDialog(this);
        progress.show();
    }
}
