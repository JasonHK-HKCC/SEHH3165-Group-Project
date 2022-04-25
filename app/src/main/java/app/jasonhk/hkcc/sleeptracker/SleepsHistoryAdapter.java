package app.jasonhk.hkcc.sleeptracker;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View view)
        {
            super(view);
        }
    }
}
