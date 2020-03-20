package br.com.ricardofelix.organizzeclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import br.com.ricardofelix.organizzeclone.R;

public class DespesaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        FloatingActionButton fab = findViewById(R.id.okBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DespesaActivity.this, "FAB SAY HELLO BUDDY!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
