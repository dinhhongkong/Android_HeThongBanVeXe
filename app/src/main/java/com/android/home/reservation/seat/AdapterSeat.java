package com.android.home.reservation.seat;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.R;

import java.util.List;

public class AdapterSeat extends RecyclerView.Adapter<AdapterSeat.ItemViewHolder> {
    private final List<String> data, listReservedSeat, listChosenSeat;

    public AdapterSeat(List<String> data, List<String> listChosenSeat, List<String> listReservedSeat) {
        this.data = data;
        this.listChosenSeat = listChosenSeat;
        this.listReservedSeat = listReservedSeat;
    }

    public String getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String item = data.get(position);
        TextView tvSeatCode = holder.tvSeatCode;

        tvSeatCode.setText(item);
        if (listReservedSeat.contains(item)) {
            tvSeatCode.setBackgroundResource(R.drawable.bg_reserved);
            tvSeatCode.setTextColor(Color.WHITE);
        } else {
            if (listChosenSeat.contains(item)) {
                tvSeatCode.setBackgroundResource(R.drawable.bg_selected);
                tvSeatCode.setTextColor(Color.parseColor("#EF4444"));
            } else {
                tvSeatCode.setBackgroundResource(R.drawable.bgi_seat);
                tvSeatCode.setTextColor(Color.parseColor("#22C55E"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvSeatCode;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvSeatCode = itemView.findViewById(R.id.SeatCode);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String item = getItem(getAdapterPosition());
            if (listReservedSeat.contains(item)) return;

            if (listChosenSeat.contains(item)) {
                tvSeatCode.setBackgroundResource(R.drawable.bgi_seat);
                tvSeatCode.setTextColor(Color.parseColor("#22C55E"));
                listChosenSeat.remove(item);
            } else {
                if (listChosenSeat.size() == 5) {
                    Toast.makeText(v.getContext(), "TỐI ĐA ĐẶT 5 VÉ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                tvSeatCode.setBackgroundResource(R.drawable.bg_selected);
                tvSeatCode.setTextColor(Color.parseColor("#EF4444"));
                listChosenSeat.add(item);
            }
        }
    }
}
