package example.videostreamingtest1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class RecordSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStream;
    EditText etIPAddr, etName;

    String room_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_settings);

        btnStream = (Button)findViewById(R.id.btnStream);
        etIPAddr = (EditText)findViewById(R.id.etIPAddr);
        etName = (EditText)findViewById(R.id.etName);

        btnStream.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStream:
                HashMap postData = new HashMap();

                String addr = etIPAddr.getText().toString();
                String name = etName.getText().toString();

                postData.put("txtAddr", addr);
                postData.put("txtName", name);

                PostResponseAsyncTask task1 = new PostResponseAsyncTask(RecordSettingsActivity.this, postData, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            room_name = s;
                            Intent i = new Intent(RecordSettingsActivity.this, StreamVideoActivity.class);
                            i.putExtra("ipaddr", etIPAddr.getText().toString());
                            i.putExtra("room_name", room_name);
                            startActivity(i);
                        }
                    });
                task1.execute("http://livestreaming-citycal.rhcloud.com/start_stream.php");





                break;
        }
    }
}
