package com.example.trackcovid_19;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    public static final int VIEW_TYPE_SEND = 1;
    public static final int VIEW_TYPE_RECEIVED = -1;
    private List<Msg> msgList;
    private Uri imgURL;
    int previousTextFrom=0;

    public MsgAdapter(Context context, List<Msg> msgList, Uri imgURL) {
        this.context = context;
        this.msgList = msgList;
        this.imgURL = imgURL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType==VIEW_TYPE_SEND){
            View sentMsgView = inflater.inflate(R.layout.send_msg_item,viewGroup,false);
            viewHolder = new SentMsgViewHolder(sentMsgView);

        }else{
            View receivedMsgView = inflater.inflate(R.layout.received_msg_item,viewGroup,false);
            viewHolder = new ReceivedMsgViewHolder(receivedMsgView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Msg msg = msgList.get(i);
        if(viewHolder instanceof SentMsgViewHolder){
            ((SentMsgViewHolder)viewHolder).msg.setText(msg.getText());
            Picasso.get().load(imgURL).into(((SentMsgViewHolder)viewHolder).imageView);
        }else{
            ((ReceivedMsgViewHolder)viewHolder).msg.setText(msg.getText());
        }
        if(i>=1 && msg.getType()==msgList.get(i-1).getType())
            dontShowImage(viewHolder);
    }

    private void dontShowImage(RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof SentMsgViewHolder)
            ((SentMsgViewHolder)viewHolder).cardView.setVisibility(View.INVISIBLE);
        else
            ((ReceivedMsgViewHolder)viewHolder).cardView.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemViewType(int position) {
        return msgList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public class SentMsgViewHolder extends RecyclerView.ViewHolder{
        TextView msg;
        ImageView imageView;
        CardView cardView;
        public SentMsgViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.tv_send);
            imageView = itemView.findViewById(R.id.ivSendChat);
            cardView = itemView.findViewById(R.id.ivSendChatCard);
        }
    }
    public class ReceivedMsgViewHolder extends RecyclerView.ViewHolder{
        TextView msg;
        ImageView imageView;
        CardView cardView;
        public ReceivedMsgViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.tv_received);
            imageView = itemView.findViewById(R.id.ivReceivedChat);
            cardView = itemView.findViewById(R.id.ivReceivedChatCard);
        }
    }
}
