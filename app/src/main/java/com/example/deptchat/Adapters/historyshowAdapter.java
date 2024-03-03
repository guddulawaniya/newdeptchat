package com.example.deptchat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.deptchat.R;
import com.example.deptchat.chat_activity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class historyshowAdapter extends RecyclerView.Adapter<historyshowAdapter.chatRoomHolder> {
    Context context;
    private List<favoratemodule> listData;

    public historyshowAdapter(List<favoratemodule> arrayList, Context context) {
        this.listData = arrayList;
        this.context = context;
    }



    @Override
    public chatRoomHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new chatRoomHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatroomadapter, viewGroup, false));
    }



    @Override
    public int getItemViewType(int i) {
        return this.listData.size();
    }

    public void onBindViewHolder(chatRoomHolder holder, int i) {
        favoratemodule module = listData.get(i);
        holder.name.setText(module.getName());
        Picasso.get().load(module.getImage()).into(holder.img_girl);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = context.getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                editor.putString("video", module.getVideo());
                editor.putString("image", module.getImage());
                editor.putString("name", module.getName());
                editor.commit();
                Intent intent = new Intent(context, chat_activity.class);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return this.listData.size();
    }


    public class chatRoomHolder extends RecyclerView.ViewHolder {
        ImageView img_girl;
        private LinearLayout container;
        private TextView name;

        public chatRoomHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.chatname);
            container = (LinearLayout) view.findViewById(R.id.cont_item_root);
            img_girl = (ImageView) view.findViewById(R.id.img_girl);
        }
    }
}
