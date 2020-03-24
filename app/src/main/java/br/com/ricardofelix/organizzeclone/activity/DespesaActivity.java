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
        


        FloatingActionButton fab = findViewById(R.id.okBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textValidation()){
                    saveExpenses();
                    Toast.makeText(DespesaActivity.this, "Despesa salva com sucesso!!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DespesaActivity.this, "Preencha todos os campos para salvar uma despesa.", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    public Boolean textValidation(){
        String value =  textValue.getText().toString();
        String date = textDate.getText().toString();
        String description = textDescription.getText().toString();
        String category = textCategory.getText().toString();

        if(value.isEmpty()){
            return false;
        }else if(date.isEmpty()){
            return false;
        }else if(description.isEmpty()){
            return false;
        }else if(category.isEmpty()){
            return false;
        }else{

            return true;
        }

    }

    public void saveExpenses(){

        userMovementation = new UserMovementation();
        Double d = Double.parseDouble(textValue.getText().toString().replace(",","."));

        userMovementation.setValue(d);

        userMovementation.setCategory(textCategory.getText().toString());

        userMovementation.setDescription(textDescription.getText().toString());

        userMovementation.setDate(textDate.getText().toString());

        userMovementation.setType("d");

        userMovementation.saveMovementation(textDate.getText().toString());

    }
}
