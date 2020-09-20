package agarwal.shashwat.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verifyPhone extends AppCompatActivity {

    private String verificationCodeBySystem;
    private PinView pin;
    Button continueButton;
    private ProgressBar progressBar;
    private static final String TAG = "verifyPhone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra("phoneNo");
        TextView otpsent = findViewById(R.id.otpSentNo);
        continueButton = findViewById(R.id.continueButton);
        pin = findViewById(R.id.firstPinView);
        progressBar=findViewById(R.id.progressBar);
        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(verifyPhone.this, "Back button pressed", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        startPhoneNumberVerification(phoneNumber);
        otpsent.setText("OTP has been sent to " + phoneNumber);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codeEnteredByUser=pin.getText().toString().trim();
                if (codeEnteredByUser.isEmpty() || codeEnteredByUser.length()<6){
                    pin.setError("Enter code..");
                    pin.requestFocus();
                    return;
                }
                verifyCode(codeEnteredByUser);
                Toast.makeText(verifyPhone.this, "Continue...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startPhoneNumberVerification(String phoneNumber) {

        progressBar.setVisibility(View.VISIBLE);

        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 phoneNumber,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        //mVerificationInProgress = true;
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
               pin.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(verifyPhone.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onVerificationFailed: " + e.getMessage());
        }
    };

    private void verifyCode(String codeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(verifyPhone.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(verifyPhone.this, "Success", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);



                        } else {
                            Toast.makeText(verifyPhone.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Log.i(TAG, "onComplete: " + task.getException().getMessage());
                        }
                    }
                });
    }
}
