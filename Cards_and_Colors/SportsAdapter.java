package com.example.cards_and_colors;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import org.w3c.dom.Text;
import java.util.ArrayList;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Sport> sportsData;

    public SportsAdapter(Context context, ArrayList<Sport>sportArrayList) {
        this.context = context;
        this.sportsData = sportArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((LayoutInflater.from(context).inflate(R.layout.list_item,parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sport currSport = sportsData.get(position);
        holder.bindItem(currSport);
    }

    @Override
    public int getItemCount() {
        return sportsData.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textTitle;
        private TextView textInfo;
        private ImageView imageViewSport;
        private GradientDrawable mGradientDrawable;

        public ViewHolder(View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.title);
            textInfo = itemView.findViewById(R.id.subTitle);
            imageViewSport = (ImageView)itemView.findViewById(R.id.imageViewSport);

        }

        public void bindItem(Sport currentSport){
            textTitle.setText(currentSport.getTitle());
            textInfo.setText(currentSport.getInfo());
            Glide.with(context).load(currentSport.getImageId()).into(imageViewSport);
//  Glide
//    .with(myFragment)
//    .load(url)
//    .centerCrop()
//    .placeholder(R.drawable.loading_spinner)
//    .into(myImageView);
        }
    }
}

