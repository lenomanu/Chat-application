package com.finix.midnight.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.finix.midnight.R;
import com.finix.midnight.adapters.UsersAdapter;
import com.finix.midnight.databinding.ActivityUsersBinding;
import com.finix.midnight.models.User;
import com.finix.midnight.utilities.Constants;
import com.finix.midnight.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        getUsers();
        setListeners();

    }
    public  void setListeners(){
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }
    public void showErrorMessage(){
        binding.textError.setText(String.format("%s","No user available"));
        binding.textError.setVisibility(View.VISIBLE);
    }
    public void getUsers(){
        isLoading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    isLoading(false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() != null){
                        List<User> users = new ArrayList<>();
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            if(currentUserId.equals(queryDocumentSnapshot.getId())){
                                continue;
                            }
                            User user = new User();
                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            user.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);

                            users.add(user);
                        }
                        if(users.size()>0){
                            UsersAdapter usersAdapter = new UsersAdapter(users);
                            binding.usersRecyclerView.setAdapter(usersAdapter);
                            binding.usersRecyclerView.setVisibility(View.VISIBLE);
                        }else {
                            showErrorMessage();
                        }

                    }else {
                        showErrorMessage();
                    }
                });

    }

    public void isLoading(Boolean isLoading){
        if (isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}