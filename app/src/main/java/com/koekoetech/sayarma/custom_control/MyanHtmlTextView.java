package com.koekoetech.sayarma.custom_control;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import org.sufficientlysecure.htmltextview.HtmlTextView;


public class MyanHtmlTextView extends HtmlTextView {

    public MyanHtmlTextView(Context context) {
        super(context);

        setMyanmarHtmlText(getText().toString());
    }

    public MyanHtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setMyanmarHtmlText(getText().toString());
    }

    public MyanHtmlTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setMyanmarHtmlText(getText().toString());
    }

    public void setMyanmarHtmlText(final String text) {

        applyCustomFont();
        this.post(new Runnable() {
            @Override
            public void run() {
                setHtml(MyanTextProcessor.processText(getContext(), text));
            }
        });

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void applyCustomFont() {
        /*Typeface customFont = FontCache.getTypeface("Avenir-Medium.ttf", getContext());
        setTypeface(customFont);*/
    }

}
