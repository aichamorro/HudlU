package chamorro.alberto.hudlu;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import chamorro.alberto.hudlu.models.MashableNewsItem;
import chamorro.alberto.hudlu.models.realm.FavoriteUtil;

/**
 * Created by alberto.chamorro on 10/11/15.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    public interface MyRecyclerViewInteractionListener {
        void onItemClicked(View view, int position);
    }

    private List<MashableNewsItem> _dataSet;
    private MyRecyclerViewInteractionListener _interactionListener;
    private RequestQueue _requestQueue;
    private Context _context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView _titleTextView;
        TextView _authorTextView;
        ImageView _imageView;
        Button _favoriteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            _titleTextView = (TextView)itemView.findViewById(R.id.item_title);
            _authorTextView = (TextView)itemView.findViewById(R.id.item_author);
            _imageView = (ImageView)itemView.findViewById(R.id.item_image);
            _favoriteButton = (Button)itemView.findViewById(R.id.favorite_button);
        }
    }

    public MyRecyclerViewAdapter(Context context, List<MashableNewsItem> dataSet) {
        _dataSet = dataSet;
        _context = context;
        _interactionListener = (MyRecyclerViewInteractionListener)context;
        _requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_view_2, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder._titleTextView.setText(_dataSet.get(position).title);
        holder._authorTextView.setText(_dataSet.get(position).author);

        final ViewHolder tmpHolder = holder;
        ImageRequest request = new ImageRequest(_dataSet.get(position).image, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                tmpHolder._imageView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.FIT_XY, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MyRecyclerViewAdapter", error.getMessage());
            }
        });
        _requestQueue.add(request);

        holder._favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _interactionListener.onItemClicked(v, position);
            }
        });

        if (FavoriteUtil.isFavorite(_context, _dataSet.get(position))) {
            holder._favoriteButton.setBackgroundColor(_context.getResources().getColor(R.color.colorAccent));
            holder._favoriteButton.setTextColor(_context.getResources().getColor(R.color.white));
        } else {
            holder._favoriteButton.setBackgroundColor(_context.getResources().getColor(R.color.white));
            holder._favoriteButton.setTextColor(_context.getResources().getColor(R.color.black));
        }

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