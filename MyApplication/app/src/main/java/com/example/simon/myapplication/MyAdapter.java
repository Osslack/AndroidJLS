package com.example.simon.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by simon on 27.10.15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<DataStruct> mDatastructs;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView1;
        public TextView mTextView2;
        public Button mButton;




        public ViewHolder(final View itemView) {
            super(itemView);


            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "The Item Clicked is: " + ((TextView) view.findViewById(R.id.text_view1)).getText(), Toast.LENGTH_SHORT).show();
                }

            });
            mTextView1 = (TextView) itemView.findViewById(R.id.text_view1);
            mTextView2 = (TextView) itemView.findViewById(R.id.text_view2);
            mButton = (Button) itemView.findViewById(R.id.button);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView) itemView.findViewById(R.id.text_view2);
                    CharSequence text = tv.getText();
                    Toast.makeText(itemView.getContext(),text.toString() , Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<DataStruct> myDatastructs) {
        mDatastructs = myDatastructs;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        DataStruct Struct = mDatastructs.get(position);

        holder.mTextView1.setText(Struct.getTitle());
        holder.mTextView2.setText((Struct.getText()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDatastructs.size();
    }
    public void addItem(DataStruct ds){
        mDatastructs.add(ds);
        this.notifyDataSetChanged();
    }
}
