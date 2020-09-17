package agarwal.shashwat.ecommerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

public class randomAdapter extends RecyclerView.Adapter<randomAdapter.RecyclerViewHolder> {
    private Random random;

    public randomAdapter(int seed) {
        this.random = new Random(seed);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.horizontal_scroll_item_layout;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.getView().setText(String.valueOf(random.nextInt()));
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView view;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.productName);
        }

        public TextView getView(){
            return view;
        }
    }
}