package app.jasonhk.hkcc.sleeptracker;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import lombok.val;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener
{
    private Timer timer = null;
    private MediaPlayer mediaPlayer = null;

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

        timer = new Timer();
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        val type = NoiseType.from(intent.getStringExtra("noise_type"));

        mediaPlayer = MediaPlayer.create(this, type.getNoise());
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        timer.schedule(new TimerTask() {
            @Override
            public void run() { stopSelf(); }
        }, 10000);

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
        timer.cancel();

        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
