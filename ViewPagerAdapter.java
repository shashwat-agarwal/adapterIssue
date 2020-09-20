package agarwal.shashwat.ecommerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder> {

    private List<SlideItem> slideItems;
    private ViewPager2 viewPager2;

     ViewPagerAdapter(List<SlideItem> slideItems, ViewPager2 viewPager2) {
        this.slideItems = slideItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewPagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewPagerHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container,parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerHolder holder, int position) {
           holder.setImage(slideItems.get(position));
           if (position==slideItems.size()-2){
               viewPager2.post(runnable);
           }
    }

    @Override
    public int getItemCount() {
        return slideItems.size();
    }

    class ViewPagerHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;

         ViewPagerHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageSlide);
        }

        void setImage(SlideItem slideItem){

            Picasso.get()
                    .load(slideItem.getImage())
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }
    }

    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            slideItems.addAll(slideItems);
            notifyDataSetChanged();
        }
    };

}
