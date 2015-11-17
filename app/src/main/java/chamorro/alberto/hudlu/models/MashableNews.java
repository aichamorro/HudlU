package chamorro.alberto.hudlu.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alberto.chamorro on 17/11/15.
 */
@SerializedName("new")
public class MashableNews {
    List<MashableNewsItem> newsItems;
}
