package com.deptchat.livevideocallapp.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.deptchat.livevideocallapp.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    private final List<Datum> list;
    private Context context;

    public SliderAdapter(Context context,List<Datum> sliderDataArrayList) {
        this.list = sliderDataArrayList;
        this.context = context;
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterViewHolder holder, final int position) {

        final Datum sliderItem = list.get(position);

        if (sliderItem.getEnableDisable().equals("1"))
        {
            Picasso.get().load(sliderItem.getImage()).into(holder.imageViewBackground);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urlToRedirect = sliderItem.getUrl();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToRedirect));

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getCount() {
        return list.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView imageViewBackground;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
        }
    }
}
