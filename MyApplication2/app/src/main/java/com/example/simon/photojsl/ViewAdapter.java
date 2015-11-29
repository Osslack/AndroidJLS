package com.example.simon.photojsl;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by simon on 27.10.15.
 */
public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {
    private ArrayList<ListEntry> mListEntries;
    public ViewAdapter(ArrayList<ListEntry> ListEntries){
        this.mListEntries = ListEntries;
    }
    static final int RESULT_VIEW_IMAGE = 3;
    public ListEntry getEntryByTitle (String title){
        for(ListEntry LE : mListEntries){
            if(LE.getTitle() == title){
                return LE;
            }
        }
        return null;
    }
    public void addData(ListEntry listEntry){
        this.mListEntries.add(0,listEntry);
        notifyDataSetChanged();
    }

    @Override
    public ViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_entry, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public boolean deleteListEntry(String name){
        boolean removed = false;
        ListEntry toDelete = null;
        for(ListEntry e: mListEntries){
            if(e.getTitle().equals(name)){
                toDelete = e;
                removed = true;
            }
        }
        if(removed && toDelete != null){
            mListEntries.remove(toDelete);
            notifyDataSetChanged();}
        return removed;
    }



    @Override
    public void onBindViewHolder(ViewAdapter.ViewHolder holder, int position) {
        ListEntry lEntry = mListEntries.get(position);
        holder.mTextView1.setText(lEntry.getTitle());
        holder.mTextView2.setText((lEntry.getDate()));
        holder.mImageView1.setImageBitmap(lEntry.getImage());

    }

    @Override
    public int getItemCount() {
        if(mListEntries != null) {
            return mListEntries.size();
        }
        else return 0;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mImageView1;
        public ViewHolder(final View itemView){
            super(itemView);
            mTextView1 = (TextView) itemView.findViewById(R.id.textView2);
            mTextView2 = (TextView) itemView.findViewById(R.id.textView3);
            mImageView1 = (ImageView) itemView.findViewById(R.id.imageView3);
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    //TODO: Call Detail-View -> Create Detail-View first
                    Intent detail_view = new Intent(v.getContext(), DetailView.class);
                    detail_view.putExtra("Filename", mTextView1.getText());
//                    v.getContext().startActivity(detail_view);
                    ((Activity)v.getContext()).startActivityForResult(detail_view, RESULT_VIEW_IMAGE);
                    //Toast.makeText(itemView.getContext(), "The Item Clicked is: " + ((TextView) v.findViewById(R.id.textView2)).getText(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
