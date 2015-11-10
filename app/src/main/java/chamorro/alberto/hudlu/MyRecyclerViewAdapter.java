package chamorro.alberto.hudlu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by alberto.chamorro on 10/11/15.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private String[] _dataSet;

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        
        public ViewHolder(TextView itemView) {
            super(itemView);
        }
    }

    public MyRecyclerViewAdapter(Context context, String[] dataSet) {
        _dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}