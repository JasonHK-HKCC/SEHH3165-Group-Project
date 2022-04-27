package app.jasonhk.hkcc.sleeptracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.Duration;
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

        val totalSleeps = sessions.size();

        TextView totalSleepsText = view.findViewById(R.id.total_sleeps);
        totalSleepsText.setText(String.valueOf(totalSleeps));

        val totalTime = sessions.stream()
                .mapToLong(session -> Duration.between(session.startTime, session.endTime).getSeconds())
                .sum();

        TextView averageTimeText = view.findViewById(R.id.average_time);
        averageTimeText.setText(DataModel.formatDuration((totalSleeps == 0) ? 0 : totalTime / totalSleeps));

        TextView dailyTimeValue = view.findViewById(R.id.daily_time);
        dailyTimeValue.setText(DataModel.formatDuration(totalTime / 7));

        RecyclerView historyRecycler = view.findViewById(R.id.sleep_history_recycler);
        historyRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        val all = DataModel.getAllSessions().blockingGet();
        val historyAdapter = new SleepsHistoryAdapter(all);
        historyRecycler.setAdapter(historyAdapter);
    }
}