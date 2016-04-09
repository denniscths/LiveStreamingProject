package example.videostreamingtest1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.widget.VideoView;

public class PlayStreamVideoActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String path;
    //private HashMap<String, String> options;
    private VideoView mVideoView;

    TextView txtHostName, txtIPAddr, txtRoomName, txtRoomStreamTitle, txtRoomStreamText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Post post = (Post) getIntent().getSerializableExtra("post");



        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.activity_play_stream_video);
        mVideoView = (VideoView) findViewById(R.id.vitamio_videoView);

//        Intent intent = getIntent();
        String ipaddr = "rtsp://" + post.ipaddr + ":1935/"+ post.post_title + "/myStream";

        txtHostName = (TextView) findViewById(R.id.txtHostName);
        txtIPAddr = (TextView) findViewById(R.id.txtIPAddr);
        txtRoomName = (TextView) findViewById(R.id.txtRoomName);
        txtRoomStreamTitle = (TextView) findViewById(R.id.txtRoomStreamTitle);
        txtRoomStreamText = (TextView) findViewById(R.id.txtRoomStreamText);

        txtHostName.setText(post.post_text);
        txtIPAddr.setText(post.ipaddr);
        txtRoomName.setText(post.post_title);
        txtRoomStreamTitle.setText(post.stream_title);
        txtRoomStreamText.setText(post.stream_text);

        path = ipaddr;
        /*options = new HashMap<>();
        options.put("rtmp_playpath", "");
        options.put("rtmp_swfurl", "");
        options.put("rtmp_live", "1");
        options.put("rtmp_pageurl", "");*/
        mVideoView.setVideoPath(path);
        //mVideoView.setVideoURI(Uri.parse(path), options);
mVideoView.getAudioTrack();
        mVideoView.start();

        //Media Controller
//        mVideoView.setMediaController(new MediaController(this));
//        mVideoView.requestFocus();

        mVideoView.setBufferSize(1000);
//        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.setPlaybackSpeed(1.0f);
//
//            }
//        });
    }
}