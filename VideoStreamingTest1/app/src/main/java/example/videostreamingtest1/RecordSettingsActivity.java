package example.videostreamingtest1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;

public class RecordSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    final String LOG = "RecordSettingsActivity";

    Button btnStream;
    EditText etIPAddr, etName;

    Button btnTakePic;
    Button btnChooseGallery;
    ImageView imageView;
    EditText etUploadImageName;
    EditText etStreamTitle;
    EditText etStreamText;

    String room_name;

    private String encoded_string, image_name;
    private Bitmap bitmap;
    private File file;
    private Uri file_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_settings);

        btnStream = (Button)findViewById(R.id.btnStream);
        etIPAddr = (EditText)findViewById(R.id.etIPAddr);
        etName = (EditText)findViewById(R.id.etName);

        btnStream.setOnClickListener(this);

        btnTakePic = (Button) findViewById(R.id.btnTakePic);
        btnChooseGallery = (Button) findViewById(R.id.btnChooseGallery);
        imageView = (ImageView) findViewById(R.id.imageView);
        etUploadImageName = (EditText) findViewById(R.id.etUploadImageName);
        etStreamTitle = (EditText) findViewById(R.id.etStreamTitle);
        etStreamText = (EditText) findViewById(R.id.etStreamText);
        btnTakePic.setOnClickListener(this);
        btnChooseGallery.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStream:
                new Encode_image().execute();
                break;
            case R.id.btnTakePic:
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getFileUri();
                i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
                startActivityForResult(i, 10);
                break;
            case R.id.btnChooseGallery:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
        }
    }

    private void getFileUri() {
        image_name = "temp.jpg";
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + image_name);

        file_uri = Uri.fromFile(file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 10 && resultCode == RESULT_OK){
            String jpgPath = "sdcard/Pictures/temp.jpg";
            imageView.setImageDrawable(Drawable.createFromPath(jpgPath));
        }
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            file = new File(getRealPathFromURI(selectedImage));
            file_uri = Uri.fromFile(file);
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private class Encode_image extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            bitmap = BitmapFactory.decodeFile(file_uri.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            HashMap postData = new HashMap();
            String uploadImageName = etUploadImageName.getText().toString();
            String postTitle = etStreamTitle.getText().toString();
            String postText = etStreamText.getText().toString();
            uploadImageName = uploadImageName + ".jpg";
            String addr = etIPAddr.getText().toString();
            String name = etName.getText().toString();

            postData.put("txtAddr", addr);
            postData.put("txtName", name);
            postData.put("encoded_string", encoded_string);
            postData.put("image_name", uploadImageName);
            postData.put("stream_title", postTitle);
            postData.put("stream_text", postText);
            PostResponseAsyncTask task1 = new PostResponseAsyncTask(RecordSettingsActivity.this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {

                        room_name = s;
                        Intent i = new Intent(RecordSettingsActivity.this, StreamVideoActivity.class);
                        i.putExtra("ipaddr", etIPAddr.getText().toString());
                        i.putExtra("room_name", room_name);
//                    i.putExtra("stream_title", etStreamTitle.getText().toString());
//                    i.putExtra("stream_text", etStreamText.getText().toString());

//                        Log.d(LOG, s);
                        Toast.makeText(RecordSettingsActivity.this, "You are now streaming!", Toast.LENGTH_LONG).show();
                        startActivity(i);


                }
            });
            task1.execute("http://livestreaming-citycal.rhcloud.com/start_stream.php");

        }


    }
}
