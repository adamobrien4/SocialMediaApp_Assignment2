package ie.ul.socialmediaappassignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getData(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("drinks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId());
                                System.out.println(document.getData());
                            }
                        } else {
                            Log.w("tag", "Error getting documents", task.getException());
                        }
                    }
                });
    }

    public void storeData(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference drinks = db.collection("drinks");

        Map<String, Object> drink1 = new HashMap<>();
        drink1.put("name", "Espresso");
        drink1.put("description", "It's a strong coffee!");
        drink1.put("price", 3.50);
        drinks.document("espresso").set(drink1);
    }
}
