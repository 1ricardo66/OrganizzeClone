package br.com.ricardofelix.organizzeclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.ricardofelix.organizzeclone.Helper.Base64Custom;
import br.com.ricardofelix.organizzeclone.Helper.ConnectivityChecker;
import br.com.ricardofelix.organizzeclone.Helper.DateCustom;
import br.com.ricardofelix.organizzeclone.R;
import br.com.ricardofelix.organizzeclone.config.ConfigFirebase;
import br.com.ricardofelix.organizzeclone.model.UserMovementation;
import br.com.ricardofelix.organizzeclone.model.Usuario;

public class ReceitaActivity extends AppCompatActivity {
    private EditText textDate, textDescription,textCategory,textValue;
    private FirebaseAuth auth;
    private DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDatabase();
    private Double currentRevenue,totalRevenue, recoveredRevenue;

    public ReceitaActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);
        textValue = findViewById(R.id.editValueRevenue);
        textDate = findViewById(R.id.editDateRevenue);
        textDescription = findViewById(R.id.editDescriptionRevenue);
        textCategory = findViewById(R.id.editCategoryRevenue);

        getTotalRevenue();

        FloatingActionButton fab = findViewById(R.id.okBtnRevenue);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fieldsValidation()){

                    if(ConnectivityChecker.isConnected(getApplicationContext())){

                        saveRevenue();
                    }else{
                        Toast.makeText(ReceitaActivity.this, "Receita não salva\n Sem acesso a rede.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ReceitaActivity.this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        textDate.setText(DateCustom.getDate());

        if(textDate != null){
            Toast.makeText(this, "Different null", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "IT's NULL", Toast.LENGTH_SHORT).show();
        }
    }


    private void saveRevenue(){
        UserMovementation userMovementation = new UserMovementation();
        String date,type="r",description,category,value;
        Double doublevalue;

        value = textValue.getText().toString().replace(",",".");
        doublevalue = Double.parseDouble(value);
        currentRevenue = doublevalue;
        date = textDate.getText().toString();
        description = textDescription.getText().toString();
        category = textCategory.getText().toString();

        userMovementation.setDate(date);
        userMovementation.setDescription(description);
        userMovementation.setCategory(category);
        userMovementation.setValue(doublevalue);
        userMovementation.setType(type);

        recoveredRevenue = totalRevenue + currentRevenue;
        setCurrentRevenue(recoveredRevenue);

        userMovementation.saveMovementation(date);



    }


    public void setCurrentRevenue(Double revenue){
        FirebaseAuth auth = ConfigFirebase.getAuth();
        DatabaseReference firebaseRef = ConfigFirebase.getFirebaseDatabase().child("usuarios");

        String userId = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());

        firebaseRef.child(userId).child("receitaTotal").setValue(revenue);


    }


    private Boolean fieldsValidation(){
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


    public void getTotalRevenue(){
        auth = ConfigFirebase.getAuth();

        String userEmail = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        DatabaseReference userRef = firebaseRef.child("usuarios").child(userEmail);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                totalRevenue = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
