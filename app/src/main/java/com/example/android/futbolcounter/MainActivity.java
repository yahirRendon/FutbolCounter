package com.example.android.futbolcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText teamAInput; //Input from Team A
    EditText teamBInput; //Input from Team B
    Button submitTeamsButton; //Launch second activity button
    String teamA; // TeamA input to String
    String teamB; // TeamB input to String

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setBackgroundDrawableResource(R.drawable.grass);
        onInitialize();
    }

    /*
     * Initialize elements in onCreate
     */
    public void onInitialize() {
        teamAInput = (EditText)findViewById(R.id.team_A_input);
        teamBInput = (EditText)findViewById(R.id.team_B_input);
        submitTeamsButton = (Button)findViewById(R.id.submit_teams_button);
    }

    /*
     * Launch activity two when submit button is clicked
    */
    public void launchActivityTwo(View view) {
        teamA = teamAInput.getText().toString().trim();
        teamB = teamBInput.getText().toString().trim();
        if(teamA.matches("") && teamB.matches("")) {
            teamA = "FC Barcelona";
            teamB = "Real Madrid CF";
        }else if (teamA.matches("")) {
            teamA = "FC Barcelona";
        }else if(teamB.matches("")) {
            teamB = "Real Madrid CF";
        } else if( teamA.matches(teamB)) {
            //Inform user team names must be different
            Toast.makeText(getBaseContext(), "Team Names Must Differ", Toast.LENGTH_SHORT).show();
        }
        Intent toActivity2 = new Intent(getApplicationContext(), Main2Activity.class);
        toActivity2.putExtra(MyConstants.teamAKey, teamA);
        toActivity2.putExtra(MyConstants.teamBKey, teamB);
        startActivity(toActivity2);
    }

    /*
    * Manage global constants
    */
    public class MyConstants {
        public static final String teamAKey = "teamAPush";
        public static final String teamBKey = "teamBPush";
    }
}
