package br.com.ricardofelix.organizzeclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.ricardofelix.organizzeclone.R;
import br.com.ricardofelix.organizzeclone.config.ConfigFirebase;
import br.com.ricardofelix.organizzeclone.model.Usuario;

public class RegisterActivity extends AppCompatActivity {
    private TextView textName,textEmail,textPassword;
    private Button btnRegister;
    private FirebaseAuth auth;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textName = findViewById(R.id.editName);
        textEmail = findViewById(R.id.editEmail);
        textPassword = findViewById(R.id.editPassword);
        btnRegister = findViewById(R.id.btnRegister);
        
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });



    }

    public boolean dataValidation (){
        String email,pwd;
        email =textEmail.getText().toString();
        pwd = textPassword.getText().toString();

        if(email.isEmpty() && pwd.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
           return false;
        }else{
            usuario = new Usuario();
            usuario.setEmail(textEmail.getText().toString());
            usuario.setPassword(textPassword.getText().toString());
            return true;
        }
    }

    public void registerUser(){
        auth = ConfigFirebase.getAuth();

        

        if(dataValidation()){

            auth.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         finish();
                     }else{
                         String exception = "";
                         try {
                             throw task.getException();
                         }catch (FirebaseAuthWeakPasswordException e){
                             exception = "Digite uma senha mais forte!";
                         }catch(FirebaseAuthInvalidCredentialsException e){
                             exception = "Digite um Email válido";
                         }catch(FirebaseAuthUserCollisionException e){
                             exception = "Esta conta já foi cadastrada";
                         }

                         catch (Exception e) {
                             e.printStackTrace();
                         }

                         Toast.makeText(RegisterActivity.this, "Erro ao cadastrar usuario: "
                                 +exception, Toast.LENGTH_SHORT).show();
                     }
                }
            });

        }
    }

}
