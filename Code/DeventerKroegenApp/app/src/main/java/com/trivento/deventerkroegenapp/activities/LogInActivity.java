package com.trivento.deventerkroegenapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trivento.deventerkroegenapp.R;
import com.trivento.deventerkroegenapp.tasks.LoginTask;
import com.trivento.deventerkroegenapp.util.Reference;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences(Reference.LOCATION, MODE_PRIVATE);

        if(sharedPreferences.getString(Reference.USERNAME, null) != null){
            Toast.makeText(this, "Uitgelogd", Toast.LENGTH_SHORT).show();
            sharedPreferences.edit().remove(Reference.USERNAME).apply();
            sharedPreferences.edit().remove(Reference.PASSWORD).apply();
        }

        final EditText etUsername = (EditText) findViewById(R.id.et_username);
        final EditText etPassword = (EditText) findViewById(R.id.et_wachtwoord);

        Button btnLogin = (Button) findViewById(R.id.btn_login);
        if (btnLogin != null) {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();

                    LoginTask loginTask = new LoginTask(LogInActivity.this, true);
                    loginTask.execute(username, password);
                }
            });
        }

        TextView tvRegister = (TextView) findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static boolean checkLogin(Context context, boolean showToast) {
        String username, password;
        SharedPreferences sharedPreferences = context.getSharedPreferences(Reference.LOCATION, MODE_PRIVATE);
        String checkUsername = sharedPreferences.getString(Reference.USERNAME, null);
        if(checkUsername != null){
            username = checkUsername;
        } else {
            return false;
        }
        String checkPassword = sharedPreferences.getString(Reference.PASSWORD, null);
        if(checkPassword != null){
            password = checkPassword;
        } else {
            return false;
        }
        LoginTask loginTask = new LoginTask(context, true, showToast);
        loginTask.execute(username, password);
        return Reference.loginAccepted;
    }
}
