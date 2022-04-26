package app.jasonhk.hkcc.sleeptracker;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener
{
    MediaPlayer mediaPlayer = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.music_rain);
        mediaPlayer.setLooping(true);
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
//        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
//        mediaPlayer.setOnPreparedListener(this);
//        mediaPlayer.prepareAsync();

        mediaPlayer.start();

        return Service.START_STICKY;
    }

    @Override
    public void onPrepared(MediaPlayer player)
    {
        player.start();
    }

    @Override
    public void onDestroy()
    {
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
