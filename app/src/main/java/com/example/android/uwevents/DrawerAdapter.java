package com.example.android.uwevents;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by Danny on 9/23/2017.
 */


public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    public final static int TYPE_HEADER = 0;
    public final static int TYPE_MENU = 1;

    private ArrayList<DrawerItem> drawerMenuList;

    private OnItemSelecteListener mListener;

    public DrawerAdapter(ArrayList<DrawerItem> drawerMenuList) {
        this.drawerMenuList = drawerMenuList;
    }
    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_HEADER){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drawer_head, parent, false);
        }else{

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_menu_item, parent, false);
        }

        return new DrawerViewHolder(view,viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        }
        return TYPE_MENU;
    }
    class DrawerViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView headerText;
        ImageView icon;

        public DrawerViewHolder(View itemView, int viewType){
            super(itemView);

            if(viewType == 0){
                headerText = (TextView)itemView.findViewById(R.id.headerText);
            }else {
                title = (TextView) itemView.findViewById(R.id.title);
                icon = (ImageView) itemView.findViewById(R.id.icon);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());

                }
            });
        }
    }
    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
       if(position == 0) {
            holder.headerText.setText("Header Text");
        }
        else{
            holder.title.setText(drawerMenuList.get(position - 1).getTitle());
            holder.icon.setImageResource(drawerMenuList.get(position - 1).getIcon());
       }
    }
    @Override
    public int getItemCount() {
        return drawerMenuList.size()+1;
    }

    public void setOnItemClickLister(OnItemSelecteListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemSelecteListener{
        public void onItemSelected(View v, int position);
    }

}