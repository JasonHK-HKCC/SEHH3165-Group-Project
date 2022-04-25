package app.jasonhk.hkcc.sleeptracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.val;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.d("LocalDateTime", String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)));

        val preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        DataModel.init(getApplicationContext(), preferences);


//        val session = new SleepSession();
//        session.startTime = LocalDateTime.now();
//        session.endTime = LocalDateTime.now();
//        DataModel.database.sleepSessionDao().insert(session).subscribeOn(Schedulers.io()).blockingAwait();
//
//        val s = DataModel.database.sleepSessionDao().getAll().subscribeOn(Schedulers.io()).blockingGet();
//        Log.d("Test", String.valueOf(s.size()));

        NavHostFragment      navHostFragment  = (NavHostFragment) getSupportFragmentManager().findFragmentById(
                R.id.nav_host_fragment);
        NavController        navController    = navHostFragment.getNavController();
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigation, navController);
    }
}