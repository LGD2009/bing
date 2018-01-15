package com.wallpaper.bing.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wallpaper.bing.R;
import com.wallpaper.bing.network.bean.CoverStoryRelevantEntity;

import java.util.List;

/**
 * author GaoPC
 * date 2018-01-10 20:05
 * description
 */
public class StoryRelevantAdapter extends RecyclerView.Adapter<StoryRelevantAdapter.ViewHolder> {

    private List<CoverStoryRelevantEntity> list;

    public StoryRelevantAdapter(List<CoverStoryRelevantEntity> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_relevant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.indexText.setText(String.valueOf(list.get(position).getRelevantIndex()));
        holder.titleText.setText(list.get(position).getRelevantTitle());
        holder.contentText.setText(list.get(position).getRelevantContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView indexText,titleText,contentText;

        public ViewHolder(View itemView) {
            super(itemView);
            indexText= (TextView) itemView.findViewById(R.id.item_relevant_index);
            titleText= (TextView) itemView.findViewById(R.id.item_relevant_title);
            contentText= (TextView) itemView.findViewById(R.id.item_relevant_content);
        }

    }

}