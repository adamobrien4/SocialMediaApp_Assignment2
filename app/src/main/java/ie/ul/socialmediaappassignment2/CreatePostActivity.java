package ie.ul.socialmediaappassignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.*;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
    }


    //Using button to send Post
    public void sendPost(View view)
    {
        //connecting to firebase and path
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference postDB = db.collection("posts");

        String message = ((EditText)findViewById(R.id.create_post_txt)).getText().toString();

        //Adding to the database
        Map<String, Object> post = new HashMap<>();
        post.put("username", "chob");
        post.put("message", message);
        post.put("timestamp", System.currentTimeMillis());
        postDB.document()
                .set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Intent intent = new Intent();
                        // startActivity(intent);
                    }
                });
    }

}


