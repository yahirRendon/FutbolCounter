package com.example.android.futbolcounter;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Console;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    TextView teamAText; //Display Team A Name
    TextView teamBText; //Display Team B Name
    TextView teamAScore; //Display Team A Score
    TextView teamBScore; //Display Team B Score
    String teamAName; //Capture String value of team A Name
    String teamBName; //Capture String value of team B Name
    int teamAGoals; //Count team A Goals Scored
    int teamBGoals; //Count team B Goals Scored
    int shotA; //Count shots for team A
    int shotB; //Count shots for team B
    int shotTargetA; //Count shots on target for team A
    int shotTargetB; //Count shots on target for team B
    int cornerA; //Count team A corners
    int cornerB; //Count team B corners
    Button teamAShot; //Team A shot button
    Button teamBShot; //Team B shot button
    Button teamA_shotTarget; //Team A shot on target button
    Button teamB_shotTarget; //Team B shot on target button
    Button teamA_shotAcc; //Team A shot accuracy
    Button teamB_shotAcc; //Team B shot accuracy
    Button teamA_Corner; //Team A corners
    Button teamB_Corner; //Team B corners
    Button resetButton; //Reset button
    TextView summaryTitle; //Match Summary title
    boolean startTimer; //Start/Stop timer
    int seconds; //count seconds in timer
    int minutes; //count minutes in timer
    int secs; //assign seconds in timer
    Button timerButton; //Timer button
    String time; //Display game time
    boolean secondHalf; //Set time for second half
    ArrayList<String> summaryList = new ArrayList<String>(); //Match summary list
    TextView summaryReport; //Display match summary
    int count = -1; //For tracking game summary indices
    ArrayList<Integer> countA = new ArrayList<Integer>(); //For tracking teamA goal indices
    ArrayList<Integer> countB = new ArrayList<Integer>(); //For tracking teamB goal indices

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //For Timer
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            startTimer = savedInstanceState.getBoolean("startRun");
        }
        //Run InitializeActivityTwo method
        onInitializeActivityTwo();
    }

    /*
     *Initialize elements in onCreate
     */
    public void onInitializeActivityTwo() {
        getWindow().setBackgroundDrawableResource(R.drawable.grass);
        //Timer
        Timer();
        timerButton = (Button)findViewById(R.id.timer_button);
        //Buttons and TextViews
        teamAText = (TextView)findViewById(R.id.team_A_text);
        teamBText = (TextView)findViewById(R.id.team_B_text);
        teamAName = getIntent().getStringExtra(MainActivity.MyConstants.teamAKey);
        teamBName = getIntent().getStringExtra(MainActivity.MyConstants.teamBKey);
        teamAText.setText(teamAName);
        teamBText.setText(teamBName);
        teamAScore = (TextView)findViewById(R.id.team_A_score);
        teamBScore = (TextView)findViewById(R.id.team_B_score);
        teamAShot = (Button)findViewById(R.id.team_A_shot);
        teamBShot = (Button)findViewById(R.id.team_B_shot);
        teamA_shotTarget = (Button)findViewById(R.id.team_A_shotTarget);
        teamB_shotTarget = (Button)findViewById(R.id.team_B_shotTarget);
        teamA_shotAcc = (Button)findViewById(R.id.team_A_shotAccuracy);
        teamB_shotAcc = (Button)findViewById(R.id.team_B_shotAccuracy);
        teamA_Corner = (Button)findViewById(R.id.team_A_corner);
        teamB_Corner = (Button)findViewById(R.id.team_B_corner);
        resetButton = (Button)findViewById(R.id.reset_button);
        summaryReport = (TextView)findViewById(R.id.summary_text);
        summaryTitle = (TextView)findViewById(R.id.summaryTitle_text);
        //For onClickListener
        findViewById(R.id.team_A_goal).setOnClickListener(mGlobal_OnClickListener); //Team A Goal Button
        findViewById(R.id.team_B_goal).setOnClickListener(mGlobal_OnClickListener); //Team B Goal Button
        findViewById(R.id.team_A_shot).setOnClickListener(mGlobal_OnClickListener); //Team A Shot Button
        findViewById(R.id.team_B_shot).setOnClickListener(mGlobal_OnClickListener); //Team B Shot Button
        findViewById(R.id.team_A_shotTarget).setOnClickListener(mGlobal_OnClickListener); //Team A ShotTarget Button
        findViewById(R.id.team_B_shotTarget).setOnClickListener(mGlobal_OnClickListener); //Team B ShotTarget Button
        findViewById(R.id.team_A_corner).setOnClickListener(mGlobal_OnClickListener); //Team A Corner Button
        findViewById(R.id.team_B_corner).setOnClickListener(mGlobal_OnClickListener); //Team B Corner Button
        findViewById(R.id.timer_button).setOnClickListener(mGlobal_OnClickListener); //Reset Button
        findViewById(R.id.reset_button).setOnClickListener(mGlobal_OnClickListener); //Reset Button
        //For onLONGClickListener
        findViewById(R.id.team_A_goal).setOnLongClickListener(mGlobal_onLongClick); //Team A Long Goal
        findViewById(R.id.team_B_goal).setOnLongClickListener(mGlobal_onLongClick); //Team B Long Goal
        findViewById(R.id.team_A_shot).setOnLongClickListener(mGlobal_onLongClick); //Team A Long Shot
        findViewById(R.id.team_B_shot).setOnLongClickListener(mGlobal_onLongClick); //Team B Long Shot
        findViewById(R.id.team_A_shotTarget).setOnLongClickListener(mGlobal_onLongClick); //Team A Long ShotTarget
        findViewById(R.id.team_B_shotTarget).setOnLongClickListener(mGlobal_onLongClick); //Team B Long ShotTarget
        findViewById(R.id.team_A_corner).setOnLongClickListener(mGlobal_onLongClick); //Team A Long Corner
        findViewById(R.id.team_B_corner).setOnLongClickListener(mGlobal_onLongClick); //Team B Long Corner
        findViewById(R.id.timer_button).setOnLongClickListener(mGlobal_onLongClick); //Team B Long Corner
    }

    /*
     * Timer method;
     */
    public void onSaveInstanceStateA(Bundle saveInstanceStateA) {
        saveInstanceStateA.putInt("seconds", seconds);
        saveInstanceStateA.putBoolean("startRun", startTimer);
    }

    /*
     * This method runs when timer button is clicked.
     */
    public void startStopTimer() {
        startTimer =! startTimer;
    }

    /**
     * Update the goal summary report
     */
    public void createSummary() {
        summaryReport.setText("");
        if(summaryList.size() > 0) {
            for (int i = 0; i < summaryList.size(); i++) {
                if(summaryList.get(i).matches("")) {

                } else{
                    summaryReport.append(summaryList.get(i) + "\n");
                }
            }
        }
        //summaryList.clear();
    }

    /*
     * Update the goal count and display
     *
     * isTeamA is used to determine if TeamA scored
     * isLongClick is used to determine if button was LongClicked
     */
    public void updateGoal(boolean isTeamA, boolean isLongClick) {
        if(isTeamA) {
            if(isLongClick) {
                teamAGoals--;
                if(teamAGoals <0) {
                    teamAGoals = 0;
                }
                //Removing entry from game summary
                if(summaryList.size() > 0 && countA.size() > 0) {
                    summaryList.set(countA.get(countA.size()-1), "");
                    countA.remove(countA.size() - 1);
                }
            } else {
                teamAGoals++;
                count++; //For tracking game summary indices
                countA.add(count); //for tracking game summary
                summaryList.add(getString(R.string.team_a_summary, time, teamAName));
            }
            calcShotAccuracy(isTeamA, shotTargetA, shotA);
        } else {
            if(isLongClick) {
                teamBGoals--;
                if(teamBGoals < 0) {
                    teamBGoals = 0;
                }
                //Removing entry from game summary
                if(summaryList.size() > 0 && countB.size() > 0) {
                    summaryList.set(countB.get(countB.size()-1), "");
                    countB.remove(countB.size() -1);
                }
                } else {
                    teamBGoals++;
                    count++; //For tracking game summary indices
                    countB.add(count); //For tracking game summary
                    summaryList.add(getString(R.string.team_b_summary, time, teamBName));
            }
            calcShotAccuracy(isTeamA, shotTargetB, shotB);
            }
        teamAScore.setText(String.valueOf(teamAGoals));
        teamBScore.setText(String.valueOf(teamBGoals));
        createSummary();
    }

    /*
     * Update the shot count and display
     *
     * isTeamA is used to determine if TeamA shot
     * isLongClick is used to determine if button was LongClicked
     */
    public void updateShot(boolean isTeamA, boolean isLongClick) {
        if(isTeamA) {
            if(isLongClick) {
                shotA--;
                if(shotA <0) {
                    shotA = 0;
                } else if(shotA < shotTargetA) {
                    shotA = shotTargetA;
                }
            } else {
                shotA++;
            }
            calcShotAccuracy(isTeamA, shotTargetA, shotA);
        } else {
            if(isLongClick) {
                shotB--;
                if(shotB < 0) {
                    shotB = 0;
                } else if(shotB < shotTargetB) {
                    shotB = shotTargetB;
                }
                } else {
                    shotB++;
            }
            calcShotAccuracy(isTeamA, shotTargetB, shotB);
        }
        teamAShot.setText(String.valueOf(shotA));
        teamBShot.setText(String.valueOf(shotB));
    }

    /*
     * Update the shot on target count and display
     *
     * isTeamA is used to determine if TeamA shot was on target
     * isLongClick is used to determine if button was LongClicked
     */
    public void updateShotTarget(boolean isTeamA, boolean isLongClick) {
        if(isTeamA) {
            if(isLongClick) {
                shotTargetA--;
                if(shotTargetA <0) {
                    shotTargetA = 0;
                }
            } else {
                shotTargetA++;
            }
            calcShotAccuracy(isTeamA, shotTargetA, shotA);
        } else {
            if(isLongClick) {
                shotTargetB--;
                if(shotTargetB < 0) {
                    shotTargetB = 0;
                }
            } else {
                shotTargetB++;
            }
            calcShotAccuracy(isTeamA, shotTargetB, shotB);
        }
        teamA_shotTarget.setText(String.valueOf(shotTargetA));
        teamB_shotTarget.setText(String.valueOf(shotTargetB));
    }

    /*
     * Method calculates shot accuracy for each team
     *
     * isTeamA is used to determine if calculation is for teamA or not
     * numShotTarget and numShot are used to get percent accuracy
     */

    public void calcShotAccuracy(boolean isTeamA, int numShotTarget, int numShot) {
        float target = numShotTarget;
        float shot = numShot;
        float calc = (target / shot) * 100;
        int accuracy = Math.round(calc);
        if(isTeamA) {
            teamA_shotAcc.setText(Math.round(accuracy) + "%");
        } else {
            teamB_shotAcc.setText(Math.round(accuracy) + "%");
        }
    }

    /*
    * Update the corner count and display
    *
    * isTeamA is used to determine if TeamA got a corner
    * isLongClick is used to determine if button was LongClicked
    */
    public void updateCorner(boolean isTeamA, boolean isLongClick) {
        if(isTeamA && isLongClick) {
            cornerA--;
            if(cornerA <0) {
                cornerA = 0;
            }
        } else if(isTeamA && !isLongClick) {
            cornerA++;
        } else if(!isTeamA && isLongClick) {
            cornerB--;
            if(cornerB < 0) {
                cornerB = 0;
            }
        } else {
            cornerB++;
        }
        teamA_Corner.setText(String.valueOf(cornerA));
        teamB_Corner.setText(String.valueOf(cornerB));
    }

    /*
     * Reset counts and views
     */
    public void reset() {
        teamAGoals = 0;
        teamBGoals = 0;
        shotA = 0;
        shotB = 0;
        shotTargetA = 0;
        shotTargetB = 0;
        cornerA = 0;
        cornerB = 0;
        startTimer = false;
        secondHalf = false;
        seconds = 0;
        minutes = 0;
        summaryList.clear();
        teamAScore.setText(String.valueOf(teamAGoals));
        teamBScore.setText(String.valueOf(teamBGoals));
        teamAShot.setText(String.valueOf(shotA));
        teamBShot.setText(String.valueOf(shotB));
        teamA_shotTarget.setText(String.valueOf(shotTargetA));
        teamB_shotTarget.setText(String.valueOf(shotTargetB));
        teamA_shotAcc.setText(R.string.team_a_acc_txt);
        teamB_shotAcc.setText(R.string.team_b_acc_txt);
        teamA_Corner.setText(String.valueOf(cornerA));
        teamB_Corner.setText(String.valueOf(cornerB));
        time = String.format("%02d:%02d", 0, 0);
        timerButton.setText(R.string.time_display);
        summaryReport.setText(R.string.no_text);
        count = -1;
        countA.clear();
        countB.clear();
    }

    /*
    * This method controls the game timer
    */
    public void Timer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //If I wish to display running timer Team A in 00:00:00 format
                if(secondHalf) {
                    int hours = seconds / 3600;
                    minutes = 45 + ((seconds % 3600) / 60);
                    secs = seconds % 60;
                    time = String.format("%02d:%02d", minutes, secs);
                    timerButton.setText(time);
                } else {
                    int hours = seconds / 3600;
                    minutes = (seconds % 3600) / 60;
                    secs = seconds % 60;
                    time = String.format("%02d:%02d", minutes, secs);
                    timerButton.setText(time);
                }
                if (startTimer) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    /*
     * Global On click listener for all views
     */
    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.team_A_goal:
                    updateGoal(true, false);
                    updateShot(true, false);
                    updateShotTarget(true, false);
                    break;
                case R.id.team_B_goal:
                    updateGoal(false, false);
                    updateShot(false, false);
                    updateShotTarget(false, false);
                    break;
                case R.id.team_A_shot:
                    updateShot(true, false);
                    break;
                case R.id.team_B_shot:
                    updateShot(false, false);
                    break;
                case R.id.team_A_shotTarget:
                    updateShot(true, false);
                    updateShotTarget(true, false);
                    break;
                case R.id.team_B_shotTarget:
                    updateShot(false, false);
                    updateShotTarget(false, false);
                    break;
                case R.id.team_A_corner:
                    updateCorner(true, false);
                    break;
                case R.id.team_B_corner:
                    updateCorner(false, false);
                    break;
                case R.id.timer_button:
                    startStopTimer();
                    break;
                case R.id.reset_button:
                    reset();
                    break;
            }
        }
    };

    /*
     * Global onLongClick listener for all views
     */
    final View.OnLongClickListener mGlobal_onLongClick = new View.OnLongClickListener() {
        public boolean onLongClick(final View v) {
            switch (v.getId()) {
                case R.id.team_A_goal:
                    updateGoal(true, true);
                    updateShotTarget(true, true);
                    updateShot(true, true);
                    return true;
                case R.id.team_B_goal:
                    updateGoal(false, true);
                    updateShotTarget(false, true);
                    updateShot(false, true);
                    return true;
                case R.id.team_A_shot:
                    updateShot(true, true);
                    return true;
                case R.id.team_B_shot:
                    updateShot(false, true);
                    return true;
                case R.id.team_A_shotTarget:
                    updateShot(true, true);
                    updateShotTarget(true, true);
                    return true;
                case R.id.team_B_shotTarget:
                    updateShot(false, true);
                    updateShotTarget(false, true);
                    return true;
                case R.id.team_A_corner:
                    updateCorner(true, true);
                    return true;
                case R.id.team_B_corner:
                    updateCorner(false, true);
                    return true;
                case R.id.timer_button:
                    startTimer = false;
                    seconds = 0;
                    secondHalf = true;
                    return true;
                default:
                    return false;
            }
        }
    };
}
