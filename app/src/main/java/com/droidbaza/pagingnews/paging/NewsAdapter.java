package com.droidbaza.pagingnews.paging;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.droidbaza.pagingnews.R;
import com.droidbaza.pagingnews.pojo.Article;

import static com.droidbaza.pagingnews.pojo.Article.DIFF_CALLBACK;

public class NewsAdapter extends PagedListAdapter<Article,NewsAdapter.NewsHolder> {

    private Context context;

    public NewsAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {

        Article item = getItem(position);
        assert item != null;
        holder.title.setText(item.getTitle());
        holder.timestamp.setText(item.getPublishedAt());
        holder.description.setText(item.getDescription());
        Glide.with(context)
                .load(item.getUrlToImage())
                .error(R.drawable.ic_filter_hdr_black_24dp)
                .into(holder.image);
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(item.getUrl()));
            context.startActivity(i);
        });
    }


    class NewsHolder extends RecyclerView.ViewHolder {
        private TextView title,description,timestamp;
        private ImageView image;

        private NewsHolder(View v) {
            super(v);
            title = v.findViewById(R.id.news_title);
            description = v.findViewById(R.id.news_description);
            timestamp = v.findViewById(R.id.news_timestamp);
            image = v.findViewById(R.id.news_image);
        }

    }
}

