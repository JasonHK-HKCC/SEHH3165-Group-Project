package app.jasonhk.hkcc.sleeptracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import lombok.val;

/**
 *
 */
public class HomeFragment extends Fragment
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
    private static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    private SharedPreferences preferences;

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

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        TextView tv_beforeSleep = view.findViewById(R.id.tv_beforeSleep);
        TextView tv_afterSleep = view.findViewById(R.id.tv_afterSleep);

        val name = preferences.getString("user_name", getString(R.string.user_name_default));
        ((TextView) view.findViewById(R.id.welcome_message))
                .setText(getString(getWelcomeMessage(), name));

        CheckBox playNoise = view.findViewById(R.id.play_noise);
        playNoise.setChecked(preferences.getBoolean("noise_play", true));
        playNoise.setOnCheckedChangeListener(this);

        val sleepButton = (ExtendedFloatingActionButton) view.findViewById(R.id.sleep_button);
        sleepButton.setOnClickListener(this);
        if (DataModel.isSessionStarted())
        {
            sleepButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_stop));
            sleepButton.setText(R.string.sleeping_end);

            tv_afterSleep.setVisibility(View.VISIBLE);
            tv_afterSleep.setText(getString(R.string.help_sleeping, DataModel.getStartTime().format(formatter)));
            tv_beforeSleep.setVisibility(View.GONE);
        }
        else
        {
            sleepButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_play));
            sleepButton.setText(R.string.sleeping_start);

            tv_afterSleep.setVisibility(View.GONE);
            tv_beforeSleep.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.sleep_button)
        {
            val button = (ExtendedFloatingActionButton) view;

            TextView tv_beforeSleep = getActivity().findViewById(R.id.tv_beforeSleep);
            TextView tv_afterSleep = getActivity().findViewById(R.id.tv_afterSleep);

            CheckBox playNoise = getActivity().findViewById(R.id.play_noise);

            if (DataModel.isSessionStarted())
            {
                DataModel.setEndTime(LocalDateTime.now());
                DataModel.storeSession()
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribe(() -> {
                             button.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_play));
                             button.setText(R.string.sleeping_start);

                             tv_afterSleep.setVisibility(View.GONE);
                             tv_beforeSleep.setVisibility(View.VISIBLE);

                             playNoise.setEnabled(true);
                             if (playNoise.isChecked())
                             {
                                 getActivity().stopService(new Intent(getContext(), NoiseService.class));
                             }
                         });
            }
            else
            {
                DataModel.setStartTime(LocalDateTime.now());

                button.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_stop));
                button.setText(R.string.sleeping_end);

                tv_afterSleep.setVisibility(View.VISIBLE);
                tv_afterSleep.setText(getString(R.string.help_sleeping, DataModel.getStartTime().format(formatter)));
                tv_beforeSleep.setVisibility(View.GONE);

                playNoise.setEnabled(false);
                if (playNoise.isChecked())
                {
                    val intent = new Intent(getContext(), NoiseService.class)
                            .putExtra("noise_type", preferences.getString(
                                    "noise_type", getString(R.string.noise_type_default)))
                            .putExtra("noise_timer", getNoiseTimer());
                    getActivity().startService(intent);
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean checked)
    {
        if (button.getId() == R.id.play_noise)
        {
            preferences.edit()
                       .putBoolean("noise_play", checked)
                       .apply();
        }
    }

    /**
     * Returns the resource ID of the welcome message determined by the time of day.
     *
     * @return The resource ID of the message.
     */
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

    private long getNoiseTimer()
    {
        switch (preferences.getString("noise_timer", getString(R.string.noise_timer_default)))
        {
            case "5_minutes":
                return 300_000;
            case "10_minutes":
                return 600_000;
            case "15_minutes":
                return 900_000;
            case "30_minutes":
                return 1_800_000;
            case "1_hour":
                return 3_600_000;
            case "2_hours":
                return 7_200_000;
            default:
                return 0;
        }
    }
}