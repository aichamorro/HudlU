package chamorro.alberto.hudlu.models.realm;

import android.content.Context;

import chamorro.alberto.hudlu.models.MashableItem;
import chamorro.alberto.hudlu.models.MashableNewsItem;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by alberto.chamorro on 27/11/15.
 */
public class FavoriteUtil {
    public static void addFavorite(Context context, MashableNewsItem newsItem) {
        Favorite favorite = FavoriteAdapter.favoriteFromItem(newsItem);

        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.copyToRealm(favorite);
        realm.commitTransaction();
    }

    public static void removeFavorite(Context context, MashableItem newsItem) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.where(Favorite.class).equalTo("link", newsItem.getLink()).findFirst().removeFromRealm();
        realm.commitTransaction();
    }

    public static boolean isFavorite(Context context, MashableItem newsItem) {
        if (newsItem instanceof Favorite) {
            return true;
        }

        Realm realm = Realm.getInstance(context);

        return realm.where(Favorite.class).equalTo("link", newsItem.getLink()).count() > 0;
    }

    public static RealmResults<Favorite> getAllFavorites(Context context) {
        Realm realm = Realm.getInstance(context);

        return realm.where(Favorite.class).findAll();
    }
}
