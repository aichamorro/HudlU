package chamorro.alberto.hudlu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by alberto.chamorro on 10/11/15.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private String[] _dataSet;

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        TextView _myTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            _myTextView = (TextView)itemView.findViewById(R.id.item_my_text);
        }
    }

    public MyRecyclerViewAdapter(Context context, String[] dataSet) {
        _dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder._myTextView.setText(_dataSet[position]);
    }

    @Override
    public int getItemCount() {
        return _dataSet.length;
    }

}