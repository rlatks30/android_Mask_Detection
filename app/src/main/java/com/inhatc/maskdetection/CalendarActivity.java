package com.inhatc.maskdetection;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CalendarActivity extends AppCompatActivity {
    public String fname=null;
    public String str=null;
    public CalendarView calendarView;
    public Button cha_Btn,del_Btn,save_Btn;
    public TextView diaryTextView,textView2,textView3;
    public EditText contextEditText;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private FirebaseAuth firebaseAuth;
    private Intent intent;
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private FirebaseUser fireUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        firebaseAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);

        calendarView=findViewById(R.id.calendarView);
        diaryTextView=findViewById(R.id.diaryTextView);
        textView3=findViewById(R.id.textView3);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                diaryTextView.setVisibility(View.VISIBLE);
                diaryTextView.setText(String.format("%d / %d / %d",year,month+1,dayOfMonth)+"\n\n\n"+
                        "데이터");
            }
        });
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
                intent = new Intent(CalendarActivity.this, LoginActivity.class);
                Toast.makeText(CalendarActivity.this,"로그아웃",Toast.LENGTH_SHORT).show();
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
                intent = new Intent(CalendarActivity.this, LoginActivity.class);
                mDatabaseRef.child("UserAccount").child(fireUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CalendarActivity.this,"회원 탈퇴 성공",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CalendarActivity.this,"회원 탈퇴 실패",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.devinfo:
                intent = new Intent(CalendarActivity.this, DevinfoActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
