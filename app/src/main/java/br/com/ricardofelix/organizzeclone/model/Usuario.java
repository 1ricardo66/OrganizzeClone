package br.com.ricardofelix.organizzeclone.model;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import br.com.ricardofelix.organizzeclone.config.ConfigFirebase;

public class Usuario {

    private String nome,email,password,idUser;
    private Double receitaTotal=0.00;
    private Double despesaTotal =0.00;

    public Double getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(Double receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public Double getDespesaTotal() {
        return despesaTotal;
    }

    public void setDespesaTotal(Double despesaTotal) {
        this.despesaTotal = despesaTotal;
    }

    public Usuario(){

    }


    public void saveUserData(){
        DatabaseReference dataRef = ConfigFirebase.getFirebaseDatabase();

        dataRef.child("usuarios")
                .child(this.idUser)
                .setValue( this );

    }





    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Exclude
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
