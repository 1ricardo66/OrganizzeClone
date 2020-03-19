package br.com.ricardofelix.organizzeclone.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import br.com.ricardofelix.organizzeclone.R;
import br.com.ricardofelix.organizzeclone.config.ConfigFirebase;

public class MainActivity extends IntroActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        userLogged();

        setButtonBackVisible(false);
        setButtonNextVisible(false);
        addSlide( new SimpleSlide.Builder()
                .title("Organizze App")
                .description("Olá, seja bem vindo ao Organizze App")
                .image(R.drawable.um)
                .background(R.color.whiteColor)
                .build()
        );

        addSlide( new SimpleSlide.Builder()
                .title("Organizze App")
                .description("Gerencie suas contas com maior facilidade!!")
                .image(R.drawable.dois)
                .background(R.color.whiteColor)
                .build()
        );

        addSlide( new SimpleSlide.Builder()
                .title("Organizze App")
                .description("Facite seu trabalho")
                .image(R.drawable.tres)
                .background(R.color.whiteColor)
                .build()
        );

        addSlide( new SimpleSlide.Builder()
                .title("Organizze App")
                .description("Aproveite todas as funcionalidades do nosso App!!")
                .image(R.drawable.quatro)
                .background(R.color.whiteColor)
                .build()
        );

        addSlide(new FragmentSlide.Builder()

            .fragment(R.layout.presentation_login)
            .background(R.color.whiteColor)
            .canGoForward(false)
            .build()
        );

    }


    public void btnRegister(View v){

        startActivity(new Intent(MainActivity.this,RegisterActivity.class));

    }

    public void btnLogin(View v){
        startActivity(new Intent(MainActivity.this,LoginActivity.class));

    }

    public void userLogged(){
        auth = ConfigFirebase.getAuth();
        if (auth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this,HomeActivity.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        userLogged();
    }
}
