package app.jasonhk.hkcc.sleeptracker;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataModel
{
    private boolean initialized = false;

    private SharedPreferences preferences;
    public AppDatabase database;

    public void init(Context context, SharedPreferences preferences)
    {
        if (initialized) { return; }

        DataModel.preferences = preferences;

        database = Room.databaseBuilder(context, AppDatabase.class, "database").build();

        initialized = true;
    }

    public void insertSession(SleepSession session)
    {
//        database.sleepSessionDao().insert(session);

        database.sleepSessionDao().insert(session)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
