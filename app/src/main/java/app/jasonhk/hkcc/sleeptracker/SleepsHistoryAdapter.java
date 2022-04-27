package app.jasonhk.hkcc.sleeptracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lombok.val;

public class SleepsHistoryAdapter extends RecyclerView.Adapter<SleepsHistoryAdapter.ViewHolder>
{
    private List<SleepSession> dataSet;

    public SleepsHistoryAdapter(List<SleepSession> dataSet)
    {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType)
    {
        val view = LayoutInflater.from(parent.getContext())
                                 .inflate(R.layout.recycler_sleep_session, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.setSession(dataSet.get(position));
    }

    @Override
    public int getItemCount()
    {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView startTimeValue;
        TextView stopTimeValue;

        public ViewHolder(View view)
        {
            super(view);

            startTimeValue = view.findViewById(R.id.start_time_value);
            stopTimeValue = view.findViewById(R.id.stop_time_value);
        }

        public void setSession(SleepSession session)
        {
            startTimeValue.setText(session.startTime.toString());
            stopTimeValue.setText(session.endTime.toString());
        }
    }
}
