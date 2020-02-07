package ie.ul.socialmediaappassignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void openPostsActivity(View view) {
        Intent intent = new Intent(this, PostFeedActivity.class);
        startActivity(intent);
    }
}
