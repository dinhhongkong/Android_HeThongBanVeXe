package com.android.feature.home.reservation.journey.page;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.databinding.JourneyItemLayoutBinding;
import com.android.model.response.JourneyResponse;
import com.android.utils.NumberUtils;

import java.util.List;

public class JourneyAdapter extends RecyclerView.Adapter<JourneyAdapter.VH> {

    private final AsyncListDiffer<JourneyResponse> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);
    private OnItemClickListener itemClick;



    // Method
    public void submitList(List<JourneyResponse> newList) {
        differ.submitList(newList);
    }

    public List<JourneyResponse> getList() {
        return differ.getCurrentList();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClick = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(JourneyResponse journey);
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        JourneyItemLayoutBinding binding = JourneyItemLayoutBinding.inflate(inflater,parent,false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        JourneyResponse journey = differ.getCurrentList().get(position);
        holder.onBind(journey);
        holder.binding.cardView.setOnClickListener(view -> {
            itemClick.onItemClick(journey);
        });

    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private final JourneyItemLayoutBinding binding;
        public VH(JourneyItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(JourneyResponse journey) {
            binding.tvDepartureTime.setText(journey.getStartTime());
//            binding.tvDestinationTime.setText(journey.getE);
            binding.tvHours.setText(String.valueOf(journey.getEstimatedTime()));

            binding.tvDeparture.setText(journey.getDepartureProvince());
            binding.tvDestination.setText(journey.getDestProvince());
            binding.tvSeatType.setText(journey.getSeatType());
//            binding.tvEmptySeat.setText();
            binding.tvPrice.setText(NumberUtils.format(journey.getPrice()) +"đ");


        }
    }

    private static final DiffUtil.ItemCallback<JourneyResponse> DIFF_CALLBACK = new DiffUtil.ItemCallback<JourneyResponse>() {
        @Override
        public boolean areItemsTheSame(@NonNull JourneyResponse oldItem, @NonNull JourneyResponse newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull JourneyResponse oldItem, @NonNull JourneyResponse newItem) {
            return oldItem.equals(newItem);
        }
    };
}
