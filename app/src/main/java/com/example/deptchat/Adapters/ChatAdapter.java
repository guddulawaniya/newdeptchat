package com.example.deptchat.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.example.deptchat.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessagesModule> messagesModules;
    Context context;

    int SENDER_VIEW_TYPE;


    public ChatAdapter(ArrayList<MessagesModule> messagesModules, Context context, int layid) {
        this.messagesModules = messagesModules;
        this.context = context;
        this.SENDER_VIEW_TYPE = layid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (SENDER_VIEW_TYPE == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_message, parent, false);
            return new senderViewholder(view);
        } else if (SENDER_VIEW_TYPE == 2) {
            View view = LayoutInflater.from(context).inflate(R.layout.sampl_reciever, parent, false);
            return new recieverViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.imageviewmessage, parent, false);
            return new recieverViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessagesModule messagemodule = messagesModules.get(position);

        SharedPreferences preferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        String imagetext = preferences.getString("image", null);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you  sure want to delete this message ")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int watch) {
                                dialog.dismiss();
                            }
                        }).show();

                return false;
            }
        });

        if (SENDER_VIEW_TYPE == 1) {
            ((senderViewholder) holder).senderMsg.setText(messagemodule.getMessage());
            Picasso.get().load(imagetext).into(((senderViewholder) holder).senderimage);
        } else if (SENDER_VIEW_TYPE == 2) {
            ((recieverViewHolder) holder).recieverMsg.setText(messagemodule.getMessage());
            Picasso.get().load(imagetext).into(((recieverViewHolder) holder).recieverimage);
        } else {
            Picasso.get().load(imagetext).into(((imageViewholder) holder).profileimage);
            Picasso.get().load(messagemodule.getImage()).into(((imageViewholder) holder).senderMsg);
        }

    }

    @Override
    public int getItemCount() {


        return messagesModules.size();
    }

    public class recieverViewHolder extends RecyclerView.ViewHolder {
        TextView recieverMsg;
        CircleImageView recieverimage;

        public recieverViewHolder(@NonNull View itemView) {
            super(itemView);

            recieverMsg = itemView.findViewById(R.id.recieverText);
            recieverimage = itemView.findViewById(R.id.recieverimage);
        }
    }

    class senderViewholder extends RecyclerView.ViewHolder {
        TextView senderMsg;
        CircleImageView senderimage;

        public senderViewholder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.senderText);
            senderimage = itemView.findViewById(R.id.senderimage);

        }
    }

    class imageViewholder extends RecyclerView.ViewHolder {
        ImageView senderMsg;
        CircleImageView profileimage;

        public imageViewholder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.messageimage);
            profileimage = itemView.findViewById(R.id.image_message_profile);

        }
    }
}
