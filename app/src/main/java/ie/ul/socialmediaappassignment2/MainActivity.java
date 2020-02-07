package ie.ul.socialmediaappassignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSignIn (View view){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()
        );
        startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(), RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                System.out.println("Sign in successful!\n"+
                        "name = " + user.getDisplayName()+"\n"+
                        "email = " + user.getEmail() + "\n" +
                        "id = "+ user.getUid());
            } else{
                if(response == null){
                    System.out.println("Sign in cancelled"); return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK){
                    System.out.println("No internet Connection"); return;
                }
            }
        }
    }

    public void getData(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        CollectionReference colRef = db.collection("test-collection");

        Query query = colRef.orderBy("timestamp", Query.Direction.DESCENDING).limit(2);

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        System.out.println("OnCompleteListerner");
                        if (task.isSuccessful()) {
                            System.out.println("Task ResultSet size : " + task.getResult().size());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("tag", "Error getting documents", task.getException());
                        }
                    }
                });
    }

    public void storeData(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference drinks = db.collection("test-collection");

        Map<String, Object> drink1 = new HashMap<>();
        drink1.put("name", "Adam");
        drink1.put("message", "This is my message");
        drink1.put("timestamp", System.currentTimeMillis());
        drinks.document("message2").set(drink1);
    }
}
