package com.example.simon.photojsl;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by simon on 27.10.15.
 */
public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {
    private ArrayList<ListEntry> mListEntries;
    public ViewAdapter(ArrayList<ListEntry> ListEntries){
        this.mListEntries = ListEntries;
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
                    Toast.makeText(itemView.getContext(), "The Item Clicked is: " + ((TextView) v.findViewById(R.id.textView2)).getText(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
