package com.inhatc.maskdetection;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
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


public class RegisterActivity extends Activity implements View.OnClickListener {
    private Button btn_register;
    private EditText edt_email, edt_pw, edt_pwck;
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private User user;
    private FirebaseAuth firebaseAuth; //파이어베이스 인증
    private FirebaseUser fireUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edt_email = (EditText)findViewById(R.id.edt_email);
        edt_pw = (EditText)findViewById(R.id.edt_pw);
        edt_pwck = (EditText)findViewById(R.id.edt_pwck);


        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("MaskDetection");

        btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        String strEmail = edt_email.getText().toString();
        String strPw = edt_pw.getText().toString();
        String strPwCk = edt_pwck.getText().toString();
        if(strPw.equals(strPwCk)){
            // Firebase Auth 진행
            firebaseAuth.createUserWithEmailAndPassword(strEmail, strPw).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        fireUser = firebaseAuth.getCurrentUser();
                        user= new User(fireUser.getUid(),fireUser.getEmail(),strPw);
                        // setValue : database에 insert
                        mDatabaseRef.child("UserAccount").child(fireUser.getUid()).setValue(user);
                        Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this, "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        }

    }

}
