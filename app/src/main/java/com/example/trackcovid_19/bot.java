package com.example.trackcovid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.model.AIError;
import ai.api.model.AIResponse;

public class bot extends AppCompatActivity implements AIListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot);
        final AIConfiguration config = new AIConfiguration("CLIENT_ACCESS_TOKEN",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
    }

    @Override
    public void onResult(AIResponse result) {

    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}
