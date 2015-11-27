package chamorro.alberto.hudlu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import chamorro.alberto.hudlu.models.MashableItem;
import chamorro.alberto.hudlu.models.MashableNews;
import chamorro.alberto.hudlu.models.MashableNewsItem;
import chamorro.alberto.hudlu.models.realm.Favorite;
import chamorro.alberto.hudlu.models.realm.FavoriteUtil;
import io.realm.RealmResults;

public class FavoritesActivity extends AppCompatActivity implements MyRecyclerViewAdapter.MyRecyclerViewInteractionListener {
    private RecyclerView _recyclerView;
    private RecyclerView.Adapter _recyclerViewAdapter;
    private RecyclerView.LayoutManager _recyclerViewLayoutManager;
    private List<MashableItem> _dataSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _dataSet = fetchListOfFavorites();
        _recyclerView = (RecyclerView) findViewById(R.id.favorites_recyclerView);
        _recyclerView.setHasFixedSize(true);

        _recyclerViewLayoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(_recyclerViewLayoutManager);

        _recyclerViewAdapter = new MyRecyclerViewAdapter(this, _dataSet);
        _recyclerView.setAdapter(_recyclerViewAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private List<MashableItem> fetchListOfFavorites() {
        RealmResults<Favorite> allFavorites = FavoriteUtil.getAllFavorites(this);

        ArrayList<MashableItem> result = new ArrayList<>();
        for (int i=0; i<allFavorites.size(); i++) {
            result.add(i, allFavorites.get(i));
        }

        return result;
    }

    @Override
    public void onItemClicked(View view, int position) {
        if (view.getId() == R.id.favorite_button) {
            MashableItem item = _dataSet.get(position);

            FavoriteUtil.removeFavorite(this, item);
            _dataSet.remove(position);
            _recyclerViewAdapter.notifyDataSetChanged();
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(_dataSet.get(position).getLink()));

            startActivity(intent);
        }
    }


}
