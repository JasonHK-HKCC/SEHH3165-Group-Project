package app.jasonhk.hkcc.sleeptracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;

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

        val totalSleeps = sessions.size();

        TextView totalSleepsText = view.findViewById(R.id.total_sleeps);
        totalSleepsText.setText(String.valueOf(totalSleeps));

        val totalTime = sessions.stream()
                .mapToLong(session -> Duration.between(session.startTime, session.endTime).getSeconds())
                .sum();
        TextView averageTimeText = view.findViewById(R.id.average_time);
        averageTimeText.setText(DataModel.formatDuration((totalSleeps == 0) ? 0 : totalTime / totalSleeps));

        sessions = DataModel.getAllSessionsAfter(LocalDateTime.now().minusWeeks(1)).blockingGet()
        List<SleepSession> time = new ArrayList<>();
        SleepSession[] t1 = time.toArray(new SleepSession[0]);
        SleepSession[] t2 = time.toArray(new SleepSession[1]);
        SleepSession[] t3 = time.toArray(new SleepSession[2]);
        SleepSession[] t4 = time.toArray(new SleepSession[3]);
        SleepSession[] t5 = time.toArray(new SleepSession[4]);
        SleepSession[] t6 = time.toArray(new SleepSession[5]);
        SleepSession[] t7 = time.toArray(new SleepSession[6]);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(DataModel.formatDuration(t1.length));
        TextView textView5 = view.findViewById(R.id.textView5);
        textView5.setText(DataModel.formatDuration(t2.length));
        TextView textView10 = view.findViewById(R.id.textView10);
        textView10.setText(DataModel.formatDuration(t3.length));
        TextView textView13 = view.findViewById(R.id.textView13);
        textView13.setText(DataModel.formatDuration(t4.length));
        TextView textView14 = view.findViewById(R.id.textView14);
        textView14.setText(DataModel.formatDuration(t5.length));
        TextView textView17 = view.findViewById(R.id.textView17);
        textView17.setText(DataModel.formatDuration(t6.length));
        TextView textView19 = view.findViewById(R.id.textView19);
        textView19.setText(DataModel.formatDuration(t7.length));



    }
}