package app.jasonhk.hkcc.sleeptracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.LocalDateTime;

import lombok.val;

/**
 *
 */
public class AnalysisFragment extends Fragment
{
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analysis, container, false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        val sessions = DataModel.getAllSessionsAfter(LocalDateTime.now().minusWeeks(1)).blockingGet();

        TextView totalSleeps = view.findViewById(R.id.total_sleeps);
        totalSleeps.setText(String.valueOf(sessions.size()));
    }
}