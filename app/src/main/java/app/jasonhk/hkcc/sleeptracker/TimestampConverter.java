package app.jasonhk.hkcc.sleeptracker;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.text.ParseException;
import java.time.LocalDateTime;

import lombok.experimental.UtilityClass;

public class TimestampConverter
{
    @TypeConverter
    public static @Nullable LocalDateTime fromTimestamp(@Nullable String timestamp)
    {
        if (timestamp != null)
        {
            return LocalDateTime.parse(timestamp);
        }

        return null;
    }

    @TypeConverter
    public static @Nullable String toTimestamp(@Nullable LocalDateTime time)
    {
        if (time != null)
        {
            return time.toString();
        }

        return null;
    }
}
