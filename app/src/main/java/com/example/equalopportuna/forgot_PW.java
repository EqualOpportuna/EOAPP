package com.example.equalopportuna;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class forgot_PW extends AppCompatActivity {

    //Declaration
    Button btnReset, btnBack;
    EditText edtEmail;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String strEmail;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pw);

        //Initializaton
        btnBack = findViewById(R.id.or_login);
        btnReset = findViewById(R.id.btn_next);
        edtEmail = findViewById(R.id.et_email);

        mAuth = FirebaseAuth.getInstance();





        //Reset Button Listener
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = edtEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(strEmail)) {
                    ResetPassword();
                } else {
                    edtEmail.setError("Email field can't be empty");
                }
            }
        });


        //Back Button Code
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void ResetPassword() {
        btnReset.setVisibility(View.INVISIBLE);

        mAuth.sendPasswordResetEmail(strEmail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(forgot_PW.this, "Reset Password link has been sent to your registered Email", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(forgot_PW.this, sign_up.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(forgot_PW.this, "Error :- " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        btnReset.setVisibility(View.VISIBLE);
                    }
                });
    }










    // [START auth_fui_create_launcher]
    // See: https://developer.android.com/training/basics/intents/result
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );
    // [END auth_fui_create_launcher]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_ui);
    }

    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
    // [END auth_fui_result]

    public void delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_delete]
    }

    public void themeAndLogo() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();

        // [START auth_fui_theme_logo]
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.my_great_logo)      // Set logo drawable
                .setTheme(R.style.MySuperAppTheme)      // Set theme
                .build();
        signInLauncher.launch(signInIntent);
        // [END auth_fui_theme_logo]
    }

    public void emailLink() {
        // [START auth_fui_email_link]
        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                .setAndroidPackageName(
                        /* yourPackageName= */ "...",
                        /* installIfNotAvailable= */ true,
                        /* minimumVersion= */ null)
                .setHandleCodeInApp(true) // This must be set to true
                .setUrl("https://google.com") // This URL needs to be whitelisted
                .build();

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder()
                        .enableEmailLinkSignIn()
                        .setActionCodeSettings(actionCodeSettings)
                        .build()
        );
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
        // [END auth_fui_email_link]
    }

    public void catchEmailLink() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();

        // [START auth_fui_email_link_catch]
        if (AuthUI.canHandleIntent(getIntent())) {
            if (getIntent().getExtras() == null) {
                return;
            }
            String link = getIntent().getExtras().getString("email_link_sign_in");
            if (link != null) {
                Intent signInIntent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setEmailLink(link)
                        .setAvailableProviders(providers)
                        .build();
                signInLauncher.launch(signInIntent);
            }
        }
        // [END auth_fui_email_link_catch]
    }
}