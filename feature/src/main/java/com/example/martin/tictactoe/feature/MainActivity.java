package com.example.martin.tictactoe.feature;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    private BoardView boardView;
    private GameEngine gameEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = (BoardView) findViewById(R.id.board);
        gameEngine = new GameEngine();
        boardView.setGameEngine(gameEngine);
        boardView.setMainActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            newGame();
        }

        return super.onOptionsItemSelected(item);
    }

    public void gameError() {
        String msg = "only press empty fields";
        new AlertDialog.Builder(this).setTitle("PRESS OTHERWERE").setMessage(msg).show();
    }

    public void gameEnded(char c) {
        String msg ;

        if (c == 'T') {
            msg = "Game Ended. Nobody won";
        } else if (c == 'X') {
            msg = "Congratulations! You won!";
        } else {
            msg = "Looser! Computer won!";
        }
        new AlertDialog.Builder(this).setTitle("Hey Bro").setMessage(msg).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                newGame();
            }
        }).show();
    }

    private void newGame() {
        gameEngine.newGame();
        // if the computer starts, let him make the first move
        if( gameEngine.getCurrentPlayer() == 'O') {
            gameEngine.computer();
        }
        boardView.invalidate();
    }

}
