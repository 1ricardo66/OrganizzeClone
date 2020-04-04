package br.com.ricardofelix.organizzeclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.ricardofelix.organizzeclone.Helper.Base64Custom;
import br.com.ricardofelix.organizzeclone.Helper.ConnectivityChecker;
import br.com.ricardofelix.organizzeclone.Helper.DateCustom;
import br.com.ricardofelix.organizzeclone.R;
import br.com.ricardofelix.organizzeclone.config.ConfigFirebase;
import br.com.ricardofelix.organizzeclone.model.UserMovementation;
import br.com.ricardofelix.organizzeclone.model.Usuario;

public class DespesaActivity extends AppCompatActivity {
     private EditText textDate, textDescription,textCategory,textValue;
     private UserMovementation userMovementation;
     private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDatabase();
     private FirebaseAuth auth = ConfigFirebase.getAuth();
     private Double totalExpenditure, recoveredValue ,updatedExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);
        textValue = findViewById(R.id.textValue);
        textDate = findViewById(R.id.editDate);
        textDescription = findViewById(R.id.editDescricao);
        textCategory = findViewById(R.id.editCategoria);
        textDate.setText(DateCustom.getDate());

        getTotalExpenditure();


        FloatingActionButton fab = findViewById(R.id.okBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textValidation()){

                    if(ConnectivityChecker.isConnected(getApplicationContext())){

                        saveExpenses();
                        Toast.makeText(DespesaActivity.this, "Despesa salva com sucesso!!", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(DespesaActivity.this, "Receita não salva\n Sem acesso a rede.", Toast.LENGTH_SHORT).show();
                    }
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
        String type = "d";
        userMovementation.setValue(d);

        userMovementation.setCategory(textCategory.getText().toString());

        userMovementation.setDescription(textDescription.getText().toString());

        userMovementation.setDate(textDate.getText().toString());

        userMovementation.setType(type);

        recoveredValue = d;

        updatedExpense =  totalExpenditure + recoveredValue;

        setCurrentExpense(updatedExpense);

        userMovementation.saveMovementation(textDate.getText().toString());

    }


    public void getTotalExpenditure(){
        String userEmail = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        DatabaseReference userRef = firebaseRef.child("usuarios").child(userEmail);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                totalExpenditure = usuario.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setCurrentExpense(Double expense){
        String userEmail = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        DatabaseReference userRef = firebaseRef.child("usuarios").child(userEmail);

        userRef.child("despesaTotal").setValue(expense);
    }

}
