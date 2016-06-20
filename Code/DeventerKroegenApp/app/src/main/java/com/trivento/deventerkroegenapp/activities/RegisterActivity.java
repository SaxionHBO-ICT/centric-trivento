package com.trivento.deventerkroegenapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trivento.deventerkroegenapp.R;
import com.trivento.deventerkroegenapp.tasks.NewUserTask;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText etVoornaam = (EditText) findViewById(R.id.et_voornaam);
        final EditText etAchternaam = (EditText) findViewById(R.id.et_achternaam);
        final EditText etAdres = (EditText) findViewById(R.id.et_adres);
        final EditText etPostcode = (EditText) findViewById(R.id.et_postcode);
        final EditText etWoonplaats = (EditText) findViewById(R.id.et_woonplaats);
        final EditText etLand = (EditText) findViewById(R.id.et_land);
        final EditText etMail = (EditText) findViewById(R.id.et_mail);
        final EditText etWachtwoord = (EditText) findViewById(R.id.et_wachtwoord);
        final EditText etWachtwoordCheck = (EditText) findViewById(R.id.et_wachtwoordcheck);
        Button btnRegister = (Button) findViewById(R.id.btn_register);

        assert btnRegister != null;
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String voornaam = etVoornaam.getText().toString();
                String achternaam = etAchternaam.getText().toString();
                String adres = etAdres.getText().toString();
                String postcode = etPostcode.getText().toString();
                String woonplaats = etWoonplaats.getText().toString();
                String land = etLand.getText().toString();
                String mail = etMail.getText().toString();
                String wachtwoord = null;
                String wachtwoordCheck = null;
                wachtwoord = md5(etWachtwoord.getText().toString());
                wachtwoordCheck = md5(etWachtwoordCheck.getText().toString());

                //Als de wachtwoordCheck overeen komt, run de task voor het aanmaken van een gebruiker
                if(wachtwoord.equals(wachtwoordCheck)){
                    NewUserTask newUserTask = new NewUserTask(RegisterActivity.this);
                    newUserTask.execute(voornaam, achternaam, adres, postcode, woonplaats, land, mail, wachtwoord);
                } else {
                    Toast.makeText(RegisterActivity.this, "Wachtwoorden komen niet overeen", Toast.LENGTH_LONG).show();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Maak een md5 Versie van het wachtwoord
     * Source: http://stackoverflow.com/questions/4846484/md5-hashing-in-android
     * @param s De input string
     * @return MD5 versie van de input string
     */
    private String md5(String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
