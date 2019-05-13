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

import com.escom.topsecret.Entities.User;
import com.escom.topsecret.Utils.SessionManagement;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onResume() {
        super.onResume();
        checkAccount();
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

    @OnClick(R.id.btn_login)
    public void login() {


        if (!validate()) {
            return;
        }

        if (serverValidate()) {
            if (user == null) {
                user = new User();
                user.setEmail(emailText);
                sessionManagement.setUserData(gson.toJson(user));
            }

            startActivity(new Intent(getApplicationContext(), SessionActivity.class));
        }
    }

    private boolean serverValidate() {
        return true;
    }

    @OnTextChanged({R.id.et_email, R.id.et_password})
    public void textChanged(CharSequence charSequence) {

        String text = String.valueOf(charSequence);
        emailText = this.email.getText().toString();
        passwordText = this.password.getText().toString();

        if (String.valueOf(charSequence).hashCode() == emailText.hashCode()) {
            tilEmail.setError(null);
        } else if (String.valueOf(charSequence).hashCode() == passwordText.hashCode()) {
            tilPassword.setError(null);
        }
    }


    private boolean validate() {
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

        if (userData.equals("")) {
            ;
        } else {
            user = gson.fromJson(userData, User.class);
            email.setText(user.getEmail());
            fingerPrintDialogPrompt(user.getEmail()); //Execute dialogPrompt
        }
    }


    /**
     * Creates the fingerprint dialog
     *
     * @param subtitle
     * Shows the subtitle as the user to log in.
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void fingerPrintDialogPrompt(String subtitle) {

        Log.d(TAG, "fingerPrintDialogPrompt: no sirve");
        Executor newExecutor = Executors.newSingleThreadExecutor();
        BiometricCallback biometricCallback = new BiometricCallback();

        final BiometricPrompt myBiometricPrompt = new BiometricPrompt(this, newExecutor, biometricCallback);

        if(promptInfo == null){
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
        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
        }

        @Override
        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            startActivity(new Intent(getApplicationContext(), SessionActivity.class));
        }


        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
        }
    }
}
