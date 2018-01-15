package com.wallpaper.bing.adapter;

import android.animation.ObjectAnimator;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class CoverStoriesAdapter extends RecyclerView.Adapter<CoverStoriesAdapter.ViewHolder> {

    private ArrayList<WallpaperBean> list;
    private ArrayList<Boolean> expendsList;//用来记录展开的cardview的position
    private RequestManager requestManager;

    public CoverStoriesAdapter(RequestManager requestManager, ArrayList<WallpaperBean> list, ArrayList<Boolean> expendsList) {
        this.requestManager = requestManager;
        this.list = list;
        this.expendsList = expendsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cover_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.storyTitleText.setText(list.get(position).getTitle());
        if (expendsList.get(position)) {
            holder.switchLayout(true, list.get(position).getCoverTitle());
        } else {
            holder.switchLayout(false, list.get(position).getCoverAttribute());
        }
        String url = BingUrl.BASE_IMAGE_URL + list.get(position).getImageUrl()+"?x-oss-process=style/640x360";
        requestManager.load(url).asBitmap().into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private ConstraintLayout storyLayout;
        private ConstraintLayout constraintRootLayout;
        private CardView rootLayout;
        private ImageView switchImage;
        private TextView storyTitleText;
        private TextView storyDescriptionText;

        public ViewHolder(View itemView) {
            super(itemView);
            constraintRootLayout = (ConstraintLayout) itemView.findViewById(R.id.item_wallpaper_constraint_root);
            imageView = (ImageView) itemView.findViewById(R.id.item_wallpaper_image);
            storyLayout = (ConstraintLayout) itemView.findViewById(R.id.item_wallpaper_story);
            rootLayout = (CardView) itemView.findViewById(R.id.item_wallpaper_root);
            switchImage = (ImageView) itemView.findViewById(R.id.item_wallpaper_story_switch);
            storyTitleText = (TextView) itemView.findViewById(R.id.item_wallpaper_story_title);
            storyDescriptionText = (TextView) itemView.findViewById(R.id.item_wallpaper_story_description);

            switchImage.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_wallpaper_story_switch:
                    int position = getLayoutPosition();
                    if (expendsList.get(position)) {
                        switchLayout(false, list.get(position).getCoverAttribute());
                        expendsList.set(position,false);
                    } else {
                        switchLayout(true, list.get(position).getCoverTitle());
                        expendsList.set(position,true);
                    }
                    break;
                case R.id.item_wallpaper_image:
                    onItemClickListener.itemClickListener(getLayoutPosition());
                    break;
            }
        }

        private void switchLayout(boolean isExpend, String description) {
            ConstraintSet constraintSet = new ConstraintSet();
            TransitionManager.beginDelayedTransition(constraintRootLayout);
            if (isExpend) {
                if (switchImage.getRotation() != 180) {
                    ObjectAnimator.ofFloat(switchImage, "rotation", 0f, 180f).setDuration(500).start();
                }
                constraintSet.connect(R.id.item_wallpaper_story, ConstraintSet.TOP, R.id.item_wallpaper_constraint_root, ConstraintSet.TOP);
            } else {
                if (switchImage.getRotation() != 0) {
                    ObjectAnimator.ofFloat(switchImage, "rotation", 180f, 0f).setDuration(500).start();
                }
                constraintSet.connect(R.id.item_wallpaper_story, ConstraintSet.TOP, R.id.item_wallpaper_image, ConstraintSet.BOTTOM);
            }
            constraintSet.connect(R.id.item_wallpaper_story, ConstraintSet.LEFT, R.id.item_wallpaper_constraint_root, ConstraintSet.LEFT);
            constraintSet.connect(R.id.item_wallpaper_story, ConstraintSet.RIGHT, R.id.item_wallpaper_constraint_root, ConstraintSet.RIGHT);
            constraintSet.connect(R.id.item_wallpaper_story, ConstraintSet.BOTTOM, R.id.item_wallpaper_line, ConstraintSet.TOP);
            constraintSet.applyTo(constraintRootLayout);

            storyDescriptionText.setText(description);
        }

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

}