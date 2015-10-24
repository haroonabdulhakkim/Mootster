package com.example.ha294221.mootster.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.ha294221.mootster.R;
import com.example.ha294221.mootster.model.MootListItem;
import com.example.ha294221.mootster.util.Fx;

import java.util.Collections;
import java.util.List;
import java.util.zip.CheckedOutputStream;

/**
 * Created by HA294221 on 10/14/2015.
 */
public class MootListAdminAdapter extends RecyclerView.Adapter<MootListAdminAdapter.MootListViewHolder> {

    private LayoutInflater layoutInflater;
    private ViewGroup parentRecyclerView;
    private List<MootListItem> mootList= Collections.EMPTY_LIST;
    private int currentPos=-1;
    private Context ctx;
    public MootListAdminAdapter(Context context,List<MootListItem> data) {
        layoutInflater=LayoutInflater.from(context);
        mootList=data;
        ctx=context;
    }

    @Override
    public MootListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view=layoutInflater.inflate(R.layout.custom_moot_list_item,viewGroup,false);
        MootListViewHolder viewHolder=new MootListViewHolder(view);
        parentRecyclerView=viewGroup;
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final MootListViewHolder mootListViewHolder, final int i) {
        ColorGenerator generator = ColorGenerator.MATERIAL;

        mootListViewHolder.mootDateInList.setText(mootList.get(i).getMootDate());
        mootListViewHolder.mootTitleInList.setText(mootList.get(i).getMootTitle());
        mootListViewHolder.venue.setText(mootList.get(i).getVenue());
        String letter = String.valueOf(mootListViewHolder.mootTitleInList.getText().charAt(0));
        TextDrawable drawable = TextDrawable.builder().buildRound(letter, generator.getRandomColor());
        mootListViewHolder.itemLetter.setImageDrawable(drawable);
        if(mootList.get(i).isExpanded()) {
            mootListViewHolder.collapsibleMootOptions.setVisibility(View.VISIBLE);
        }else {
            mootListViewHolder.collapsibleMootOptions.setVisibility(View.GONE);
        }
        mootListViewHolder.mootTitleInList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*View last;
                if (currentPos != -1) {

                    last = parentRecyclerView.getChildAt(currentPos);

                    if(null!=last) {
                        last.findViewById(R.id.collapsible_moot_options).setVisibility(View.GONE);
                    }

                }else{
                }*/
                if(mootListViewHolder.collapsibleMootOptions.isShown()){
                    Fx.slide_up(ctx,mootListViewHolder.collapsibleMootOptions);
                    mootListViewHolder.collapsibleMootOptions.setVisibility(View.GONE);
                    mootList.get(i).setExpanded(false);
                }else{
                    mootListViewHolder.collapsibleMootOptions.setVisibility(View.VISIBLE);
                    Fx.slide_down(ctx, mootListViewHolder.collapsibleMootOptions);
                    mootList.get(i).setExpanded(true);
                }
                currentPos = i;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mootList.size();
    }

    static class MootListViewHolder extends RecyclerView.ViewHolder{
        private ImageView itemLetter;
        private TextView mootDateInList;
        private TextView mootTitleInList;
        private TextView venue;
        private RelativeLayout collapsibleMootOptions;

        public MootListViewHolder(View itemView) {
            super(itemView);
            itemLetter=(ImageView)itemView.findViewById(R.id.item_letter);
            mootDateInList=(TextView)itemView.findViewById(R.id.moot_date_in_list);
            mootTitleInList=(TextView)itemView.findViewById(R.id.moot_title_in_list);
            collapsibleMootOptions=(RelativeLayout)itemView.findViewById(R.id.collapsible_moot_options);
            venue=(TextView)itemView.findViewById(R.id.venue);
        }
    }
}
