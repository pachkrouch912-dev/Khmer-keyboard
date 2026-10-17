package com.example.khmerkeyboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(24, 28, 24, 24);

        TextView title = new TextView(this);
        title.setText("Khmer Keyboard");
        title.setTextSize(26);
        title.setPadding(0, 0, 0, 12);
        layout.addView(title);

        TextView instructions = new TextView(this);
        instructions.setText(
            "Enable the keyboard, then select it as your current input method.\\n\\n" +
            "1. Tap Open keyboard settings and enable Khmer Keyboard.\\n" +
            "2. Return here and tap Choose keyboard.\\n" +
            "3. Select Khmer Keyboard.\\n\\n" +
            "The keyboard uses Khmer Unicode. To apply your own font to the key labels, " +
            "add a licensed TTF file named KhmerCustom.ttf to app/src/main/assets/fonts/ " +
            "and rebuild. The font used to display typed text in other apps is controlled " +
            "by those apps and the Android system."
        );
        instructions.setTextSize(16);
        instructions.setLineSpacing(5, 1.0f);
        layout.addView(instructions);

        Button settingsButton = new Button(this);
        settingsButton.setText("Open keyboard settings");
        settingsButton.setOnClickListener(v ->
            startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        );
        layout.addView(settingsButton);

        Button chooseButton = new Button(this);
        chooseButton.setText("Choose keyboard");
        chooseButton.setOnClickListener(v -> {
            InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.showInputMethodPicker();
        });
        layout.addView(chooseButton);

        setContentView(layout);
    }
}
