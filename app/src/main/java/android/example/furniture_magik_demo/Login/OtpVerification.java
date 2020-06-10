package android.example.furniture_magik_demo.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.app.ProgressDialog;
import android.content.Intent;

import android.example.furniture_magik_demo.MainActivity;
import android.example.furniture_magik_demo.R;
import android.example.furniture_magik_demo.utils.SharedPref_Util;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


import static androidx.constraintlayout.widget.Constraints.TAG;

public class OtpVerification extends AppCompatActivity {


    private static final String TAG = "OtpVerification";
    private TextView textView,count;
    private Button button;
    private EditText otptext;
    private ProgressDialog progressDialog;
    private boolean Authsuccess;
    private String mobile;
    private String mVerificationId;
    private boolean submitToken;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        toolbar = findViewById(R.id.otp_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setViews();
        mobile = getIntent().getStringExtra("mobile");
        Log.d(TAG, "onCreate: "+mobile);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        textView.setText("+91 "+mobile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitVerification();
            }
        });

        /** Callback for Phone Auth */
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                Toast.makeText(OtpVerification.this, "Got It, Verifying", Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progressBarUnset();
                Toast.makeText(OtpVerification.this, "Failed to get", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(OtpVerification.this, "Too Many Req", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                progressBarUnset();
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                Toast.makeText(OtpVerification.this, "Got It, Verifying", Toast.LENGTH_SHORT).show();
                submitToken = true;
            }
        };

        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              verifyPhoneNumber("+91"+mobile);
            }
        });

    }

    private void setViews() {
        otptext = findViewById(R.id.activity_otp_OTP);
        button = findViewById(R.id.activity_otp_button);
        textView = findViewById(R.id.activity_otp_mobile);
        count = findViewById(R.id.count);

    }
    /** Verify Phone Number init method */
    public void verifyPhoneNumber(String phoneNumber)
    {
        countDown();
        progressBarSet("Trying to auto detect");

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }


    @Override
    protected void onStart() {
        super.onStart();
        verifyPhoneNumber("+91"+mobile);
    }

    /** If User submit otp */
    void submitVerification()
    {
        if(!otptext.getText().toString().isEmpty()&&submitToken) {
            progressBarSet("Verifying");
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otptext.getText().toString());
            signInWithPhoneAuthCredential(credential);

        }else{
            otptext.setError("Please enter the otp.");
            submitToken=false;
        }
    }


    /** Sign IN */
    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(OtpVerification.this, "Welcome", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();
                            UserVerified();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                            progressBarUnset();
                            Toast.makeText(OtpVerification.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    /** Navigate to main screen */
    public void UserVerified()
    {
        progressBarUnset();
        SharedPref_Util.setString(this,"UID","9918385660");
        SharedPref_Util.setBoolean(this,"USER_PRESENT",true);
        Intent intent = new Intent(OtpVerification.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    void progressBarSet(String message)
    {
        progressDialog.setTitle("OTP verification");
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void progressBarUnset()
    {
        progressDialog.dismiss();
    }

    /** Count Down timer 60 sec */
    public void countDown()
    {
        count.setVisibility(View.VISIBLE);
        if(count.getText().toString().equals("Retry!")) {

            CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    count.setText(String.format(Locale.getDefault(), "%d sec.", millisUntilFinished / 1000L));
                }

                public void onFinish() {
                    count.setText("Retry!");
                }
            }.start();
        }
    }

}
