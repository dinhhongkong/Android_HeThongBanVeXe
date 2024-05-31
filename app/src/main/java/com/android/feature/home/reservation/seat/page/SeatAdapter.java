package com.android.feature.home.reservation.seat.page;

import android.graphics.Color;
import android.util.Log;
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
import java.util.Set;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.VH> {
    private final AsyncListDiffer<Seat> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    public List<Seat> getListOfSeat() {
        return differ.getCurrentList();
    }

    public void submitList(List<Seat> list) {
        differ.submitList(list);
    }

    public SeatAdapter(Type type, Position position, List<String> listDisabledSeat) {
        Log.d("TAG check", "SeatAdapter: " + listDisabledSeat.toString());
        String positionSeat ;
        String seatStyle;
        int seatIndex = 1;
        int hideIndex = 1;

        // init style seats
        if (type == Type.SLEEPING_BERTH) {
            seatStyle = TypeDesignSeat.SLEEPING_BERTH;
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
                String seatName = "";
                if (seatIndex < 10) {
                    seatName = positionSeat + 0 + seatIndex++;
                }
                else {
                    seatName = positionSeat + seatIndex++;
                }

                boolean checkDisabled = false;
                for (int j = 0; j < listDisabledSeat.size(); j++)  {
                    if (seatName.equals(listDisabledSeat.get(j))) {
                        Seats.add( new Seat(seatName,1,true));
                        checkDisabled = true;
                        break;
                    }
                }
                if (!checkDisabled) {
                    Seats.add( new Seat(seatName,0,true));
                }
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
            if(seat.getStatus()==2) {
                holder.binding.tvSeat.setBackgroundResource(R.drawable.seat_selecting);
                holder.binding.tvSeat.setTextColor(Color.parseColor("#EF5222"));
            }
            else if (seat.getStatus()==0) {
                holder.binding.tvSeat.setBackgroundResource(R.drawable.seat_active);
                holder.binding.tvSeat.setTextColor(Color.parseColor("#339AF4"));

            }
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
            else if (seat.getStatus() == 1) {
                binding.tvSeat.setBackgroundResource(R.drawable.seat_disabled);
                binding.tvSeat.setTextColor(Color.parseColor("#A2ABB3"));
                binding.tvSeat.setEnabled(false);
            }
            else if (seat.getStatus() == 2) {
                binding.tvSeat.setBackgroundResource(R.drawable.seat_selecting);
                binding.tvSeat.setTextColor(Color.parseColor("#EF5222"));
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
        SLEEPING_BERTH,
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
    public final static String SLEEPING_BERTH =
            "U_U_U" +
            "U_U_U" +
            "U_U_U" +
            "U_U_U" +
            "U_U_U" +
            "U___U" +
            "UUUUU";


    public final static String LIMOUSINE =
            "U___U" +
            "U_U_U" +
            "U_U_U" +
            "U_U_U" +
            "U_U_U" +
            "U_U_U";


}
