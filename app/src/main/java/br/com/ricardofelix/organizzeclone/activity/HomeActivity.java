package br.com.ricardofelix.organizzeclone.activity;

import android.content.Intent;
import android.os.Bundle;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.Menu;

import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.ricardofelix.organizzeclone.Helper.Base64Custom;
import br.com.ricardofelix.organizzeclone.Helper.CalendarCustom;
import br.com.ricardofelix.organizzeclone.Helper.ConnectivityChecker;
import br.com.ricardofelix.organizzeclone.R;
import br.com.ricardofelix.organizzeclone.config.ConfigFirebase;
import br.com.ricardofelix.organizzeclone.adapter.MovimentationsAdapter;
import br.com.ricardofelix.organizzeclone.model.UserMovementation;
import br.com.ricardofelix.organizzeclone.model.Usuario;

public class HomeActivity extends AppCompatActivity {
    private ValueEventListener userValueEventListener;
    private DatabaseReference dataRef,userRef;
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    private TextView textUserName,textAmount;
    private String userName;
    private MaterialCalendarView calendarView;
    private RecyclerView recyclerMovimentations;
    private Double totalExpenditure = 0.0;
    private Double totalRevenue = 0.0;
    private Double amount = 0.0;
    private List<UserMovementation> movimentations = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        calendarView = findViewById(R.id.calendarView);
        textUserName = findViewById(R.id.textUserName);
        textAmount = findViewById(R.id.textAmount);
        recyclerMovimentations = findViewById(R.id.recyclerMovimentations);
        getSupportActionBar().setTitle("");

        configureCalendar();

        UserMovementation usr1 = new UserMovementation();

        usr1.setType("d");
        usr1.setValue(22.5);
        usr1.setCategory("Compras");
        usr1.setDate("28/03/20");

        UserMovementation usr2 = new UserMovementation();

        usr2.setType("r");
        usr2.setValue(2700.90);
        usr2.setCategory("Salário");
        usr2.setDate("28/03/20");

        movimentations.add(usr1);
        movimentations.add(usr2);


        //Conf Adapter
        MovimentationsAdapter mvAdapter = new MovimentationsAdapter(movimentations,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerMovimentations.setLayoutManager(layoutManager);
        recyclerMovimentations.setHasFixedSize(true);
        recyclerMovimentations.setAdapter(mvAdapter);


    }


    public void setAdapter(){


    }



    public void getUserData(){
        dataRef = ConfigFirebase.getFirebaseDatabase();
        String userId = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());

         userRef = dataRef.child("usuarios").child(userId);

        userValueEventListener = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                DecimalFormat df = new DecimalFormat("0.00");


                userName = usuario.getNome();
                totalExpenditure = usuario.getDespesaTotal();
                totalRevenue = usuario.getReceitaTotal();
                amount = totalRevenue - totalExpenditure;

                textUserName.setText("Olá "+splitUserName(userName)+" !");
                textAmount.setText("R$ "+df.format(amount));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void configureCalendar(){
        calendarView.setTitleMonths(CalendarCustom.getMounths());

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Toast.makeText(HomeActivity.this, "Mês: - "+date.getMonth(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addDespesas(View v){
        startActivity(new Intent(HomeActivity.this,DespesaActivity.class));
    }

    public void addReceitas(View v){
        startActivity(new Intent(HomeActivity.this,ReceitaActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }


    public void logout(){
        auth = ConfigFirebase.getAuth();
        auth.signOut();
        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.optionLogout:
                logout();
                break;

            default:
                Toast.makeText(this, "Impossivel se deslogar do App", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public String splitUserName(String name){
        String []userName = name.split(" ");

        return userName[0];
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUserData();
        boolean answer = ConnectivityChecker.getConnectivity(this);
        Toast.makeText(this, answer+"" , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        userRef.addValueEventListener(userValueEventListener);
    }
}
