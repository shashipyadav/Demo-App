package com.itaakash.android.demoapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.itaakash.android.demoapp.LanguageActivity;
import com.itaakash.android.demoapp.MainActivity;
import com.itaakash.android.demoapp.R;
import com.itaakash.android.demoapp.TodotaskActivity;
import com.itaakash.android.demoapp.model.MenuModel;
import com.itaakash.android.demoapp.model.TodotaskModel;

import java.util.List;


public class Draweradapter extends RecyclerView.Adapter<Draweradapter.myViewHolder> {
    private Activity context;
    List<MenuModel> mData;
    private DrawerLayout drawerLayout;


    public Draweradapter(Activity context, List<MenuModel> data, DrawerLayout drawerLayout) {
        this.context = context;
        this.mData = data;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public Draweradapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_drawer, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Draweradapter.myViewHolder holder, final int position) {
        holder.nav.setText(mData.get(position).menuName.toUpperCase());
       // holder.iv_menu_icon.setImageResource(mData.get(position).icon);


        holder.nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*fi ("Leaves Claimed".equalsIgnoreCase(mData.get(position).menuName)) {
                   *//* Intent intent = new Intent(context, LeavesClaimedActivity.class);
                    context.startActivity(intent);*//*

                } else*/ if ("Change Language".equalsIgnoreCase(mData.get(position).menuName)||
                        "إلغاء".equalsIgnoreCase(mData.get(position).menuName)) {
                   /* Intent intent = new Intent(context, LeavesApprovalActivity.class);
                    context.startActivity(intent);*/
                    Intent i = new Intent(context, LanguageActivity.class);
                    context.startActivityForResult(i,121);

                } else if ("Logout".equalsIgnoreCase(mData.get(position).menuName)||
                        "تسجيل خروج".equalsIgnoreCase(mData.get(position).menuName)) {
                    new AlertDialog.Builder(context)
                            .setMessage(R.string.alert)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    FirebaseAuth.getInstance().signOut();
                                    Auth.GoogleSignInApi.signOut(((MainActivity)context).googleApiClient).setResultCallback(
                                            new ResultCallback<Status>() {
                                                @Override
                                                public void onResult(Status status) {
                                                    if (status.isSuccess()){
                                                        ((MainActivity)context).gotoMainActivity();
                                                    }else{
                                                        Toast.makeText(context,"Session not close",Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });




                                }})
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();


                }
                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView nav;
        private ImageView iv_menu_icon;

        public myViewHolder(View itemView) {
            super(itemView);
            nav = (TextView) itemView.findViewById(R.id.lblListHeader);
            //iv_menu_icon = (ImageView) itemView.findViewById(R.id.iv_menu_icon);

        }
    }
}

