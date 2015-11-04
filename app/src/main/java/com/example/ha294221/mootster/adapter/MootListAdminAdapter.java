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

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.ha294221.mootster.R;
import com.example.ha294221.mootster.model.MootListItem;
import com.example.ha294221.mootster.util.Fx;

import java.util.Collections;
import java.util.List;

/**
  * Created by Haroon on 10/14/2015.
 */
public class MootListAdminAdapter extends RecyclerView.Adapter<MootListAdminAdapter.MootListViewHolder> {

    private LayoutInflater layoutInflater;
    private RecyclerView parentRecyclerView;
    private List<MootListItem> mootList= Collections.EMPTY_LIST;
    private int expandedPos;
    private Context ctx;

    public MootListAdminAdapter(Context context,List<MootListItem> data,int pos) {
        layoutInflater=LayoutInflater.from(context);
        mootList=data;
        ctx=context;
        expandedPos=pos;
    }

    @Override
    public MootListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.e("onCreateViewHolder", String.valueOf(i));
        View view=layoutInflater.inflate(R.layout.custom_moot_list_item,viewGroup,false);
        MootListViewHolder viewHolder=new MootListViewHolder(view);
        parentRecyclerView=(RecyclerView)viewGroup;
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final MootListViewHolder mootListViewHolder, final int i) {
        ColorGenerator generator = ColorGenerator.MATERIAL;
        Log.e("onBindViewHolder",String.valueOf(i));

        // Setting data from passed list to the ViewHolder elements
        mootListViewHolder.mootDateInList.setText(mootList.get(i).getMootDate());
        mootListViewHolder.mootTitleInList.setText(mootList.get(i).getMootTitle());
        mootListViewHolder.venue.setText(mootList.get(i).getVenue());

        // Rounded First Letter
        final String letter = String.valueOf(mootListViewHolder.mootTitleInList.getText().charAt(0));
        TextDrawable drawable = TextDrawable.builder().buildRound(letter, generator.getRandomColor());
        mootListViewHolder.itemLetter.setImageDrawable(drawable);

        // Handling orientation changes (Setting the visibility if already in clicked state, on changing orientation)
        if(mootListViewHolder.getAdapterPosition()==expandedPos){
            mootListViewHolder.collapsibleMootOptions.setVisibility(View.VISIBLE);
        }else {
            mootListViewHolder.collapsibleMootOptions.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mootList.size();
    }

    public int getExpandedPos() {
        return expandedPos;
    }


    public class MootListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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

            mootTitleInList.setOnClickListener(this);
            mootDateInList.setOnClickListener(this);
            collapsibleMootOptions.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // Toggle the views such that only one view is expanded at a time

            MootListViewHolder last;
            if (expandedPos != -1) {
                last= (MootListViewHolder) parentRecyclerView.findViewHolderForAdapterPosition(expandedPos);

                if(null!=last) {
                    if (collapsibleMootOptions.isShown()) {
                        Fx.slide_up(ctx, collapsibleMootOptions);
                        collapsibleMootOptions.setVisibility(View.GONE);
                        expandedPos = -1;
                    } else {
                        if( last.collapsibleMootOptions.isShown()) {
                            Fx.slide_up(ctx, last.collapsibleMootOptions);
                            last.collapsibleMootOptions.setVisibility(View.GONE);
                        }
                        collapsibleMootOptions.setVisibility(View.VISIBLE);
                        Fx.slide_down(ctx, collapsibleMootOptions);
                        expandedPos = getAdapterPosition();
                    }
                }else{
                    if(!collapsibleMootOptions.isShown()) {
                        collapsibleMootOptions.setVisibility(View.VISIBLE);
                        Fx.slide_down(ctx, collapsibleMootOptions);
                        expandedPos = getAdapterPosition();
                    }
                }
            }
            else{
                if(!collapsibleMootOptions.isShown()) {
                    collapsibleMootOptions.setVisibility(View.VISIBLE);
                    Fx.slide_down(ctx, collapsibleMootOptions);
                    expandedPos = getAdapterPosition();
                }
            }
        }
    }


}
