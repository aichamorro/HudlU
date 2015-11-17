package chamorro.alberto.hudlu;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import chamorro.alberto.hudlu.models.MashableNews;
import chamorro.alberto.hudlu.models.MashableNewsItem;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.MyRecyclerViewInteractionListener {
    private RecyclerView _recyclerView;
    private RecyclerView.Adapter _recyclerViewAdapter;
    private RecyclerView.LayoutManager _recyclerViewLayoutManager;
    private List<MashableNewsItem> _dataSet = new ArrayList<>();

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
            Log.d("HudlU", "Settings menu item clicked");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(View view, int position) {
        Snackbar.make(view, _dataSet.get(position).author, Snackbar.LENGTH_SHORT).show();
    }

    void fetchLatestNews() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final Context context = this;
        if (connectivityManager.getActiveNetworkInfo().isConnected()) {
            Toast.makeText(this, "Fetching latest news", 5).show();

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(
                    "http://mashable.com/stories.json?hot_per_page=0&new_per_page=5&rising_per_page=0", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // TODO Parse the response
                    MashableNews mashableNews = new Gson().fromJson(response, MashableNews.class);
                    _dataSet.addAll(mashableNews.newsItems);
                    _recyclerViewAdapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "An error occurred when trying to fetch the latest news: " + error.getLocalizedMessage(), 5);
                    Log.e("MainActivity", error.getMessage());
                }
            });
            requestQueue.add(request);
        } else {
            Toast.makeText(this, "We couldn't connect to the server to fetch the latest news. Please check your internet connection and try again.", 5).show();
        }
    }
}
