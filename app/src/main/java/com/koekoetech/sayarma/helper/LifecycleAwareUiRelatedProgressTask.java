package com.koekoetech.sayarma.helper;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import needle.UiRelatedProgressTask;

public abstract class LifecycleAwareUiRelatedProgressTask<Result, Progress> extends UiRelatedProgressTask<Result, Progress> implements LifecycleEventObserver {
    private static final String TAG = "LCAUiProgressTask";

    public LifecycleAwareUiRelatedProgressTask(@NonNull final LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            Log.d(TAG, "onStateChanged: State changed to DESTROY");
            try {
                if (!isCanceled()) {
                    Log.d(TAG, "onStateChanged: Canceling task.");
                    cancel();
                }
                source.getLifecycle().removeObserver(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
