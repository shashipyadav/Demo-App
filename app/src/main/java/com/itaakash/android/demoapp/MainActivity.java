package com.itaakash.android.demoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.itaakash.android.demoapp.adapter.Draweradapter;
import com.itaakash.android.demoapp.model.MenuModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    List<MenuModel> headerList = new ArrayList<>();
    private MenuModel home,Language,Logout;
    public String hometxt = "Home";
    public String langigaext = "Change Language";
    public String logouttxt = "Logout";
    private static SharedPreferences sharedPreferences;
    private static final String Locale_Preference = "Locale Preference";
    private static final String Locale_KeyValue = "Saved Locale";
    private static Locale myLocale;
    private static SharedPreferences.Editor editor;
    private TextView tv_todo;
    private TextView tv_enc;
    private TextView tv_sw;
    private TextView cl;
    public GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private TextView tv_user_Name,textView;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        prepareMenuData();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerview = navigationView.getHeaderView(0);

        tv_user_Name = headerview.findViewById(R.id.tv_user_Name);
        textView = headerview.findViewById(R.id.textView);
        imageView = headerview.findViewById(R.id.imageView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        sharedPreferences = getSharedPreferences(Locale_Preference, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadLocale();

        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

    }

    private void loadLocale() {
        String language = sharedPreferences.getString(Locale_KeyValue, "");
        changeLocale(language);
    }

    private void changeLocale(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);//Set Selected Locale
        saveLocale(lang);//Save the selected locale
        Locale.setDefault(myLocale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = myLocale;//set config locale as selected locale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());//Update the config
        updateTexts();//Update texts according to locale
    }

    private void updateTexts() {
        try {
            if (home != null) {

                home.menuName = getResources().getString(R.string.home);
            } if (Language != null) {

                Language.menuName = getResources().getString(R.string.Language);
            } if (Logout != null) {

                Logout.menuName = getResources().getString(R.string.logout);
            }
            tv_todo.setText(getResources().getString(R.string.todo));
            tv_enc.setText(getResources().getString(R.string.Encryption));
            tv_sw.setText(getResources().getString(R.string.Stopwatch));
            cl.setText(getResources().getString(R.string.Language));
            prepareMenuData();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void saveLocale(String lang) {
        editor.putString(Locale_KeyValue, lang);
        editor.commit();
    }
    private void init() {

        tv_todo = findViewById(R.id.tv_todo);
        tv_enc = findViewById(R.id.tv_enc);
        tv_sw = findViewById(R.id.tv_sw);
        cl = findViewById(R.id.cl);

        LinearLayout ll_todo =  findViewById(R.id.ll_todo);
        LinearLayout ll_enc =  findViewById(R.id.ll_enc);
        LinearLayout ll_stop_watch =  findViewById(R.id.ll_stop_watch);
        LinearLayout ll_ch_langigae =  findViewById(R.id.ll_ch_langigae);
        ll_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,TodotaskActivity.class);
                startActivity(i);
            }
        });
        ll_enc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,EncryptionDecryptionActivity.class);
                startActivity(i);
            }
        }); ll_stop_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,StropWatchActivity.class);
                startActivity(i);
            }
        }); ll_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,TodotaskActivity.class);
                startActivity(i);
            }
        });
        ll_ch_langigae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LanguageActivity.class);
                startActivityForResult(i,121);

            }
        });
    }

    private void prepareMenuData() {
        headerList.clear();

         home = new MenuModel(getResources().getString(R.string.home), 0);
        headerList.add(home);


         Language = new MenuModel(getResources().getString(R.string.Language), 0);
        headerList.add(Language);

         Logout = new MenuModel(getResources().getString(R.string.logout), 0);
        headerList.add(Logout);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        RecyclerView list = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        Draweradapter recyclerAdapter = new Draweradapter(MainActivity.this, headerList, drawer);
        list.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 121:
                loadLocale();
                break;

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadLocale();

    }
    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            Log.d("userName",account.getDisplayName());
            /*userName.setText(account.getDisplayName());
            userEmail.setText(account.getEmail());
            userId.setText(account.getId());*/
            tv_user_Name.setText(account.getDisplayName());
            textView.setText(account.getEmail());
            try{
                Glide.with(this).load(account.getPhotoUrl()).into(imageView);
            }catch (NullPointerException e){
                Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }


        }else{
            gotoMainActivity();
        }
    }

    public void gotoMainActivity() {
        SharedPreferences settings = getSharedPreferences("Locale Preference", Context.MODE_PRIVATE);
        settings.edit().clear().commit();

        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}