package android.example.furniture_magik_demo.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.example.furniture_magik_demo.MainActivity;
import android.example.furniture_magik_demo.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {


    EditText editText;
    Button submit;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViews();
        mAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otpVerification();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
    private void setViews() {
        editText = findViewById(R.id.activity_login_editText);
        submit = findViewById(R.id.activity_login_button);

    }

    void otpVerification()
    {
        if(editText.getText().toString().length()!=0)
        {
            Intent intent = new Intent(LoginActivity.this,OtpVerification.class);
            intent.putExtra("mobile",editText.getText().toString());
            startActivity(intent);
        }else
            editText.setError("Please provide a valid mobile no");
    }
}
