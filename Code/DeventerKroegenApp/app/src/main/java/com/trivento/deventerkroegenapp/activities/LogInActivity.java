package com.trivento.deventerkroegenapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trivento.deventerkroegenapp.R;
import com.trivento.deventerkroegenapp.tasks.LoginTask;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final boolean loggedIn = checkLogin();
        if(loggedIn){
            Toast.makeText(LogInActivity.this, "Er is al ingelogd", Toast.LENGTH_SHORT).show();
            finish();
        }

        final EditText etUsername = (EditText) findViewById(R.id.et_username);
        final EditText etPassword = (EditText) findViewById(R.id.et_password);

        Button btnLogin = (Button) findViewById(R.id.btn_login);
        if (btnLogin != null) {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();

                    LoginTask loginTask = new LoginTask(getApplicationContext());
                    loginTask.execute(username, password);
                }
            });
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean checkLogin() {
        //TODO add check to sharedPrefs for login
        return false;
    }
}
