package com.example.myapplication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FBref {
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();
    public static DatabaseReference myRef= FBDB.getReference("message");

    public static FirebaseStorage FBST = FirebaseStorage.getInstance();
    public static StorageReference mStorageRef = FBST.getReference("images");
    public static StorageReference islandRef = mStorageRef.child("images/island.jpg");
}
