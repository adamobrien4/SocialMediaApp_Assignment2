package ie.ul.socialmediaappassignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PostFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_feed);
    }

    public void getPosts(View view) {
        // Get reference to FirebaseFirestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Firebase settings recommended by build console
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        // Query firestore for the 5 most recently added posts/documents
        Query query = db.collection("test-collection").orderBy("timestamp", Query.Direction.DESCENDING).limit(5);

        // Execute firestore query
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        System.out.println("OnCompleteListener");
                        if (task.isSuccessful()) {
                            // Print amount of posts/documents found that match query
                            System.out.println("Task ResultSet size : " + task.getResult().size());
                            // Loop through each document from query resultset
                            int postIndex = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TextView username = null;
                                TextView message = null;
                                postIndex++;
                                // Print out data from each document to console
                                System.out.println(document.getId() + " => " + document.getData());
                                switch (postIndex){
                                    case 1:
                                        username = (TextView) findViewById(R.id.username1);
                                        message = (TextView) findViewById(R.id.message1);
                                        break;
                                    case 2:
                                        username = (TextView) findViewById(R.id.username2);
                                        message = (TextView) findViewById(R.id.message2);
                                        break;
                                    case 3:
                                        username = (TextView) findViewById(R.id.username3);
                                        message = (TextView) findViewById(R.id.message3);
                                        break;
                                    case 4:
                                        username = (TextView) findViewById(R.id.username4);
                                        message = (TextView) findViewById(R.id.message5);
                                        break;
                                    case 5:
                                        username = (TextView) findViewById(R.id.username5);
                                        message = (TextView) findViewById(R.id.message5);
                                        break;
                                }

                                username.setText(document.getId());
                                message.setText(document.getData().toString());
                            }
                        } else {
                            // Error logging
                            Log.w("tag", "Error getting documents", task.getException());
                        }
                    }
                });
    }
}
