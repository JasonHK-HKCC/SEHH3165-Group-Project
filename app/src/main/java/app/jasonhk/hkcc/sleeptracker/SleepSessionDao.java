package app.jasonhk.hkcc.sleeptracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.time.LocalDateTime;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface SleepSessionDao
{
    @Insert
    Completable insert(SleepSession session);

    @Update
    Completable update(SleepSession... sessions);

    @Delete
    Completable delete(SleepSession... sessions);

    @Query("SELECT * FROM sleep_sessions WHERE id = :id LIMIT 1")
    Maybe<SleepSession> getById(int id);

    @Query("SELECT * FROM sleep_sessions ORDER BY start_time DESC")
    Single<List<SleepSession>> getAll();

    @Query("SELECT * FROM sleep_sessions WHERE end_time > :time ORDER BY start_time DESC")
    @TypeConverters({ TimestampConverter.class })
    Single<List<SleepSession>> getAllAfter(LocalDateTime time);
}
