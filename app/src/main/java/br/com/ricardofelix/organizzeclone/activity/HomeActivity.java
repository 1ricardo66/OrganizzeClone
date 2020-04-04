package br.com.ricardofelix.organizzeclone.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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


import android.util.Log;
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
    private ValueEventListener movimentationsValueEventListener;
    private DatabaseReference dataRef = ConfigFirebase.getFirebaseDatabase()
            ,userRef,movimentationsRef;
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    private TextView textUserName,textAmount;
    private String userName;
    private MovimentationsAdapter mvAdapter;
    private MaterialCalendarView calendarView;
    private RecyclerView recyclerMovimentations;
    private Double totalExpenditure = 0.0;
    private Double totalRevenue = 0.0;
    private Double amount = 0.0;
    private List<UserMovementation> movimentations = new ArrayList<>();
    private String selectedMounth;


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



        //Conf Adapter
        mvAdapter = new MovimentationsAdapter(movimentations,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerMovimentations.setLayoutManager(layoutManager);
        recyclerMovimentations.setHasFixedSize(true);
        recyclerMovimentations.setAdapter(mvAdapter);


    }


    public void getMovimentation(){
        String userEmail = auth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codeToBase64( userEmail );
        movimentationsRef = dataRef.child("movimentacao")
                .child( idUsuario )
                .child( selectedMounth );

        movimentationsValueEventListener = movimentationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                movimentations.clear();
                for (DataSnapshot data: dataSnapshot.getChildren() ){

                    UserMovementation movimentation = data.getValue( UserMovementation.class );
                    movimentations.add( movimentation );


                }

                mvAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    public void getUserData(){
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

        CalendarDay currentDate = calendarView.getCurrentDate();
        String mounth = String.format("%02d",currentDate.getMonth());
        selectedMounth = mounth+""+currentDate.getYear();

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Toast.makeText(HomeActivity.this, "Mês: - "+date.getMonth(), Toast.LENGTH_SHORT).show();
                String mounth = String.format("%02d",date.getMonth());
                selectedMounth = mounth+""+date.getYear();
                getMovimentation();

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
        finish();
        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
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
        ConnectivityChecker.isConnected(this);
        getUserData();
        getMovimentation();

    }

    @Override
    protected void onStop() {
        super.onStop();

        userRef.addValueEventListener(userValueEventListener);
        movimentationsRef.addValueEventListener(movimentationsValueEventListener);
    }
}
