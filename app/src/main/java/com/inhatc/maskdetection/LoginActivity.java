package com.inhatc.maskdetection;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends Activity implements View.OnClickListener {
    private Button btn_login, btn_register;
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private User user;
    private FirebaseAuth firebaseAuth; //파이어베이스 인증
    private FirebaseUser fireUser;
    private EditText edt_email, edt_pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_email = (EditText)findViewById(R.id.edt_email);
        edt_pw = (EditText)findViewById(R.id.edt_pw);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("MaskDetection");

        btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v==btn_register){
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        }else if(v==btn_login){
            String strEmail = edt_email.getText().toString();
            String strPw = edt_pw.getText().toString();

            firebaseAuth.signInWithEmailAndPassword(strEmail, strPw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        // 로그인 성공
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
