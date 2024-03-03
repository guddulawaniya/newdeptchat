package com.example.deptchat.chatroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.deptchat.Adapters.favoratemodule;
import com.example.deptchat.R;
import com.example.deptchat.chat_activity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.chatRoomHolder> {
    Context context;
    private List<favoratemodule> listData;

    public ChatRoomAdapter(List<favoratemodule> arrayList, Context context) {
        this.listData = arrayList;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public chatRoomHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new chatRoomHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatroomadapter, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.listData.size();
    }

    public void onBindViewHolder(chatRoomHolder holder, int i) {

        favoratemodule data = this.listData.get(i);

        String[] parts = data.getName().split("-");

        if (parts.length == 2) {
            // Extract the name and city
            String name = parts[0];
            String location = parts[1];
//            holder.age.setText(new DecimalFormat("18").format(new Random().nextInt(35)));
            holder.name.setText(name);
//            holder.location.setText(location);
        } else {

        }
//        holder.name.setText(chatRoomModel.getText());
        Picasso.get().load( data.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = context.getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                editor.putString("video", data.getVideo());
                editor.putString("image", data.getImage());
                editor.putString("name", holder.name.getText().toString());
                editor.commit();
                Intent intent = new Intent(context, chat_activity.class);
                context.startActivity(intent);
            }
        });
    }


    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.listData.size();
    }


    public class chatRoomHolder extends RecyclerView.ViewHolder {
        ImageView image;
        private TextView name;

        public chatRoomHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.chatname);
            image = (ImageView) view.findViewById(R.id.img_girl);
        }
    }
}
