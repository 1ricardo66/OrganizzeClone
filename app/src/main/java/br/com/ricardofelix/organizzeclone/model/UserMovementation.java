package br.com.ricardofelix.organizzeclone.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


import br.com.ricardofelix.organizzeclone.Helper.Base64Custom;
import br.com.ricardofelix.organizzeclone.Helper.DateCustom;
import br.com.ricardofelix.organizzeclone.config.ConfigFirebase;

public class UserMovementation {
    private String date,category,description,type;
    private Double value;

    public UserMovementation() {

    }

    public void saveMovementation(String date){

        FirebaseAuth auth = ConfigFirebase.getAuth();
        String userId = auth.getCurrentUser().getEmail();
        String userId64 = Base64Custom.codeToBase64(userId);

        DatabaseReference dataRef = ConfigFirebase.getFirebaseDatabase();

        assert userId != null;
        dataRef.child("movimentacao")
                .child(userId64)
                .child(DateCustom.splitDate(date))
                .push()
                .setValue(this);

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
