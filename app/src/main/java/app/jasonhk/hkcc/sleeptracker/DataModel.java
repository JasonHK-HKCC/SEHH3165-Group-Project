package app.jasonhk.hkcc.sleeptracker;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import lombok.val;

@UtilityClass
public class DataModel
{
    @Getter
    private LocalDateTime startTime;

    @Getter
    @Setter
    private LocalDateTime endTime;

    private SharedPreferences preferences;
    public AppDatabase database;

    /**
     * Indicate whether the class was initialized.
     */
    private boolean initialized = false;

    private final String START_TIME_KEY = "Internal_StartTime";

    public void init(Context context, SharedPreferences preferences)
    {
        if (initialized) { return; }

        DataModel.preferences = preferences;

        if (preferences.contains(START_TIME_KEY))
        {
            val timestamp = preferences.getLong(START_TIME_KEY, 0);
            startTime = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
        }

        database = Room.databaseBuilder(context, AppDatabase.class, "database").build();

        initialized = true;
    }

    public boolean isSessionStarted()
    {
        return (startTime != null);
    }

    public void setStartTime(LocalDateTime time)
    {
        startTime = time;

        if (time != null)
        {
            preferences.edit()
                       .putLong(START_TIME_KEY, time.toEpochSecond(ZoneOffset.UTC))
                       .apply();
        }
    }

    public Completable storeSession()
    {
        val session = new SleepSession();
        session.startTime = startTime;
        session.endTime = LocalDateTime.now();

        val completable = database.sleepSessionDao().insert(session)
                                  .subscribeOn(Schedulers.io());

        startTime = null;
        endTime = null;

        preferences.edit().remove(START_TIME_KEY).apply();
        return completable;
    }

    public Completable insertSession(SleepSession session)
    {
        return database.sleepSessionDao().insert(session)
                       .subscribeOn(Schedulers.io());
    }

    public Maybe<SleepSession> getSessionById(int id)
    {
        return database.sleepSessionDao().getById(id)
                       .subscribeOn(Schedulers.io());
    }

    public Single<List<SleepSession>> getAllSessions()
    {
        return database.sleepSessionDao().getAll()
                       .subscribeOn(Schedulers.io());
    }

    public Single<List<SleepSession>> getAllSessionsAfter(LocalDateTime time)
    {
        return database.sleepSessionDao().getAllAfter(time)
                       .subscribeOn(Schedulers.io());
    }

    public String formatDuration(long seconds)
    {
        return String.format(Locale.getDefault(), "%d:%02d:%02d",
                             seconds / 3600, (seconds % 3600) / 60, seconds % 60);
    }
}
