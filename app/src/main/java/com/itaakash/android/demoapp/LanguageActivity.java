package com.itaakash.android.demoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class LanguageActivity extends AppCompatActivity {
    String[] languages;

    //////////////
    private static Locale myLocale;
    private static final String Locale_Preference = "Locale Preference";
    private static final String Locale_KeyValue = "Saved Locale";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    String language = "en";
    private SharedPreferences sharedPref;
    private boolean isLogin;
    private ListView listView;
    public static String shrprf = "Demo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);
        languages = getResources().getStringArray(R.array.languages);
        final Intent intent = getIntent();
        isLogin =  intent.getBooleanExtra("isLogin",false);


        sharedPreferences = getSharedPreferences(Locale_Preference, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        listView = findViewById(R.id.language_listview);
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, languages);
        listView.setAdapter(itemsAdapter);

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(LanguageSelect.this, languages[i]+" Language Selected : ", Toast.LENGTH_SHORT).show();

                if ("English".equalsIgnoreCase(languages[i])){
                    language="en";
                } else if ("لغة".equalsIgnoreCase(languages[i])) {
                    language="ar";
                }
                else {
                    language="en";
                }
                if (isLogin) {
                    Intent intent = new Intent();
                    intent.putExtra("lang",languages[i]);
                    setResult(121,intent);
                }/* else if (sharedPref != null && sharedPref.getString("auth_token", "").isEmpty()) {
                    Intent intent = new Intent(LanguageActivity.this, LoginActivity.class);

                    intent.putExtra("lang",languages[i]);
                    startActivity(intent);
                    finish();
              }  */else {
                    Intent intent = new Intent();
                    intent.putExtra("lang",languages[i]);
                    setResult(121,intent);
//                startActivity(intent);
                }




                editor.putString(Locale_KeyValue, language);
                editor.apply();

                finish();


            }
        });
    }
}
