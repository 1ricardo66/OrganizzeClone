package br.com.ricardofelix.organizzeclone.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.ricardofelix.organizzeclone.Helper.Base64Custom;
import br.com.ricardofelix.organizzeclone.Helper.CalendarCustom;
import br.com.ricardofelix.organizzeclone.R;
import br.com.ricardofelix.organizzeclone.config.ConfigFirebase;
import br.com.ricardofelix.organizzeclone.model.Usuario;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth auth = ConfigFirebase.getAuth();
    private DatabaseReference dataRef;
    private TextView textUserName;
    private String userName;
    private MaterialCalendarView calendarView;
    private Double totalExpenditure = 0.0;
    private Double totalRevenue = 0.0;
    private Double amount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getUserData();

        calendarView = findViewById(R.id.calendarView);
        configureCalendar();

        //calendarView.setWeekDayLabels(CalendarCustom.getDaysOfWeek());

        textUserName = findViewById(R.id.textUserName);
/*        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    }



    public void getUserData(){
        DatabaseReference dataRef = ConfigFirebase.getFirebaseDatabase();
        String userId = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());

        DatabaseReference userRef = dataRef.child("usuarios").child(userId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                userName = usuario.getNome();
                totalExpenditure = usuario.getDespesaTotal();
                totalRevenue = usuario.getReceitaTotal();
                amount = totalRevenue - totalExpenditure;

                /*
                *   Mostrar dados do usuario com setText.
                *
                */

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
}
