package com.example.kartikmishra.instagramc.utils;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.kartikmishra.instagramc.R;
import com.example.kartikmishra.instagramc.models.User;
import com.example.kartikmishra.instagramc.models.UserSettings;
import com.example.kartikmishra.instagramc.models.User_account_settings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;
    private Context mContext;

    public FirebaseMethods(Context context) {
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        if (mAuth.getCurrentUser() != null) {

            userID = mAuth.getCurrentUser().getUid();
        }

    }

    public boolean checkIfUsernameAlreadyExists(String username, DataSnapshot dataSnapshot) {
        Log.d(TAG, "checkIfUsernameAlreadyExists: checking if" + username + "already exists.");
        User user = new User();

        for (DataSnapshot ds : dataSnapshot.child(userID).getChildren()) {
            Log.d(TAG, "checkIfUsernameAlreadyExists: datasnapshot: " + ds);
            user.setUsername(ds.getValue(User.class).getUsername());
            Log.d(TAG, "checkIfUsernameAlreadyExists: username: " + user.getUsername());
            if (StringManipulations.expandUsername(user.getUsername()).equals(username)) {

                Log.d(TAG, "checkIfUsernameAlreadyExists: Found A Match: " + user.getUsername());
                return true;
            }

        }
        return false;

    }

    public void registerNewEmail(String email, String username, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "onComplete: " + task.isSuccessful());

                        if (!task.isSuccessful()) {

                            Toast.makeText(mContext, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                        } else if (task.isSuccessful()) {

                            sendVerificationEmail();

                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: Authstate changed" + userID);
                        }

                        // ...
                    }
                });

    }

    public void sendVerificationEmail() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {

                                Toast.makeText(mContext, "Couldn't send verification email.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }


    public void addNewUser(String email, String username, String description, String website, String profile_photo) {

        User user = new User(email, 1, userID,StringManipulations.condenseUsername(username));
        myRef.child(mContext.getString(R.string.dbName_user))
                .child(userID)
                .setValue(user);

        User_account_settings settings = new User_account_settings(description,
                 username,
                0,
                0,
                0,
                profile_photo,
                StringManipulations.condenseUsername(username),
                website
        );

        myRef.child(mContext.getString(R.string.dbName_user_account_setting))
                .child(userID)
                .setValue(settings);

    }

    /**
     * retrieve the user account settings for the user currently logged in
     * Database: user_account _settings node
     *
     * @param dataSnapshot
     * @return
     */


    public UserSettings getUserSettings(DataSnapshot dataSnapshot) {

        Log.d(TAG, "getUserSettings: retrieve user account settings from firebase");

        User_account_settings settings = new User_account_settings();
        User user = new User();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            //User account settings node

            if (ds.getKey().equals(mContext.getString(R.string.dbName_user_account_setting))) {

                Log.d(TAG, "getUserSettings: datasnapshot" + ds);

                try {

                    settings.setDisplay_name(
                            ds.child(userID)
                                    .getValue(User_account_settings.class)
                                    .getDisplay_name()
                    );
                    settings.setUsername(
                            ds.child(userID)
                                    .getValue(User_account_settings.class)
                                    .getUsername()
                    );
                    settings.setWebsite(
                            ds.child(userID)
                                    .getValue(User_account_settings.class)
                                    .getWebsite()
                    );
                    settings.setDescription(
                            ds.child(userID)
                                    .getValue(User_account_settings.class)
                                    .getDescription()
                    );
                    settings.setFollowers(
                            ds.child(userID)
                                    .getValue(User_account_settings.class)
                                    .getFollowers()
                    );
                    settings.setFollowing(
                            ds.child(userID)
                                    .getValue(User_account_settings.class)
                                    .getFollowing()
                    );
                    settings.setPosts(
                            ds.child(userID)
                                    .getValue(User_account_settings.class)
                                    .getPosts()
                    );
                    settings.setProfile_photo(
                            ds.child(userID)
                                    .getValue(User_account_settings.class)
                                    .getProfile_photo()
                    );

                    Log.d(TAG, "getUserSettings: retrieved user_account_settings " + settings.toString());
                } catch (NullPointerException e) {

                    Log.d(TAG, "getUserSettings: Null pointer Exception" + e.getMessage());
                }

                //Users node
                if (ds.getKey().equals(mContext.getString(R.string.dbName_user))) {

                    Log.d(TAG, "getUserSettings: datasnapshot" + ds);


                    user.setUsername(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUsername()
                    );

                    user.setEmail(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getEmail()
                    );

                    user.setPhone_number(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getPhone_number()
                    );

                    user.setUser_id(
                            ds.child(userID)
                                    .getValue(User.class)
                                    .getUser_id()
                    );

                    Log.d(TAG, "getSettings: retrieved user " + user.toString());


                }
            }

        }
        return new UserSettings(user,settings);
    }

}
