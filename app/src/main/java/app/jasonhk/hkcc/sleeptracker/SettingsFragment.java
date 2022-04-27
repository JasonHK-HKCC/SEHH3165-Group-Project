package app.jasonhk.hkcc.sleeptracker;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *
 */
public class SettingsFragment extends PreferenceFragmentCompat
{
    @Override
    public void onCreatePreferences(
            @Nullable Bundle savedInstanceState, @Nullable String rootKey)
    {
        setPreferencesFromResource(R.xml.preferences_user, rootKey);

        EditTextPreference name = findPreference("user_name");
        name.setOnBindEditTextListener(TextView::setSingleLine);
    }
}