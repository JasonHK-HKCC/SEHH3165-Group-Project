package app.jasonhk.hkcc.sleeptracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDateTime;

@Entity
public class SleepSession
{
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "start_time")
    @TypeConverters({ TimestampConverter.class })
    public LocalDateTime startTime;

    @ColumnInfo(name = "end_time")
    @TypeConverters({ TimestampConverter.class })
    public LocalDateTime endTime;
}
