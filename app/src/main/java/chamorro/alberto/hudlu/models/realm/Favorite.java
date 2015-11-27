package chamorro.alberto.hudlu.models.realm;

import chamorro.alberto.hudlu.models.MashableItem;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by alberto.chamorro on 27/11/15.
 */
public class Favorite extends RealmObject implements MashableItem {
    @PrimaryKey
    private String link;

    @Required
    private String title;
    private String author;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
