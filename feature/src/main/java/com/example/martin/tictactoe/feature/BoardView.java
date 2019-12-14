package com.example.martin.tictactoe.feature;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class BoardView extends View {
    private ImageView [] players_o;
    private ImageView [] players_x;
    private static final int LINE_THICK = 5;
    private static final int ELT_MARGIN = 20;
    private static final int ELT_STROKE_WIDTH = 15;
    private int width, height, eltW, eltH;
    private Paint gridPaint, oPaint, xPaint;
    private GameEngine gameEngine;
    private MainActivity activity;
    public BoardView(Context context) {
        super(context);
        ImageView img = (ImageView) activity.findViewById(R.id.p_o);
        img.setVisibility(View.INVISIBLE);

    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gridPaint = new Paint();
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(Color.RED);
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setStrokeWidth(ELT_STROKE_WIDTH);
        xPaint = new Paint(oPaint);
        xPaint.setColor(Color.BLUE);

    }


    public void setMainActivity(MainActivity a) {
        activity = a;

        players_o = new ImageView[9];
        players_x = new ImageView[9];

        players_x [0] = activity.findViewById(R.id.p_x);
        players_x [1] = activity.findViewById(R.id.p_x2);
        players_x [2] = activity.findViewById(R.id.p_x3);
        players_x [3] = activity.findViewById(R.id.p_x4);
        players_x [4] = activity.findViewById(R.id.p_x5);
        players_x [5] = activity.findViewById(R.id.p_x6);
        players_x [6] = activity.findViewById(R.id.p_x7);
        players_x [7] = activity.findViewById(R.id.p_x8);
        players_x [8] = activity.findViewById(R.id.p_x9);

        players_o [0] = activity.findViewById(R.id.p_o);
        players_o [1] = activity.findViewById(R.id.p_o2);
        players_o [2] = activity.findViewById(R.id.p_o3);
        players_o [3] = activity.findViewById(R.id.p_o4);
        players_o [4] = activity.findViewById(R.id.p_o5);
        players_o [5] = activity.findViewById(R.id.p_o6);
        players_o [6] = activity.findViewById(R.id.p_o7);
        players_o [7] = activity.findViewById(R.id.p_o8);
        players_o [8] = activity.findViewById(R.id.p_o9);
    }


    public void setGameEngine(GameEngine g) {
        gameEngine = g;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height = View.MeasureSpec.getSize(heightMeasureSpec);
        width = View.MeasureSpec.getSize(widthMeasureSpec);
        eltW = (width - LINE_THICK) / 3;
        eltH = (height - LINE_THICK) / 3;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        drawBoard(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!gameEngine.isEnded() && event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) (event.getX() / eltW);
            int y = (int) (event.getY() / eltH);
            char win = gameEngine.play(x, y);
            invalidate();

            if( win == 'F' ) {
                activity.gameError();
            } else if (win != ' ') {
                activity.gameEnded(win);
            } else {
                // computer plays ...
                win = gameEngine.computer();
                invalidate();

                if (win != ' ') {
                    activity.gameEnded(win);
                }
            }
        }

        return super.onTouchEvent(event);
    }

    private void drawBoard(Canvas canvas) {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {


                players_x[x * 3 + y].getLayoutParams().height = (height - ELT_MARGIN * 2) / 3;
                players_x[x * 3 + y].getLayoutParams().width = (width - ELT_MARGIN * 2) / 3;

                players_o[x * 3 + y].getLayoutParams().height = (height - ELT_MARGIN * 2) / 3;
                players_o[x * 3 + y].getLayoutParams().width = (width - ELT_MARGIN * 2) / 3;

                players_x[x * 3 + y].setX(width / 3 * x);
                players_x[x * 3 + y].setY(height / 3 * y);

                players_o[x * 3 + y].setX(width / 3 * x);
                players_o[x * 3 + y].setX(height / 3 * y);

                players_x[x * 3 + y].setVisibility(View.INVISIBLE);
                players_o[x * 3 + y].setVisibility(View.INVISIBLE);

                drawElt(canvas, gameEngine.getField(x, y), x, y);

            }
        }
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < 2; i++) {
            // vertical lines
            float left = eltW * (i + 1);
            float right = left + LINE_THICK;
            float top = 0;
            float bottom = height;

            canvas.drawRect(left, top, right, bottom, gridPaint);

            // horizontal lines
            float left2 = 0;
            float right2 = width;
            float top2 = eltH * (i + 1);
            float bottom2 = top2 + LINE_THICK;

            canvas.drawRect(left2, top2, right2, bottom2, gridPaint);
        }
    }

    private void drawElt(Canvas canvas, char c, int x, int y) {
        if (c == 'O') {
            players_o[x * 3 + y].setVisibility(View.VISIBLE);



        } else if (c == 'X') {
            players_x[x * 3 + y].setVisibility(View.VISIBLE);
        }
    }

}
