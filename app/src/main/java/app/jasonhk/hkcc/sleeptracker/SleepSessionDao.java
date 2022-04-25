package app.jasonhk.hkcc.sleeptracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface SleepSessionDao
{
    @Insert
    void insert(SleepSession session);

    @Update
    void update(SleepSession... sessions);

    @Delete
    void delete(SleepSession... sessions);
}
