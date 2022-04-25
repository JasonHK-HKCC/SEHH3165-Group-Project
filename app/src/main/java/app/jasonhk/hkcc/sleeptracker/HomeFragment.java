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

import lombok.val;

/**
 *
 */
public class HomeFragment extends Fragment
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

        val name = preferences.getString("user_name", "");
        ((TextView) view.findViewById(R.id.welcome_message))
                .setText(getString(R.string.welcome_night, name));
    }
}