package chamorro.alberto.hudlu;

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

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.MyRecyclerViewInteractionListener {
    private RecyclerView _recyclerView;
    private RecyclerView.Adapter _recyclerViewAdapter;
    private RecyclerView.LayoutManager _recyclerViewLayoutManager;
    private final String[] _dataSet = new String[]{
            "Adam Gucwa", "Alberto Chamorro", "Chanse Strode", "Craig Zheng",
            "David Bohner", "Eric Clymer", "Jessica Hoffman", "Jon Evans",
            "Jordan Degner", "Mitchel Pigsley", "Peter Yasi",
            "Seth Prauner", "Sue Yi", "Zach Ramaekers", "Mike Isman", "Josh Cox"
    };
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
        Snackbar.make(view, _dataSet[position], Snackbar.LENGTH_SHORT).show();
    }
}
