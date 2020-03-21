package br.com.ricardofelix.organizzeclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.ricardofelix.organizzeclone.R;

public class ReceitaActivity extends AppCompatActivity {
    private EditText textDate, textDescricao,textCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        textDate = findViewById(R.id.editDate);
        textDescricao = findViewById(R.id.editDescricao);
        textCategoria = findViewById(R.id.editCategoria);

        FloatingActionButton fab = findViewById(R.id.okBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "FAB SAY HELLO BUDDY!!", Toast.LENGTH_SHORT).show();
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dateNow = sdf.format(date);
        textDate.setHint("Ex: "+dateNow);

        if(textDate != null){
            Toast.makeText(this, "Different null", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "IT's NULL", Toast.LENGTH_SHORT).show();
        }
    }
}
