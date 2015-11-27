package chamorro.alberto.hudlu;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import chamorro.alberto.hudlu.models.MashableItem;
import chamorro.alberto.hudlu.models.MashableNews;
import chamorro.alberto.hudlu.models.MashableNewsItem;
import chamorro.alberto.hudlu.models.realm.Favorite;
import chamorro.alberto.hudlu.models.realm.FavoriteUtil;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.MyRecyclerViewInteractionListener {
    private RecyclerView _recyclerView;
    private RecyclerView.Adapter _recyclerViewAdapter;
    private RecyclerView.LayoutManager _recyclerViewLayoutManager;
    private List<MashableItem> _dataSet = new ArrayList<>();
    private String _mashableUrlString = "http://mashable.com/stories.json?hot_per_page=0&new_per_page=5&rising_per_page=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        _recyclerView.setHasFixedSize(true);

        _recyclerViewLayoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(_recyclerViewLayoutManager);

        _recyclerViewAdapter = new MyRecyclerViewAdapter(this, _dataSet);
        _recyclerView.setAdapter(_recyclerViewAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fetchLatestNews();
    }

    @Override
    protected void onStart() {
        super.onStart();

        showWelcomeDialogIfFirstRun();
    }

    private void showWelcomeDialogIfFirstRun() {
        SharedPreferences preferences = getSharedPreferences(ApplicationContext.Configuration.SharedPreferencesName, Context.MODE_PRIVATE);
        if (!preferences.contains(ApplicationContext.Configuration.IsFirstRunKey)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(ApplicationContext.Configuration.IsFirstRunKey, new Boolean(true).toString());
            editor.apply();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(R.string.welcome_to_hudlu_application)
                    .setTitle(R.string.welcome_to_hudlu_application_title)
                    .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d("HudlU", "Favorites menu item clicked");
            Intent intent = new Intent(this, FavoritesActivity.class);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(View view, int position) {
        if (view.getId() == R.id.favorite_button) {
            MashableNewsItem item = (MashableNewsItem) _dataSet.get(position);

            if (!FavoriteUtil.isFavorite(this, item)) {
                FavoriteUtil.addFavorite(this, item);
            } else {
                FavoriteUtil.removeFavorite(this, item);
            }

            _recyclerViewAdapter.notifyDataSetChanged();
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(_dataSet.get(position).getLink()));

            startActivity(intent);
        }
    }

    void fetchLatestNews() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final Context context = this;
        if (connectivityManager.getActiveNetworkInfo().isConnected()) {
            Toast.makeText(this, R.string.info_fetching_news, Toast.LENGTH_SHORT).show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(
                    _mashableUrlString, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    MashableNews mashableNews = new Gson().fromJson(response, MashableNews.class);
                    _dataSet.addAll(mashableNews.newsItems);
                    _recyclerViewAdapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, R.string.error_fetching_news + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", error.getMessage());
                }
            });
            requestQueue.add(request);
        } else {
            Toast.makeText(this, R.string.no_connection_when_fetching_news, Toast.LENGTH_SHORT).show();
        }
    }
}
