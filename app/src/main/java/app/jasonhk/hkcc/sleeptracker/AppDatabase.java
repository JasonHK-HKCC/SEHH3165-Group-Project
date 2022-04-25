package app.jasonhk.hkcc.sleeptracker;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { SleepSession.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    public static final String NAME = "database";

    public abstract SleepSessionDao sleepSessionDao();
}
