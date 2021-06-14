package com.inhatc.maskdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ActionBar actionBar;
    private FirebaseAuth firebaseAuth;
    private Intent intent;
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private FirebaseUser fireUser;
    private Button btn_mask,btn_calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);

        btn_mask = (Button)findViewById(R.id.btn_mask);
        btn_mask.setOnClickListener(this);
        btn_calendar = (Button)findViewById(R.id.btn_calendar);
        btn_calendar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                //select logout item
                firebaseAuth.signOut();
                intent = new Intent(MainActivity.this, LoginActivity.class);
                Toast.makeText(MainActivity.this,"로그아웃",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case android.R.id.home:
                //select back button
                finish();
                break;
            case R.id.logdel:
                firebaseAuth.getCurrentUser().delete();

                mDatabaseRef = FirebaseDatabase.getInstance().getReference("MaskDetection");
                fireUser = firebaseAuth.getCurrentUser();
                intent = new Intent(MainActivity.this, LoginActivity.class);
                mDatabaseRef.child("UserAccount").child(fireUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this,"회원 탈퇴 성공",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"회원 탈퇴 실패",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.devinfo:
                intent = new Intent(MainActivity.this, DevinfoActivity.class);
                startActivity(intent);
                break;
            case R.id.qnaboard:
                intent = new Intent(MainActivity.this, BoardActivity.class);
                startActivity(intent);
                break;
            case R.id.noticeboard:
                intent = new Intent(MainActivity.this, NoticeBoardActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v==btn_calendar){
            //intent = new
            intent = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(intent);
        }else if(v==btn_mask){

        }

    }
}