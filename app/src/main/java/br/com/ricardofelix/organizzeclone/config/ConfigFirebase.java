package br.com.ricardofelix.organizzeclone.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class ConfigFirebase {

     private static FirebaseAuth auth;
     private static DatabaseReference fireRef;

     public static DatabaseReference getFirebaseDatabase(){
         if(fireRef == null){
             fireRef = FirebaseDatabase.getInstance().getReference();
         }

         return fireRef;
     }



     public static FirebaseAuth getAuth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }
}
