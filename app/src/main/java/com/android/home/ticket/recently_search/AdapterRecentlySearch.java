package com.android.home.ticket.recently_search;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.R;
import com.android.databinding.ItemRecentlySearchBinding;
import com.android.response.RecentlySearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecentlySearch extends RecyclerView.Adapter<AdapterRecentlySearch.ItemViewHolder> {
    private final OnItemListener onItemListener;
    private List<RecentlySearchViewModel> data;

    public AdapterRecentlySearch(OnItemListener onItemListener) {
        this.data = new ArrayList<>();
        this.onItemListener = onItemListener;
    }

    public List<RecentlySearchViewModel> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<RecentlySearchViewModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(RecentlySearchViewModel item) {
        if (data.contains(item)) return;
        this.data.add(item);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearData() {
        this.data.clear();
        notifyDataSetChanged();
    }

    public RecentlySearchViewModel getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecentlySearchBinding mItemRecentlySearchBinding = DataBindingUtil
                .inflate(inflater, R.layout.item_recently_search, parent, false);
        return new ItemViewHolder(mItemRecentlySearchBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        RecentlySearchViewModel item = getItem(position);
        holder.binding.setRecentlySearchViewModel(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemListener {
        void onItemClickListener(View v, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecentlySearchBinding binding;

        public ItemViewHolder(@NonNull ItemRecentlySearchBinding mItemRecentlySearchBinding) {
            super(mItemRecentlySearchBinding.getRoot());

            this.binding = mItemRecentlySearchBinding;
            mItemRecentlySearchBinding.getRoot().setOnClickListener(v ->
                    onItemListener.onItemClickListener(v, getAdapterPosition()));
        }
    }
}
