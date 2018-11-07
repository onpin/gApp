package com.app.golfapp.ui.adapter;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.golfapp.R;
import com.app.golfapp.ui.listener.OnItemRecycleClickListener;


/**
 * Created by ysharma on 2/20/2017.
 */
public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.HolderNavigation> {

    private Activity activity;
    private String nav_title[];
    private TypedArray nav_icon;
    int selected_pos = 0;
    private OnItemRecycleClickListener onItemRecycleClickListener;

    public NavigationAdapter(Activity activity, OnItemRecycleClickListener onItemRecycleClickListener) {
        this.activity = activity;
        nav_title = activity.getResources().getStringArray(R.array.nav_title);
       // nav_icon = activity.getResources().obtainTypedArray(R.array.nav_icons);

        this.onItemRecycleClickListener = onItemRecycleClickListener;
    }

    @Override
    public HolderNavigation onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_nav, parent, false);

        HolderNavigation holderNavigation = new HolderNavigation(view);
        return holderNavigation;
    }

    @Override
    public void onBindViewHolder(HolderNavigation holder,final int position) {
        holder.nav_titles.setText(nav_title[position]);
       // holder.nav_icons.setImageResource(nav_icon.getResourceId(position, 0));
        if (position == selected_pos) {
            holder.nav_titles.setPressed(true);
            holder.nav_icons.setImageResource(R.drawable.holefl);
            holder.ll_main.setBackgroundColor(Color.TRANSPARENT);
        } else {
            holder.nav_titles.setPressed(false);
            //holder.nav_icons.setPressed(false);
            holder.nav_icons.setImageResource(R.drawable.settings);
            holder.ll_main.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_pos = position;
                onItemRecycleClickListener.onItemClick(v, position);
                notifyDataSetChanged();
//                ((HomeActivity) activity).onNavItemClick(v, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return nav_title.length;
    }

    public class HolderNavigation extends RecyclerView.ViewHolder {

        public ImageView nav_icons;
        private TextView nav_titles;
        private RelativeLayout ll_main;

        public HolderNavigation(View itemView) {
            super(itemView);
            nav_icons = (ImageView) itemView.findViewById(R.id.nav_icon);
            nav_titles = (TextView) itemView.findViewById(R.id.nav_title);
            ll_main =  (RelativeLayout) itemView.findViewById(R.id.ll_nav);


        }
    }
}
