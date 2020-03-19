package br.com.ricardofelix.organizzeclone.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfigFirebase {

     private static FirebaseAuth auth;

     public static FirebaseAuth getAuth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }
}
