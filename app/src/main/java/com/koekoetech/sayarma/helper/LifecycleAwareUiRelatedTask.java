package com.koekoetech.sayarma.helper;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import needle.UiRelatedTask;

public abstract class LifecycleAwareUiRelatedTask<Result> extends UiRelatedTask<Result> implements LifecycleEventObserver {
    private static final String TAG = "LCA_UiRelatedTask";

    public LifecycleAwareUiRelatedTask(@NonNull Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            Log.d(TAG, "onStateChanged: On Destroy Called");
            try {
                source.getLifecycle().removeObserver(this);
                if (!isCanceled()) {
                    Log.d(TAG, "onStateChanged: Cancelling Task");
                    cancel();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
