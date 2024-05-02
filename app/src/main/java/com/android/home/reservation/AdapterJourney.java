package com.android.home.reservation;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.R;
import com.android.databinding.ItemJourneyBinding;
import com.android.model.response.JourneyResponse;

import java.util.ArrayList;
import java.util.List;

public class AdapterJourney extends RecyclerView.Adapter<AdapterJourney.ItemViewHolder> {
    private final OnItemListener onItemListener;
    private List<JourneyResponse> data;

    public AdapterJourney(OnItemListener onItemListener) {
        this.data = new ArrayList<>();
        this.onItemListener = onItemListener;
    }

    public List<JourneyResponse> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<JourneyResponse> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public JourneyResponse getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemJourneyBinding mItemJourneyBinding = DataBindingUtil
                .inflate(inflater, R.layout.item_journey, parent, false);
        return new ItemViewHolder(mItemJourneyBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        JourneyResponse item = getItem(position);
        holder.binding.setJourneyResponse(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemListener {
        void onItemClick(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemJourneyBinding binding;

        public ItemViewHolder(@NonNull ItemJourneyBinding mItemJourneyBinding) {
            super(mItemJourneyBinding.getRoot());

            this.binding = mItemJourneyBinding;
            mItemJourneyBinding.getRoot().setOnClickListener(v ->
                    onItemListener.onItemClick(v, getAdapterPosition()));
        }
    }
}
