package com.finnauxapp.Util;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.finnauxapp.R;

public class FontEditText extends EditText {
    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public FontEditText(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public FontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        this.attrs=attrs;
        init();
    }

    public FontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        this.attrs=attrs;
        this.defStyle=defStyle;
        init();
    }

    private void init() {
        Typeface font=Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Regular.ttf");
        this.setTypeface(font);
    }
    @Override
    public void setTypeface(Typeface tf, int style) {
        tf=Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Regular.ttf");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf=Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Regular.ttf");
        super.setTypeface(tf);
    }
}

