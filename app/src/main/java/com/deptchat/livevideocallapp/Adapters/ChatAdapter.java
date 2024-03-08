package com.deptchat.livevideocallapp.Adapters;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deptchat.livevideocallapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIEVER_VIEW_TYPE = 1;
    private static final int IMAGE_RECIEVER = 2;
    private static final int SENDER_VIEW_TYPE = 3;

    private List<MessagesModule> messagesList;
    private Context context;
    private int recId;

    public ChatAdapter(List<MessagesModule> messagesList, Context context, int recId) {
        this.messagesList = messagesList;
        this.context = context;
        this.recId = recId;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {

            case RECIEVER_VIEW_TYPE:

                View receiverView = inflater.inflate(R.layout.sampl_reciever, parent, false);
                return new ReceiverViewHolder(receiverView);

            case IMAGE_RECIEVER:

                View imageView = inflater.inflate(R.layout.imageviewmessage, parent, false);
                return new ImageViewHolder(imageView);

            case SENDER_VIEW_TYPE:

                View senderView = inflater.inflate(R.layout.sample_message, parent, false);
                return new SenderViewHolder(senderView);

            default:
                throw new IllegalArgumentException("Invalid view type");
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessagesModule messageModule = messagesList.get(position);
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this message?")
                    .setPositiveButton("Yes", (dialog, which) -> {


                        // Handle message deletion
                        // Example:messagesList.remove(position);
                        // notifyDataSetChanged();


                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();

            return false;
        });


        SharedPreferences preferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);


        if (holder instanceof ReceiverViewHolder) {

            String imageUrl = preferences.getString("image", "");
            ReceiverViewHolder receiverHolder = (ReceiverViewHolder) holder;
            receiverHolder.receiverMsg.setText(messageModule.getMessage());
            Picasso.get().load(imageUrl).into(receiverHolder.receiverImage);

        }
        else if (holder instanceof ImageViewHolder) {

            String imageUrl = preferences.getString("image", "");
            ImageViewHolder imageHolder = (ImageViewHolder) holder;
            Picasso.get().load(imageUrl).into(imageHolder.profileImage);
            Picasso.get().load(messageModule.getMessageimage()).into(imageHolder.imageMessageProfile);

        }
        else if (holder instanceof SenderViewHolder) {

            String imageUrl = preferences.getString("image", "");
            SenderViewHolder senderHolder = (SenderViewHolder) holder;
            senderHolder.senderMsg.setText(messageModule.getMessage());
            Picasso.get().load(imageUrl).into(senderHolder.senderImage);

        }

    }

    @Override
    public int getItemViewType(int position) {
        return recId;
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView receiverMsg;
        CircleImageView receiverImage;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.messageTextView);
            receiverImage = itemView.findViewById(R.id.recieverimage);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView senderMsg;
        CircleImageView senderImage;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.messageTextView);
            senderImage = itemView.findViewById(R.id.senderimage);
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        CircleImageView imageMessageProfile;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.messageimage);
            imageMessageProfile = itemView.findViewById(R.id.image_message_profile);
        }
    }
}



