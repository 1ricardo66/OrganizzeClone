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
    private FirebaseAuth auth;
    private TextView textUserName;
    private String userName;
    private MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


   
}
