package ie.ul.socialmediaappassignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

        getPosts();
    }

    public void gotoCreatePosts(View view){
        Intent intent = new Intent(this, CreatePostActivity.class);
        startActivity(intent);
    }

    public void getPosts() {
        // Get reference to FirebaseFirestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Firebase settings recommended by build console
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        // Query firestore for the 5 most recently added posts/documents
        Query query = db.collection("posts").orderBy("timestamp", Query.Direction.DESCENDING).limit(5);

        // Execute firestore query
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Loop through each document from query resultset
                            int postIndex = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TextView username = null;
                                TextView message = null;
                                TextView time = null;

                                // Keep Track of which post we are currently iterating
                                postIndex++;

                                // Point to correct screen elements for this post
                                switch (postIndex){
                                    case 1:
                                        username = findViewById(R.id.username1);
                                        message = findViewById(R.id.message1);
                                        time = findViewById(R.id.time1);
                                        break;
                                    case 2:
                                        username = findViewById(R.id.username2);
                                        message = findViewById(R.id.message2);
                                        time = findViewById(R.id.time2);
                                        break;
                                    case 3:
                                        username = findViewById(R.id.username3);
                                        message = findViewById(R.id.message3);
                                        time = findViewById(R.id.time3);
                                        break;
                                    case 4:
                                        username = findViewById(R.id.username4);
                                        message = findViewById(R.id.message4);
                                        time = findViewById(R.id.time4);
                                        break;
                                    case 5:
                                        username = findViewById(R.id.username5);
                                        message = findViewById(R.id.message5);
                                        time = findViewById(R.id.time5);
                                        break;
                                }

                                // Get amount of seconds that has passed between the post being created and now
                                long diffSeconds = (System.currentTimeMillis() - (long)document.getData().get("timestamp")) / 1000;
                                String diffString;

                                // Format time string depending on when the post was created
                                if(diffSeconds < 60) {
                                    diffString = diffSeconds + " seconds ago";
                                } else if(diffSeconds < 120 ) {
                                    diffString = "1 minute ago";
                                } else if(diffSeconds < 3600) {
                                    diffString = diffSeconds/60 + " minutes ago";
                                } else {
                                    diffString = diffSeconds/3600 + " hours ago";
                                }

                                // Populate each of this posts designated fields
                                username.setText((String)document.getData().get("username"));
                                message.setText((String)document.getData().get("message"));
                                time.setText(diffString);

                            }
                        } else {
                            // Error logging
                            Log.w("tag", "Error getting documents", task.getException());
                        }
                    }
                });
    }

    public void reloadPosts(View view) {
        getPosts();
    }
}
