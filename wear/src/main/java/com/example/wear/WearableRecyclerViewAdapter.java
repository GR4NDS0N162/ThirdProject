package com.example.wear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableRecyclerView;

import com.example.wear.data.Recipe;

import java.util.List;

public class WearableRecyclerViewAdapter extends WearableRecyclerView.Adapter<WearableRecyclerViewAdapter.MyViewHolder> {
    private static List<Recipe> mDataset;     // Список городов для RecyclerView
    private Context context;                // Контекст

    // Внутренний класс, предназначенный для хранения информации о View-элементах списка
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewName;

        public MyViewHolder(View v) {
            super(v);

            mTextViewName = v.findViewById(R.id.textViewName);
        }
    }

    public WearableRecyclerViewAdapter(List<Recipe> myDataset) { mDataset = myDataset; }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wearable_recycler_view_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        context = parent.getContext();
        return  vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextViewName.setText(mDataset.get(position).Name);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
