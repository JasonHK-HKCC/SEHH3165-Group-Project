package app.jasonhk.hkcc.sleeptracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface SleepSessionDao
{
    @Insert
    Completable insert(SleepSession session);

    @Update
    void update(SleepSession... sessions);

    @Delete
    void delete(SleepSession... sessions);

    @Query("SELECT * FROM sleep_sessions ORDER BY start_time DESC")
    Single<List<SleepSession>> getAll();
}
