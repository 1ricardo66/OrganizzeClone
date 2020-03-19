package br.com.ricardofelix.organizzeclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.ricardofelix.organizzeclone.R;
import br.com.ricardofelix.organizzeclone.config.ConfigFirebase;
import br.com.ricardofelix.organizzeclone.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button btnLogin;
    TextView textEmailLogin, textPasswordLogin;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        textEmailLogin = findViewById(R.id.editEmailLogin);
        textPasswordLogin = findViewById(R.id.editPasswordLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeLogin();
            }
        });


    }

    public void makeLogin(){
        auth = ConfigFirebase.getAuth();
        
        if(dataValidation()){
            auth.signInWithEmailAndPassword(usuario.getEmail(),usuario.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Email ou senha inválidos.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    
    
    public boolean dataValidation (){
        String email,pwd;
        email =textEmailLogin.getText().toString();
        pwd = textPasswordLogin.getText().toString();

        if(email.isEmpty() && pwd.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            usuario = new Usuario();
            usuario.setEmail(textEmailLogin.getText().toString());
            usuario.setPassword(textPasswordLogin.getText().toString());
            return true;
        }
    }

}
