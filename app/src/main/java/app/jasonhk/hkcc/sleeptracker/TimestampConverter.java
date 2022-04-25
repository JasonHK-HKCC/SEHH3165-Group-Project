package app.jasonhk.hkcc.sleeptracker;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

public class TimestampConverter
{
    @TypeConverter
    public static LocalDateTime fromTimestamp(long timestamp)
    {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
    }

    @TypeConverter
    public static long toTimestamp(@NonNull LocalDateTime time)
    {
        return time.toEpochSecond(ZoneOffset.UTC);
    }
}
