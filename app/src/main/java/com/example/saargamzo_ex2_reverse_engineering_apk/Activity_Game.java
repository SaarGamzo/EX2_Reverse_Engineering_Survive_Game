package com.example.saargamzo_ex2_reverse_engineering_apk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Buttons values:
// up arrow = 2
// right arrow = 1
// down arrow = 3
// left arrow = 0
// My ID: 205385792
// My ID % 4 : 201301312
// My secret sequence to survive: up left right down left right down right up
public class Activity_Game extends AppCompatActivity {
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_STATE = "EXTRA_STATE";
    private ImageButton[] arrows;
    int currentLevel = 0;
    private boolean goodToGo = true;
    int[] steps = {1, 1, 1, 2, 2, 2, 3, 3, 3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Debug", "Entering onCreate method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String id = getIntent().getStringExtra(EXTRA_ID);
        Log.d("onCreate", "ID value is: " + id);
        Log.d("onCreate", "state value is: " + getIntent().getStringExtra(EXTRA_STATE));

        if (id.length() == this.steps.length) {
            Log.d("onCreate", "ID length matches steps length");
            for (int i = 0; i < this.steps.length; i++) {
                Log.d("onCreate", "Before change: steps[" + i + "] = " + this.steps[i]);
                this.steps[i] = Integer.valueOf(String.valueOf(id.charAt(i))) % 4;
                Log.d("onCreate", "After change: steps[" + i + "] = " + this.steps[i]);
            }
        }

        findViews();
        initViews();
        Log.d("Debug", "Exiting onCreate method");
    }

    private void findViews() {
        Log.d("Debug", "Entering findViews method");
        this.arrows = new ImageButton[]{
                findViewById(R.id.game_BTN_left),
                findViewById(R.id.game_BTN_right),
                findViewById(R.id.game_BTN_up),
                findViewById(R.id.game_BTN_down)
        };
        Log.d("Debug", "arrows array initialized: " + java.util.Arrays.toString(this.arrows));
        Log.d("Debug", "Exiting findViews method");
    }

    private void initViews() {
        Log.d("Debug", "Entering initViews method");
        for (int i = 0; i < this.arrows.length; i++) {
            final int finalI = i;
            this.arrows[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("initViews", "Arrow button clicked: " + finalI);
                    Activity_Game.this.arrowClicked(finalI);
                }
            });
        }
        Log.d("Debug", "Exiting initViews method");
    }

    private void arrowClicked(int direction) {
        Log.d("Debug", "Entering arrowClicked method");
        Log.d("arrowClicked", "Current direction value is: " + direction);
        Log.d("arrowClicked", "Current step value is: " + this.steps[this.currentLevel]);

        if (this.goodToGo && direction != this.steps[this.currentLevel]) {
            Log.d("arrowClicked", "Direction does not match step, setting goodToGo to false");
            this.goodToGo = false;
        }

        Log.d("arrowClicked", "Before increment: currentLevel = " + this.currentLevel);
        this.currentLevel++;
        Log.d("arrowClicked", "After increment: currentLevel = " + this.currentLevel);

        if (this.currentLevel >= this.steps.length) {
            Log.d("arrowClicked", "Current level exceeds steps length, finishing game");
            finishGame();
        }
        Log.d("Debug", "Exiting arrowClicked method");
    }

    private void finishGame() {
        Log.d("Debug", "Entering finishGame method");
        String state = getIntent().getStringExtra(EXTRA_STATE);
        Log.d("finishGame", "State value is: " + state);

        if (this.goodToGo) {
            Log.d("finishGame", "GoodToGo is true, showing success toast");
            Toast.makeText(this, "Survived in " + state, Toast.LENGTH_LONG).show();
        } else {
            Log.d("finishGame", "GoodToGo is false, showing failure toast");
            Toast.makeText(this, "You Failed ", Toast.LENGTH_LONG).show();
        }

        finish();
        Log.d("Debug", "Exiting finishGame method");
    }
}
