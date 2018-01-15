package com.wallpaper.bing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wallpaper.bing.R;
import com.wallpaper.bing.network.bean.CoverStoryKnowledgeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * author GaoPC
 * date 2018-01-11 16:43
 * description
 */
public class StoryKnowledgeAdapter extends RecyclerView.Adapter<StoryKnowledgeAdapter.ViewHolder> {

    private List<CoverStoryKnowledgeEntity> list;
    private Context context;

    public StoryKnowledgeAdapter(List<CoverStoryKnowledgeEntity> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_knowledge, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleText.setText(list.get(position).getKnowledgeTitle());
        holder.subtitleText.setText(list.get(position).getKnowledgeSubtitle());
        holder.contentText.setText(list.get(position).getKnowledgeContent());
        Glide.with(context).load(list.get(position).getKnowledgeSrc()).asBitmap().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText,subtitleText,contentText;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.item_knowledge_image);
            titleText= (TextView) itemView.findViewById(R.id.item_knowledge_title);
            subtitleText= (TextView) itemView.findViewById(R.id.item_knowledge_subtitle);
            contentText= (TextView) itemView.findViewById(R.id.item_knowledge_content);
        }

    }

}