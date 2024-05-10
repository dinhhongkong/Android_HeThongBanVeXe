package com.android.home.ticket.recently_search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.databinding.ItemRecentlySearchBinding;
import com.android.model.RecentlySearch;

import java.util.ArrayList;
import java.util.List;

public class RecentlySearchAdapter extends RecyclerView.Adapter<RecentlySearchAdapter.ItemViewHolder> {
    private final OnItemListener onItemListener;
    private List<RecentlySearch> data;

    public RecentlySearchAdapter(OnItemListener onItemListener) {
        this.data = new ArrayList<>();
        this.onItemListener = onItemListener;
    }

    public List<RecentlySearch> getData() {
        return data;
    }

//
//    public void setData(List<RecentlySearch> data) {
//        this.data = data;
//        notifyDataSetChanged();
//    }
//
//
//    public void addData(RecentlySearch item) {
//        if (data.contains(item)) return;
//        this.data.add(item);
//        notifyDataSetChanged();
//    }
//
//
//    public void clearData() {
//        this.data.clear();
//        notifyDataSetChanged();
//    }

//    public RecentlySearch getItem(int position) {
//        return data.get(position);
//    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecentlySearchBinding binding = ItemRecentlySearchBinding.inflate(inflater, parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemListener {
        void onItemClickListener(View v, int position);
    }

     static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecentlySearchBinding binding;

        public ItemViewHolder(@NonNull ItemRecentlySearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
