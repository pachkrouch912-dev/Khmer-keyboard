package com.example.khmerkeyboard;

import android.graphics.Typeface;
import android.inputmethodservice.InputMethodService;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import java.util.ArrayList;
import java.util.List;

public class KhmerImeService extends InputMethodService {
    private Typeface keyTypeface;

    // Keys are Khmer Unicode code points. Android's text renderer handles Khmer shaping.
    private final String[][] rows = new String[][] {
        {"ក","ខ","គ","ឃ","ង","ច","ឆ","ជ","ឈ","ញ"},
        {"ដ","ឋ","ឌ","ឍ","ណ","ត","ថ","ទ","ធ","ន"},
        {"ប","ផ","ព","ភ","ម","យ","រ","ល","វ","ស","ហ","ឡ","អ"},
        {"ា","ិ","ី","ឹ","ឺ","ុ","ូ","ួ","ើ","ឿ","ៀ","េ","ែ","ៃ","ោ","ៅ"},
        {"ំ","ះ","ៈ","់","៌","៍","៏","័","៊","៉","។","៕"}
    };

    @Override
    public View onCreateInputView() {
        keyTypeface = loadCustomTypeface();
        LinearLayout keyboard = new LinearLayout(this);
        keyboard.setOrientation(LinearLayout.VERTICAL);
        keyboard.setPadding(dp(3), dp(5), dp(3), dp(5));
        keyboard.setBackgroundColor(Color.rgb(218, 224, 232));

        for (String[] rowKeys : rows) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER);
            for (String key : rowKeys) {
                row.addView(makeKey(key, () -> commit(key), 1.0f));
            }
            LinearLayout.LayoutParams rowParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(48));
            rowParams.bottomMargin = dp(3);
            keyboard.addView(row, rowParams);
        }

        LinearLayout bottom = new LinearLayout(this);
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        bottom.setGravity(Gravity.CENTER);
        bottom.addView(makeKey("⌫", this::deleteBeforeCursor, 1.2f));
        bottom.addView(makeKey("។", () -> commit("។"), 0.8f));
        bottom.addView(makeKey("ខ្មែរ / English", this::switchKeyboard, 1.7f));
        bottom.addView(makeKey("ចន្លោះ", () -> commit(" "), 2.2f));
        bottom.addView(makeKey("↵", this::enter, 1.0f));
        keyboard.addView(bottom, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(50)));

        return keyboard;
    }

    private Typeface loadCustomTypeface() {
        try {
            return Typeface.createFromAsset(getAssets(), "fonts/KhmerCustom.ttf");
        } catch (RuntimeException missingFont) {
            return Typeface.DEFAULT;
        }
    }

    private TextView makeKey(String label, Runnable action, float weight) {
        TextView key = new TextView(this);
        key.setText(label);
        key.setTextSize(label.length() > 3 ? 13 : 22);
        key.setTextColor(Color.rgb(28, 35, 45));
        key.setGravity(Gravity.CENTER);
        key.setTypeface(keyTypeface);
        key.setClickable(true);
        key.setFocusable(true);

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.WHITE);
        bg.setCornerRadius(dp(5));
        bg.setStroke(dp(1), Color.rgb(196, 203, 213));
        key.setBackground(bg);
        key.setOnClickListener(v -> action.run());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.MATCH_PARENT, weight);
        params.leftMargin = dp(1);
        params.rightMargin = dp(1);
        return addToCurrentRow(key, params);
    }

    // The caller adds each returned view to its row. This helper keeps layout parameters clear.
    private View addToCurrentRow(View view, LinearLayout.LayoutParams params) {
        view.setLayoutParams(params);
        return view;
    }

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private void commit(String text) {
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) ic.commitText(text, 1);
    }

    private void deleteBeforeCursor() {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        CharSequence before = ic.getTextBeforeCursor(2, 0);
        if (before == null || before.length() == 0) return;
        // Delete one Unicode code point (rather than one UTF-16 code unit).
        int count = Character.charCount(Character.codePointBefore(before, before.length()));
        ic.deleteSurroundingText(count, 0);
    }

    private void enter() {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        if (!ic.performEditorAction(getCurrentInputEditorInfo().imeOptions & 0xff)) {
            commit("\\n");
        }
    }

    private void switchKeyboard() {
        try {
            switchToNextInputMethod(false);
        } catch (RuntimeException ignored) {
            // The system keyboard picker remains available from Android's keyboard settings.
        }
    }
}
