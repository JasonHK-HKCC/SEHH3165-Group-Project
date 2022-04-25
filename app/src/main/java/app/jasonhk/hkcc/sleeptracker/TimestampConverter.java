package app.jasonhk.hkcc.sleeptracker;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.text.ParseException;
import java.time.LocalDateTime;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TimestampConverter
{
    @TypeConverter
    public @Nullable LocalDateTime fromTimestamp(@Nullable String timestamp)
    {
        if (timestamp != null)
        {
            return LocalDateTime.parse(timestamp);
        }

        return null;
    }
}
