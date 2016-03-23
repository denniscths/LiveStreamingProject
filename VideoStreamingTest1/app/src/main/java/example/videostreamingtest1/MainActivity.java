package example.videostreamingtest1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener {

    Button btnStream;
    EditText etIPAddr;

    Button btnPlay;
    EditText etPlayIPAddr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnStream = (Button)findViewById(R.id.btnStream);
        etIPAddr = (EditText)findViewById(R.id.etIPAddr);

        btnPlay = (Button)findViewById(R.id.btnPlay);
        etPlayIPAddr = (EditText)findViewById(R.id.etPlayIPAddr);

        btnStream.setOnClickListener(this);
        btnPlay.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStream:
                Intent i = new Intent(MainActivity.this, StreamVideoActivity.class);
                i.putExtra("ipaddr", etIPAddr.getText().toString());
                startActivity(i);
                break;
            case R.id.btnPlay:
                Intent i2 = new Intent(MainActivity.this, PlayStreamVideoActivity.class);
                i2.putExtra("ipaddr", etPlayIPAddr.getText().toString());
                startActivity(i2);
                break;
        }
    }
}
