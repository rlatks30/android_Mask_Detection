package com.inhatc.maskdetection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class DevinfoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txt_addr1, txt_addr2, txt_name1, txt_name2;
    private Intent intent;
    private Button btn_out;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private FirebaseUser fireUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devinfo);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);


        txt_addr1=(TextView)findViewById(R.id.txt_addr1);
        txt_addr1.setOnClickListener(this);
        txt_addr2=(TextView)findViewById(R.id.txt_addr2);
        txt_addr2.setOnClickListener(this);
        txt_name1=(TextView)findViewById(R.id.txt_name1);
        txt_name2=(TextView)findViewById(R.id.txt_name2);


    }


    @Override
    public void onClick(View v) {
        intent = new Intent(DevinfoActivity.this, MapsActivity.class);
        if(v==txt_addr1){
            intent.putExtra("위도", 37.448344);
            intent.putExtra("경도", 126.657474);
            intent.putExtra("이름", txt_name1.getText().toString());
            intent.putExtra("주소", txt_addr1.getText().toString());
        }else if(v==txt_addr2){
            intent.putExtra("위도", 37.448418332701685);
            intent.putExtra("경도", 126.66081025633731);
            intent.putExtra("이름", txt_name2.getText().toString());
            intent.putExtra("주소", txt_addr2.getText().toString());
        }
        startActivity(intent);
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
                intent = new Intent(DevinfoActivity.this, LoginActivity.class);
                Toast.makeText(DevinfoActivity.this,"로그아웃",Toast.LENGTH_SHORT).show();
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
                intent = new Intent(DevinfoActivity.this, LoginActivity.class);
                mDatabaseRef.child("UserAccount").child(fireUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DevinfoActivity.this,"회원 탈퇴 성공",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DevinfoActivity.this,"회원 탈퇴 실패",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.devinfo:
                intent = new Intent(DevinfoActivity.this, DevinfoActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
