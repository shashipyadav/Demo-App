package com.itaakash.android.demoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionDecryptionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText et_noramal,et_key;
    private TextView tv_output;

    private String outputString;
    private String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encrpitadecactivity);
        init();


    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitleTextColor(Color.WHITE);
        et_noramal = findViewById(R.id.et_noramal);
        et_key = findViewById(R.id.et_key);
        tv_output = findViewById(R.id.tv_output);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Encryption / Decryption");

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Button btnencp = findViewById(R.id.btnencp);
        btnencp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    outputString = encrypt(et_noramal.getText().toString(),et_key.getText().toString());
                    Log.d("outputString",outputString);
                    tv_output.setText(outputString);

                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });

        Button btndec = findViewById(R.id.btndec);
        btndec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    outputString = decrypt(outputString,et_key.getText().toString());
                    Log.d("outputString",outputString);
                    tv_output.setText(outputString);


                }catch (Exception ex){
                    Toast.makeText(EncryptionDecryptionActivity.this,"Wrong Password",Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }

            }
        });

    }

    private String decrypt(String outputString, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c =Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodeValue = Base64.decode(outputString,Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodeValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private String encrypt(String data, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c =Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception {
      final   MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] bytes =password.getBytes("UTF-8");
      digest.update(bytes,0,bytes.length);
      byte[] key= digest.digest();
      SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
      return  secretKeySpec;
    }





}