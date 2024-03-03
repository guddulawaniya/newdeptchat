package com.example.deptchat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deptchat.ConnectionVideoActivity;
import com.example.deptchat.R;
import com.example.deptchat.details_activity;
import com.example.deptchat.plan_activity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class maineAdapter extends RecyclerView.Adapter<maineAdapter.viewholder> {
    List<YourDataModel> list;
    Context context;

    public maineAdapter(List<YourDataModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_card_layout, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        YourDataModel data = list.get(position);

        String[] parts = data.getText().split("-");


        if (parts.length == 2) {
            String name = parts[0];
            String location = parts[1];
            holder.age.setText(new DecimalFormat("18").format(new Random().nextInt(35)));
            holder.name.setText(name);
            holder.location.setText(location);
        } else {

        }


        Picasso.get().load( data.getImg()).into(holder.image);

        holder.videocallbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = context.getSharedPreferences("login", context.MODE_PRIVATE);
                int permincharge = preferences.getInt("perminchage", 0);
                int availablecoin = preferences.getInt("coins", 0);

                if (availablecoin >= permincharge && availablecoin != 0) {
                    SharedPreferences.Editor editor = context.getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                    editor.putString("video", data.getVideo());
                    editor.putString("location", holder.location.getText().toString());
                    editor.putString("name", holder.name.getText().toString());
                    editor.putString("image", data.getImg());
                    editor.commit();
                    Intent intent = new Intent(context, ConnectionVideoActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, plan_activity.class);
                    context.startActivity(intent);
                }

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = context.getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                editor.putString("video", data.getVideo());
                editor.putString("location", holder.location.getText().toString());
                editor.putString("name", holder.name.getText().toString());
                editor.putString("age", holder.age.getText().toString());
                editor.putString("image", data.getImg());
                editor.commit();
                Intent intent = new Intent(context, details_activity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView image, videocallbutton;
        TextView name, age, location;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.backgroundimage);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.count);
            location = itemView.findViewById(R.id.location);
            videocallbutton = itemView.findViewById(R.id.videocallbutton);
        }
    }
}
