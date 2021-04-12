package com.example.sailhub;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexInputFilter implements InputFilter {

    private Pattern mPattern;
    private Context mContext;
    private static final String CLASS_NAME = RegexInputFilter.class.getSimpleName();

    /**
     * Convenience constructor, builds Pattern object from a String
     * @param pattern Regex string to build pattern from.
     */
    public RegexInputFilter(Context context, String pattern) {
        this(context, Pattern.compile(pattern));
    }

    public RegexInputFilter(Context context, Pattern pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException(CLASS_NAME + " requires a regex.");
        }

        mPattern = pattern;
        mContext = context;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {

        Matcher matcher = mPattern.matcher(source);
        if (!matcher.matches()) {
            Toast.makeText(mContext, "Invalid Input", Toast.LENGTH_SHORT).show();
            return "";
        }

        return null;
    }
}