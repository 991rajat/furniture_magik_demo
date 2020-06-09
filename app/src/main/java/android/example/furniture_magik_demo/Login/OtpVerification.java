package android.example.furniture_magik_demo.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;

import android.example.furniture_magik_demo.MainActivity;
import android.example.furniture_magik_demo.R;
import android.example.furniture_magik_demo.utils.SharedPref_Util;
import android.os.Bundle;
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

import java.util.concurrent.TimeUnit;


import static androidx.constraintlayout.widget.Constraints.TAG;

public class OtpVerification extends AppCompatActivity {


    private static final String TAG = "OtpVerification";
    TextView textView;
    Button button;
    EditText otptext;
    ProgressDialog progressDialog;
    private boolean Authsuccess;
    private String mobile;
    private String mVerificationId;
    private boolean submitToken;
    FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
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


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                progressBarUnset();
                Toast.makeText(OtpVerification.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                progressBarUnset();
                Log.d(TAG, "onCodeSent:" + verificationId);

                Toast.makeText(OtpVerification.this, "Enter the OTP", Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                submitToken = true;
                //mResendToken = token;


                // ...
            }
        };

    }

    private void setViews() {
        otptext = findViewById(R.id.activity_otp_OTP);
        button = findViewById(R.id.activity_otp_button);
        textView = findViewById(R.id.activity_otp_mobile);

    }
    public void verifyPhoneNumber(String phoneNumber)
    {
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



    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            progressBarUnset();
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

    public void UserVerified()
    {
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

}
