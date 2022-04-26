package app.jasonhk.hkcc.sleeptracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.time.LocalDateTime;
import java.time.LocalTime;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import lombok.val;

/**
 *
 */
public class HomeFragment extends Fragment implements View.OnClickListener
{
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

        val preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        val name = preferences.getString("user_name", getString(R.string.user));
        ((TextView) view.findViewById(R.id.welcome_message))
                .setText(getString(getWelcomeMessage(), name));

        val sleepButton = (ExtendedFloatingActionButton) view.findViewById(R.id.sleep_button);
        sleepButton.setOnClickListener(this);
        if (DataModel.isSessionStarted())
        {
            sleepButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_stop));
            sleepButton.setText(R.string.sleeping_end);
        }
        else
        {
            sleepButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_play));
            sleepButton.setText(R.string.sleeping_start);
        }
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.sleep_button)
        {
            val button = (ExtendedFloatingActionButton) view;

            if (DataModel.isSessionStarted())
            {
                DataModel.setEndTime(LocalDateTime.now());
                DataModel.storeSession()
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribe(() -> {
                             button.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_play));
                             button.setText(R.string.sleeping_start);

                             getActivity().stopService(new Intent(getContext(), MusicService.class));
                         });
            }
            else
            {
                DataModel.setStartTime(LocalDateTime.now());

                button.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_stop));
                button.setText(R.string.sleeping_end);

                getActivity().startService(new Intent(getContext(), MusicService.class));
            }
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
}