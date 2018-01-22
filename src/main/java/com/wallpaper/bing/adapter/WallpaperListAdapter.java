package com.wallpaper.bing.adapter;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.wallpaper.bing.R;
import com.wallpaper.bing.listener.OnItemClickListener;
import com.wallpaper.bing.network.BingUrl;
import com.wallpaper.bing.network.bean.WallpaperBean;

import java.util.ArrayList;

/**
 * author GaoPC
 * date 2017-12-12 19:42
 * description
 */
public class WallpaperListAdapter extends RecyclerView.Adapter<WallpaperListAdapter.ViewHolder> {
    private static final int ANIMATED_ITEMS_COUNT = 2;
    private int lastAnimatedPosition = -1;

    private ArrayList<WallpaperBean> list;
    private RequestManager requestManager;

    public WallpaperListAdapter(RequestManager requestManager, ArrayList<WallpaperBean> list) {
        this.requestManager = requestManager;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallpaper_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        runEnterAnimation(holder.itemView, position);

        holder.storyTitleText.setText(list.get(position).getTitle());
        holder.storyAttributeText.setText(list.get(position).getCoverAttribute());
        holder.storyDateText.setText(list.get(position).getDate());

        String imageUrl;
        if (TextUtils.isEmpty(list.get(position).getImageUrl())){
            imageUrl=list.get(position).getThumbnail();
        }else {
            imageUrl = BingUrl.BASE_IMAGE_URL + list.get(position).getImageUrl()+"?x-oss-process=style/640x360";
        }
        requestManager.load(imageUrl).crossFade().into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView storyTitleText;
        private TextView storyAttributeText;
        private TextView storyDateText;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_wallpaper_list_image);
            storyTitleText = (TextView) itemView.findViewById(R.id.item_wallpaper_list_title);
            storyAttributeText = (TextView) itemView.findViewById(R.id.item_wallpaper_list_attribute);
            storyDateText= (TextView) itemView.findViewById(R.id.item_wallpaper_list_date);

            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_wallpaper_list_image:
                    onItemClickListener.itemClickListener(getLayoutPosition());
                    break;
            }
        }


    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(view.getY());
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

}