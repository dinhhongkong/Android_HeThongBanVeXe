package com.android.feature.home.reservation.seat.page;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.R;
import com.android.databinding.SeatItemLayoutBinding;
import com.android.model.Seat;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.VH> {
    private final AsyncListDiffer<Seat> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    public SeatAdapter(Type type, Position position) {
        String positionSeat ;
        String seatStyle;
        int seatIndex = 1;
        int hideIndex = 1;

        // init style seats
        if (type == Type.BOTTOM_SLEEPING_BERTH) {
            seatStyle = TypeDesignSeat.BOTTOM_SLEEPING_BERTH;
        }
        else if (type == Type.TOP_SLEEPING_BERTH) {
            seatStyle = TypeDesignSeat.TOP_SLEEPING_BERTH;
        }
        else if (type == Type.LIMOUSINE) {
            seatStyle = TypeDesignSeat.LIMOUSINE;

        }
        else {
            throw new InvalidParameterException();
        }

        // name starting with letter
        if (position == Position.TOP) {
            positionSeat = "B";

        }
        else if (position == Position.BOTTOM) {
            positionSeat = "A";

        }
        else {
            throw new InvalidParameterException();
        }

        List<Seat> Seats = new ArrayList<>();

        for (int i = 0; i < seatStyle.length(); i++) {
            if (seatStyle.charAt(i) == 'U') {
                if (seatIndex == 16 && type == Type.TOP_SLEEPING_BERTH ) {
                    seatIndex++;
                }
                String seatName = positionSeat + seatIndex++;
                Seats.add( new Seat(seatName,0,true));
            }
            else {
                String seatName = "HIDE"+ hideIndex++;
                Seats.add( new Seat(seatName,0,false));
            }
        }
        differ.submitList(Seats);
    }

    private OnItemClickListener itemClick;

    public interface OnItemClickListener {
        void onItemClick(Seat seat);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClick = listener;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SeatItemLayoutBinding binding = SeatItemLayoutBinding.inflate(inflater,parent,false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Seat seat = differ.getCurrentList().get(position);
        holder.onBind(seat);
        holder.binding.tvSeat.setOnClickListener(v->{
            itemClick.onItemClick(seat);
        });


    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    class VH extends RecyclerView.ViewHolder{
        private final SeatItemLayoutBinding binding;
        public VH(@NonNull SeatItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Seat seat) {
            binding.layoutSeat.setVisibility(seat.isVisible() ? View.VISIBLE : View.INVISIBLE);
            binding.tvSeat.setText(seat.getName());
            if(seat.getStatus() == 0) {
                binding.tvSeat.setBackgroundResource(R.drawable.seat_active);
                binding.tvSeat.setTextColor(Color.parseColor("#339AF4"));
            }
            else {
                binding.tvSeat.setBackgroundResource(R.drawable.seat_disabled);
            }

        }
    }

    private static final DiffUtil.ItemCallback<Seat> DIFF_CALLBACK = new DiffUtil.ItemCallback<Seat>() {
        @Override
        public boolean areItemsTheSame(@NonNull Seat oldItem, @NonNull Seat newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Seat oldItem, @NonNull Seat newItem) {
            return oldItem.equals(newItem);
        }
    };

    public enum Type {
        TOP_SLEEPING_BERTH,
        BOTTOM_SLEEPING_BERTH,
        LIMOUSINE
    }

    public enum Position {
        TOP,
        BOTTOM
    }
}

final class TypeDesignSeat {
    private TypeDesignSeat() {

    }
    public final static String BOTTOM_SLEEPING_BERTH =
            "U_U_U" +
            "U_U_U" +
            "U_U_U" +
            "U_U_U" +
            "U_U_U" +
            "U___U" +
            "UUUUU";

    public final static String TOP_SLEEPING_BERTH =
            "U_U_U" +
            "U_U_U" +
            "U_U_U" +
            "U_U_U" +
            "U_U_U" +
            "____U" +
            "UUUUU";


    public final static String LIMOUSINE =
            "U_U" +
            "UUU" +
            "UUU" +
            "UUU" +
            "UUU" +
            "UUU";


}
