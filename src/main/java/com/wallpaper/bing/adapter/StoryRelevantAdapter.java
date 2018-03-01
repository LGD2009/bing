package com.wallpaper.bing.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private Context context;

    public StoryRelevantAdapter(List<CoverStoryRelevantEntity> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_relevant, parent, false);
        context= parent.getContext();
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        private TextView indexText,titleText,contentText;

        public ViewHolder(View itemView) {
            super(itemView);
            indexText= (TextView) itemView.findViewById(R.id.item_relevant_index);
            titleText= (TextView) itemView.findViewById(R.id.item_relevant_title);
            contentText= (TextView) itemView.findViewById(R.id.item_relevant_content);

            contentText.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            ClipboardManager clipboardManager =
                    (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (!TextUtils.isEmpty(((TextView)v).getText())){
                String text = ((TextView)v).getText().toString();
                ClipData clipData = ClipData.newPlainText("text", text);
                if (clipboardManager != null) {
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(context,"文本已复制到剪切板", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }
    }

}