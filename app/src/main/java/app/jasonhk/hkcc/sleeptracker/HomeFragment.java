package app.jasonhk.hkcc.sleeptracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.time.LocalDateTime;
import java.time.LocalTime;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.val;

/**
 *
 */
public class HomeFragment extends Fragment implements View.OnClickListener
{
    private AppDatabase database;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        database = Room.databaseBuilder(getContext(), AppDatabase.class, AppDatabase.NAME).build();

        val preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        val name = preferences.getString("user_name", getString(R.string.user));
        ((TextView) view.findViewById(R.id.welcome_message))
                .setText(getString(getWelcomeMessage(), name));

        val sleepButton = (ExtendedFloatingActionButton) view.findViewById(R.id.sleep_button);
        sleepButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.sleep_button)
        {
            val session = new SleepSession();
            session.startTime = LocalDateTime.now();
            session.endTime = LocalDateTime.now();

            DataModel.insertSession(session);

            val s = DataModel.database.sleepSessionDao().getAll()
                              .subscribeOn(Schedulers.io())
                              .observeOn(AndroidSchedulers.mainThread())
                              .subscribe((sleepSessions -> { ((ExtendedFloatingActionButton) view).setText(sleepSessions.get(0).startTime.toString()); }));
//            ((ExtendedFloatingActionButton) view).setText(s.get(0).startTime.toString();
        }
    }

    private int getWelcomeMessage()
    {
        val now = LocalTime.now();

        if (now.isBefore(LocalTime.of(5, 0)))
        {
            return R.string.welcome_night;
        }
        else if (now.isBefore(LocalTime.NOON))
        {
            return R.string.welcome_morning;
        }
        else if (now.isBefore(LocalTime.of(17, 0)))
        {
            return R.string.welcome_afternoon;
        }
        else if (now.isBefore(LocalTime.of(21, 0)))
        {
            return R.string.welcome_evening;
        }
        else
        {
            return R.string.welcome_night;
        }
    }
}