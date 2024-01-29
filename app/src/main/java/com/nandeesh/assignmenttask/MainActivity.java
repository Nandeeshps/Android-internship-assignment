package com.nandeesh.assignmenttask;

import android.graphics.Typeface;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.text.style.StyleSpan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private LinearLayout stackContainer;
    private int textColor = Color.BLACK;
    private String[] fontStyles = {"Select your font style", "Bold", "Italic", "Bold Italic"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        stackContainer = findViewById(R.id.stackContainer);

        setupSpinner();

        // Select Color button click listener
        Button btnSelectColor = findViewById(R.id.btnSelectColor);
        btnSelectColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showColorInputDialog();
            }
        });

        // Add Text button click listener
        Button btnAddText = findViewById(R.id.btnAddText);
        btnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTextToStack();
            }
        });
    }

    private void setupSpinner() {
        Spinner fontSpinner = findViewById(R.id.fontSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fontStyles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(adapter);

        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFontStyle(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void applyFontStyle(int styleIndex) {
        int fontStyle = Typeface.NORMAL;
        switch (styleIndex) {
            case 1:
                fontStyle = Typeface.BOLD;
                break;
            case 2:
                fontStyle = Typeface.ITALIC;
                break;
            case 3:
                fontStyle = Typeface.BOLD_ITALIC;
                break;

        }
        if (editText.getTypeface() != null) {
            editText.setTypeface(null, fontStyle);
        } else {
            // If no typeface is set, create a new one with the specified style
            editText.setTypeface(Typeface.defaultFromStyle(fontStyle));
        }
    }

    private void showColorInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.color_input_dialog, null);
        builder.setView(dialogView);

        final EditText editColorInput = dialogView.findViewById(R.id.editColorInput);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String colorInput = editColorInput.getText().toString().trim();
                textColor = parseColor(colorInput, Color.BLACK);
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

    private int parseColor(String colorInput, int defaultColor) {
        try {
            int color = Color.parseColor(colorInput);
            return color;
        } catch (IllegalArgumentException e) {
            try {
                // If parsing as a color name fails, try parsing as a hexadecimal value
                int color = Color.parseColor("#" + colorInput);
                return color;
            } catch (IllegalArgumentException ex) {
                // If both parsing attempts fail, return the default color
                return defaultColor;
            }
        }
    }
    private void addTextToStack() {
        String enteredText = editText.getText().toString().trim();

        if (!enteredText.isEmpty()) {
            SpannableStringBuilder styledText = new SpannableStringBuilder(enteredText);

            // Apply color
            if (textColor != Color.BLACK) {
                styledText.setSpan(new ForegroundColorSpan(textColor), 0, enteredText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            // Apply font style
            int fontStyle = editText.getTypeface().getStyle();
            styledText.setSpan(new StyleSpan(fontStyle), 0, enteredText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            TextView textView = new TextView(this);
            textView.setText(styledText);

            stackContainer.addView(textView);

            editText.getText().clear();
        }
    }

}
