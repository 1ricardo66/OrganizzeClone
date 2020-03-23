package br.com.ricardofelix.organizzeclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import br.com.ricardofelix.organizzeclone.Helper.DateCustom;
import br.com.ricardofelix.organizzeclone.R;
import br.com.ricardofelix.organizzeclone.model.UserMovementation;

public class DespesaActivity extends AppCompatActivity {
     private EditText textDate, textDescription,textCategory,textValue;
     private UserMovementation userMovementation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);
        textValue = findViewById(R.id.textValue);
        textDate = findViewById(R.id.editDate);
        textDescription = findViewById(R.id.editDescricao);
        textCategory = findViewById(R.id.editCategoria);




        textDate.setHint("Ex: "+ DateCustom.getDate());

        if(textDate != null){
            Toast.makeText(this, "Different null", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "IT's NULL", Toast.LENGTH_SHORT).show();
        }


        FloatingActionButton fab = findViewById(R.id.okBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExpenses();
                Toast.makeText(DespesaActivity.this, "Despesa salva com sucesso!!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void saveExpenses(){

        userMovementation = new UserMovementation();
        Double d = Double.parseDouble(textValue.getText().toString());

        userMovementation.setValue(d);

        userMovementation.setCategory(textCategory.getText().toString());

        userMovementation.setDescription(textDescription.getText().toString());

        userMovementation.setDate(textDate.getText().toString());

        userMovementation.setType("d");

        userMovementation.saveMovementation(textDate.getText().toString());


    }
}
