package example.videostreamingtest1;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Post implements Serializable {
    @SerializedName("id")
    public int post_id;

    @SerializedName("room_name")
    public String post_title;

    @SerializedName("name")
    public String post_text;

    @SerializedName("addr")
    public String ipaddr;

    @SerializedName("image_url")
    public String image_url;

    @SerializedName("stream_title")
    public String stream_title;

    @SerializedName("stream_text")
    public String stream_text;

}
