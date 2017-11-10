package com.bobo.normalman.themuseandme.auth;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by xiaobozhang on 11/2/17.
 */

public class MyAuth {
    public static FirebaseAuth mAuth;

    public static void init() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static boolean isSignIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public static GoogleSignInOptions buildGoogleSignInOptions(String clientID) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientID)
                .requestEmail()
                .build();
        return gso;
    }

    public static FirebaseUser getFireBaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

}
