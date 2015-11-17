package chamorro.alberto.hudlu.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alberto.chamorro on 17/11/15.
 */
public class MashableNews {
    @SerializedName("new")
    List<MashableNewsItem> newsItems;
}
