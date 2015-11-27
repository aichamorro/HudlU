package chamorro.alberto.hudlu.models.realm;

import chamorro.alberto.hudlu.models.MashableNewsItem;

/**
 * Created by alberto.chamorro on 27/11/15.
 */
public class FavoriteAdapter {

    public static Favorite favoriteFromItem(MashableNewsItem item) {
        Favorite favorite = new Favorite();

        favorite.setLink(item.link);
        favorite.setAuthor(item.author);
        favorite.setImage(item.image);
        favorite.setTitle(item.title);

        return favorite;
    }
}
