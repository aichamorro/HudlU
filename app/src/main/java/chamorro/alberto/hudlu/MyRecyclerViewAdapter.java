package chamorro.alberto.hudlu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chamorro.alberto.hudlu.models.MashableNewsItem;

/**
 * Created by alberto.chamorro on 10/11/15.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    public interface MyRecyclerViewInteractionListener {
        void onItemClicked(View view, int position);
    }

    private List<MashableNewsItem> _dataSet;
    private MyRecyclerViewInteractionListener _interactionListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView _myTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            _myTextView = (TextView)itemView.findViewById(R.id.item_my_text);
        }
    }

    public MyRecyclerViewAdapter(Context context, List<MashableNewsItem> dataSet) {
        _dataSet = dataSet;
        _interactionListener = (MyRecyclerViewInteractionListener)context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder._myTextView.setText(_dataSet.get(position).title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _interactionListener.onItemClicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _dataSet.size();
    }

}