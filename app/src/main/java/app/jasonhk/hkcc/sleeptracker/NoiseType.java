package app.jasonhk.hkcc.sleeptracker;

import lombok.Getter;
import lombok.val;

public enum NoiseType
{
    WHITE_NOISE("white_noise", R.raw.music_white_noise),
    DRYER("dryer", R.raw.music_dryer),
    HEATER("heater", R.raw.music_heater),
    RAIN("rain", R.raw.music_rain),
    STREAM("stream", R.raw.music_stream),
    BOTH_OF_US("both_of_us", R.raw.music_both_of_us),
    MINDFULNESS("mindfulness", R.raw.music_mindfulness),
    MORNING_GARDEN("morning_garden", R.raw.music_morning_garden),
    ORDER("order", R.raw.music_order),
    SHOWREEL("showreel", R.raw.music_showreel),
    SOUL("soul", R.raw.muisc_soul);

    @Getter
    final String key;

    @Getter
    final int noise;

    NoiseType(String key, int noise)
    {
        this.key = key;
        this.noise = noise;
    }

    static NoiseType from(String key)
    {
        for (val type : NoiseType.values())
        {
            if (type.key.equals(key))
            {
                return type;
            }
        }

        return null;
    }
}
