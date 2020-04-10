package com.example.trackcovid_19;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ai.api.AIServiceContext;
import ai.api.AIServiceContextBuilder;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class ChatBot extends AppCompatActivity implements View.OnClickListener {
    private TextView tvSend;
    private List<Msg> msgList = new ArrayList<>();
    private EditText etMsg;
    private static final String TAG = ChatBot.class.getSimpleName();
    private String uuid = UUID.randomUUID().toString();
    private AIRequest aiRequest;
    private AIDataService aiDataService;
    private AIServiceContext customAIServiceContext;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MsgAdapter adapter;

    private final static int USER = MsgAdapter.VIEW_TYPE_SEND;
    private final static int BOT = MsgAdapter.VIEW_TYPE_RECEIVED;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        recyclerView = findViewById(R.id.messagesRecyclerView);
        etMsg = findViewById(R.id.messageEditText);
        tvSend = findViewById(R.id.SendTextAsButton);
        tvSend.setOnClickListener(this);

        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        adapter = new MsgAdapter(this, msgList,user.getPhotoUrl());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom && msgList.size()>2) {
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(
                                    msgList.size() - 1);
                        }
                    }, 100);
                }
            }
        });
        initChatbot();
    }

    private void initChatbot() {
        String clientToken = "81178723c3d84df3ad4b559a8f5aba83";
        String devToken = "8b7d3d7bcf3b43e99613232f7e599313";
        final AIConfiguration config = new AIConfiguration(devToken,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiDataService = new AIDataService(this, config);
        customAIServiceContext = AIServiceContextBuilder.buildFromSessionId(uuid);// helps to create new session whenever app restarts
        aiRequest = new AIRequest();
    }

    public void callback(AIResponse aiResponse) {
        if (aiResponse != null) {
            String botReply = aiResponse.getResult().getFulfillment().getSpeech();
            Log.d(TAG, "Bot Reply: " + botReply);
            showTextView(new Msg(botReply, BOT));
        } else {
            Log.d(TAG, "Bot Reply: Null");
            showTextView(new Msg("There was some communication issue. Please Try again!", BOT));
        }
    }


    private void showTextView(Msg msg) {
        msgList.add(msg);
        adapter.notifyItemInserted(msgList.size()-1);
        recyclerView.scrollToPosition(msgList.size()-1);
    }
    private void sendMessage() {
        String msg = etMsg.getText().toString();
        if (msg.trim().isEmpty()) {
            Toast.makeText(this, "Please enter your query!", Toast.LENGTH_LONG).show();
        } else {
            showTextView(new Msg(msg,USER));
            etMsg.setText("");
            aiRequest.setQuery(msg);
            RequestTask requestTask = new RequestTask(ChatBot.this, aiDataService, customAIServiceContext);
            requestTask.execute(aiRequest);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.SendTextAsButton:
                sendMessage();
                break;
        }
    }
}
