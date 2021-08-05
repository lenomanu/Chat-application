package com.finix.midnight.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finix.midnight.databinding.ItemConteninerReceivedMessageBinding;
import com.finix.midnight.databinding.ItemConteninerSentMessageBinding;
import com.finix.midnight.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Bitmap receivedProfileImage;
    private final List<ChatMessage> chatMessages;
    private final String senderId;

     public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public ChatAdapter(Bitmap receivedProfileImage, List<ChatMessage> chatMessages, String senderId) {
        this.receivedProfileImage = receivedProfileImage;
        this.chatMessages = chatMessages;
        this.senderId = senderId;
    }

    @NonNull

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SENT){
            return new SentMessageViewHolder(
                    ItemConteninerSentMessageBinding.inflate(LayoutInflater.from(parent.getContext()),
                            parent,false)
            );
        }else {
            return new ReceivedMessageViewHolder(
                    ItemConteninerReceivedMessageBinding.inflate(LayoutInflater.from(parent.getContext()),
                            parent,false)
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)== VIEW_TYPE_SENT){
            ((SentMessageViewHolder) holder).setMessage(chatMessages.get(position));
        }else{
            ((ReceivedMessageViewHolder) holder).setMessage(chatMessages.get(position), receivedProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(chatMessages.get(position).senderId == senderId){
            return VIEW_TYPE_SENT;
        }else{
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemConteninerSentMessageBinding binding;
        SentMessageViewHolder(ItemConteninerSentMessageBinding itemConteninerSentMessageBinding){
            super(itemConteninerSentMessageBinding.getRoot());
            binding = itemConteninerSentMessageBinding;
        }
        void setMessage(ChatMessage chatMessage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);

        }
    }
    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemConteninerReceivedMessageBinding binding;
        ReceivedMessageViewHolder(ItemConteninerReceivedMessageBinding itemConteninerReceivedMessageBinding){
            super(itemConteninerReceivedMessageBinding.getRoot());
            binding = itemConteninerReceivedMessageBinding;
        }
        void setMessage(ChatMessage chatMessage,Bitmap receivedProfileImage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
            binding.imageProfile.setImageBitmap(receivedProfileImage);

        }
    }
}
