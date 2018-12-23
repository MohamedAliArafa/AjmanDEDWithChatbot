package com.ajman.ded.ae.utility;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.ajman.ded.ae.R;

import androidx.appcompat.widget.AppCompatEditText;

public class MaskEditText extends AppCompatEditText {

    private static final char REPLACE_CHAR = '#';

    private CharSequence mask;
    private boolean updating;

    public MaskEditText(Context context) {
        super(context);
    }

    public MaskEditText(Context context, String mask) {
        super(context);
        setMask(mask);
    }

    public MaskEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MaskEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaskEditText);
        try {
            setMask(a.getString(R.styleable.MaskEditText_mask));
        } finally {
            a.recycle();
        }
    }

    private void applyMask(Editable text) {
        if (TextUtils.isEmpty(text) || !hasMask()) {
            return;
        }

        //remove input filters to ignore input type
        InputFilter[] filters = text.getFilters();
        text.setFilters(new InputFilter[0]);

        int maskLen = mask.length();
        int textLen = text.length();

        int i = 0;
        int notSymbolIndex = 0;
        StringBuilder sb = new StringBuilder();
        while (i < maskLen && notSymbolIndex < textLen) {
            if (mask.charAt(i) == text.charAt(notSymbolIndex) || mask.charAt(i) == REPLACE_CHAR) {
                sb.append(text.charAt(notSymbolIndex));
                notSymbolIndex++;
            } else {
                sb.append(mask.charAt(i));
            }
            i++;
        }

        text.clear();
        text.append(sb.toString());

        //reset filters
        text.setFilters(filters);
    }

    public boolean hasMask() {
        return !TextUtils.isEmpty(mask);
    }

    public boolean isUpdating() {
        return updating;
    }

    public String getRawText() {
        String text = String.valueOf(super.getText());
        return getUnmaskedText(text);
    }

    public void setMask(CharSequence mask) {
        this.mask = mask;

        if (hasMask()) {
            setMaxLength(mask.length());
            addTextChangedListener(new MaskFormatter());
        }
    }

    public void setMaxLength(int length) {
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    public String getUnmaskedText(String text) {
        if (TextUtils.isEmpty(text) || !hasMask()) {
            return text;
        }

        int maskLen = mask.length();
        int textLen = text.length();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maskLen && i < textLen; i++) {
            if (mask.charAt(i) == REPLACE_CHAR) {
                sb.append(text.charAt(i));
            }
        }

        return sb.toString();
    }

    private class MaskFormatter implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (updating || !hasMask()) {
                return;
            }

            updating = true;
            applyMask(s);
            updating = false;
        }
    }
}