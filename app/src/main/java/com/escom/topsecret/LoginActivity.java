package com.escom.topsecret;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import com.escom.topsecret.entities.User;
import com.escom.topsecret.utils.SessionManagement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private BiometricPrompt.PromptInfo promptInfo;

    private SessionManagement sessionManagement;
    private User user;
    private Gson gson;

    private String emailText;
    private String passwordText;

    private FirebaseAuth mAuth;

    @BindView(R.id.et_email)
    TextInputEditText email;
    @BindView(R.id.et_password)
    TextInputEditText password;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.til_pw)
    TextInputLayout tilPassword;

    @BindString(R.string.hint_email)
    String hEmail;
    @BindString(R.string.hint_password)
    String hPassword;

    @BindString(R.string.fingerTitle)
    String fingerprintDialogTitle;
    @BindString(R.string.fingerDescription)
    String fingerprintDialogDescription;
    @BindString(R.string.fingerButton)
    String fingerprintDialogNegativeText;

    @BindString(R.string.errorMail)
    String errorMail;
    @BindString(R.string.errorPassword)
    String errorPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        sessionManagement = new SessionManagement(getApplicationContext());
        gson = new Gson();
        mAuth = FirebaseAuth.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onResume() {
        super.onResume();
        checkAccount();
    }

    @OnClick(R.id.btn_login)
    public void login() {


        if (!validateFields()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(task -> {
            if (user == null) {
                user = new User();
                user.setEmail(emailText);
                sessionManagement.setUserData(gson.toJson(user));
            }

            startActivity(new Intent(getApplicationContext(), SessionActivity.class));
        });
    }

    private boolean validateFields() {
        boolean valid = true;

        Log.d(TAG, String.format("Email: %s, Password: %s", email, password));

        if (emailText.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            this.tilEmail.setError(errorMail);
            valid = false;
        } else {
            this.tilEmail.setError(null);
        }

        if (passwordText.isEmpty()) {
            this.tilPassword.setError(errorPassword);
        }

        return valid;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void checkAccount() {
        String userData = sessionManagement.getUserData();

        if (!userData.equals("")) {
            user = gson.fromJson(userData, User.class);
            email.setText(user.getEmail());
            fingerPrintDialogPrompt(user.getEmail()); //Execute dialogPrompt
        }
    }


    @OnTextChanged({R.id.et_email, R.id.et_password})
    public void textChanged(CharSequence charSequence) {

        emailText = this.email.getText().toString();
        passwordText = this.password.getText().toString();

        if (String.valueOf(charSequence).hashCode() == emailText.hashCode()) {
            tilEmail.setError(null);
        } else if (String.valueOf(charSequence).hashCode() == passwordText.hashCode()) {
            tilPassword.setError(null);
        }
    }

    @OnFocusChange({R.id.et_email, R.id.et_password})
    public void placeHolderTrick(boolean focus, TextInputEditText textInputEditText) {
        if (focus) {
            if (textInputEditText == password) {
                textInputEditText.setHint(hPassword);
            } else {
                textInputEditText.setHint(hEmail);
            }
        } else {
            textInputEditText.setHint("");
        }
    }


    /**
     * Creates the fingerprint dialog
     *
     * @param subtitle Shows the subtitle as the user to log in.
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void fingerPrintDialogPrompt(String subtitle) {

        Log.d(TAG, "fingerPrintDialogPrompt: no sirve");
        Executor newExecutor = Executors.newSingleThreadExecutor();
        BiometricCallback biometricCallback = new BiometricCallback();

        final BiometricPrompt myBiometricPrompt = new BiometricPrompt(this, newExecutor, biometricCallback);

        if (promptInfo == null) {
            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle(fingerprintDialogTitle)
                    .setSubtitle(subtitle)
                    .setDescription(fingerprintDialogDescription)
                    .setNegativeButtonText(fingerprintDialogNegativeText)
                    .build();
        }

        myBiometricPrompt.authenticate(promptInfo);
    }

    /**
     * Handles the fingerprint callbacks.
     */
    private class BiometricCallback extends BiometricPrompt.AuthenticationCallback {

        @Override
        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
            startActivity(new Intent(getApplicationContext(), SessionActivity.class));
        }
    }
}
