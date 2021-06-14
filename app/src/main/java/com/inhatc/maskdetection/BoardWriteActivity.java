package com.inhatc.maskdetection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_title,edt_content;
    private Button btn_write;
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private FirebaseAuth firebaseAuth; //파이어베이스 인증
    private FirebaseUser fireUser;
    private Board board;
    private long mNow;
    private Date mDate;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        edt_title=(EditText)findViewById(R.id.edt_title);
        edt_content=(EditText)findViewById(R.id.edt_content);
        btn_write=(Button)findViewById(R.id.btn_write);
        btn_write.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("MaskDetection");
    }

    @Override
    public void onClick(View v) {
        if(v==btn_write){
            Intent intent = getIntent();
            fireUser = firebaseAuth.getCurrentUser();
            board= new Board(fireUser.getEmail(),edt_title.getText().toString(),edt_content.getText().toString(), getTime());
            if((intent.getStringExtra("Type")).equals("Board")){
                mDatabaseRef.child("Board").child(intent.getStringExtra("idx")).setValue(board);
            }else {
                mDatabaseRef.child("NoticeBoard").child(intent.getStringExtra("idx")).setValue(board);
            }
            Intent resultIntent = new Intent();
            setResult(RESULT_OK,resultIntent);
            finish();

        }
    }
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}
