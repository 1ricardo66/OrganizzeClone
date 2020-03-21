package br.com.ricardofelix.organizzeclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.ricardofelix.organizzeclone.Helper.DateCustom;
import br.com.ricardofelix.organizzeclone.R;

public class DespesaActivity extends AppCompatActivity {
     private EditText textDate, textDescricao,textCategoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        textDate = findViewById(R.id.editDate);
        textDescricao = findViewById(R.id.editDescricao);
        textCategoria = findViewById(R.id.editCategoria);


        FloatingActionButton fab = findViewById(R.id.okBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DespesaActivity.this, "FAB SAY HELLO BUDDY!!", Toast.LENGTH_SHORT).show();
            }
        });


        textDate.setHint("Ex: "+ DateCustom.getDate());

        if(textDate != null){
            Toast.makeText(this, "Different null", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "IT's NULL", Toast.LENGTH_SHORT).show();
        }

    }
}
