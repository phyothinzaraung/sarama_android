package com.koekoetech.sayarma.custom_control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;


public class MyanBoldTextView extends AppCompatTextView {

    private Context context;

    public MyanBoldTextView(Context context) {
        super(context);
        this.context = context;
        applyCustomFont(context);
        setText(MyanTextProcessor.processText(getContext(), getText().toString()));
    }

    public MyanBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
        setText(MyanTextProcessor.processText(getContext(), getText().toString()));
    }

    public MyanBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
        setText(MyanTextProcessor.processText(getContext(), getText().toString()));
    }

    public void setMyanmarText(final String text) {
        applyCustomFont(context);

        this.post(new Runnable() {
            @Override
            public void run() {

                if(text != null)
                {
                    if(!text.isEmpty())
                    {
                        String myanText = MyanTextProcessor.processText(getContext(), text);
                        setText(myanText);
                    }
                    else
                    {
                        setText("-");
                    }
                }
                else
                {
                    setText("-");
                }

            }

        });

    }

    public void setMyanmarToastText(String text){
        String myanText = MyanTextProcessor.processText(getContext(), text);
        setText(myanText);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/Poppins-Bold.ttf", context);
        setTypeface(customFont);
    }

}
