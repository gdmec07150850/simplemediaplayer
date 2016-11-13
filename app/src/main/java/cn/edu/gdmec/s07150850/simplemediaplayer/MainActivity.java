package cn.edu.gdmec.s07150850.simplemediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private  static  final String TAG="SimpleMediaPlayer";
    private MediaPlayer mMadiaPlayer;
    private String mPath;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        Uri uri=intent.getData();
        if (uri!=null){
            mPath=uri.getPath();
            setTitle(mPath);
            if (intent.getType().contains("audio")){
                setContentView(android.R.layout.simple_list_item_1);
                mMadiaPlayer=new MediaPlayer();

                try{
                    mMadiaPlayer.setDataSource(mPath);
                    mMadiaPlayer.prepare();
                    mMadiaPlayer.start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else  if (intent.getType().contains("video")){
                mVideoView=new VideoView(this);
                mVideoView.setVideoPath(mPath);
                mVideoView.setMediaController(new MediaController(this));
                mVideoView.start();
                setContentView(mVideoView);
            }

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"暂停");
        menu.add(0,2,0,"开始");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                if (mMadiaPlayer==null||!mMadiaPlayer.isPlaying()){
                    Toast.makeText(this,"没有音乐文件，不需要暂停",Toast.LENGTH_SHORT).show();
                }else {
                    mMadiaPlayer.pause();
                }
                break;
            case 2:
                if (mMadiaPlayer==null){
                    Toast.makeText(this,"没有选中音乐文件，请到文件夹中点击音乐文件后再播放",Toast.LENGTH_SHORT).show();
                }else {
                    mMadiaPlayer.start();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMadiaPlayer!=null){
            mMadiaPlayer.stop();
        }
        if (mVideoView!=null){
            mVideoView.stopPlayback();
        }
    }
}
