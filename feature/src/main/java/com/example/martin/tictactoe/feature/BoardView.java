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

        players_x [0] = activity.findViewById(R.id.p_o);
        players_x [1] = activity.findViewById(R.id.p_o2);
        players_x [2] = activity.findViewById(R.id.p_o3);
        players_x [3] = activity.findViewById(R.id.p_o4);
        players_x [4] = activity.findViewById(R.id.p_o5);
        players_x [5] = activity.findViewById(R.id.p_o6);
        players_x [6] = activity.findViewById(R.id.p_o7);
        players_x [7] = activity.findViewById(R.id.p_o8);
        players_x [8] = activity.findViewById(R.id.p_o9);

        players_o [0] = activity.findViewById(R.id.p_x);
        players_o [1] = activity.findViewById(R.id.p_x2);
        players_o [2] = activity.findViewById(R.id.p_x3);
        players_o [3] = activity.findViewById(R.id.p_x4);
        players_o [4] = activity.findViewById(R.id.p_x5);
        players_o [5] = activity.findViewById(R.id.p_x6);
        players_o [6] = activity.findViewById(R.id.p_x7);
        players_o [7] = activity.findViewById(R.id.p_x8);
        players_o [8] = activity.findViewById(R.id.p_x9);
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


/*
        ImageView img1o = (ImageView) activity.findViewById(R.id.p_o);
        img1o.setVisibility(View.VISIBLE);
        ImageView img2o = (ImageView) activity.findViewById(R.id.p_o2);
        img2o.setVisibility(View.VISIBLE);
        ImageView img3o = (ImageView) activity.findViewById(R.id.p_o3);
        img3o.setVisibility(View.VISIBLE);
        ImageView img4o = (ImageView) activity.findViewById(R.id.p_o4);
        img4o.setVisibility(View.VISIBLE);
        ImageView img5o = (ImageView) activity.findViewById(R.id.p_o5);
        img5o.setVisibility(View.VISIBLE);
        ImageView img6o = (ImageView) activity.findViewById(R.id.p_o6);
        img6o.setVisibility(View.VISIBLE);
        ImageView img7o = (ImageView) activity.findViewById(R.id.p_o7);
        img7o.setVisibility(View.VISIBLE);
        ImageView img8o = (ImageView) activity.findViewById(R.id.p_o8);
        img8o.setVisibility(View.VISIBLE);
        ImageView img9o = (ImageView) activity.findViewById(R.id.p_o9);
        img9o.setVisibility(View.VISIBLE);
*/

//        ImageView img1x = (ImageView) activity.findViewById(R.id.p_x);
//        img1x.setVisibility(View.VISIBLE);
//        ImageView img2x = (ImageView) activity.findViewById(R.id.p_x2);
//        img2x.setVisibility(View.VISIBLE);
//        ImageView img3x = (ImageView) activity.findViewById(R.id.p_x3);
//        img3x.setVisibility(View.VISIBLE);
//        ImageView img4x = (ImageView) activity.findViewById(R.id.p_x4);
//        img4x.setVisibility(View.VISIBLE);
//        ImageView img5x = (ImageView) activity.findViewById(R.id.p_x5);
//        img5x.setVisibility(View.VISIBLE);
//        ImageView img6x = (ImageView) activity.findViewById(R.id.p_x6);
//        img6x.setVisibility(View.VISIBLE);
//        ImageView img7x = (ImageView) activity.findViewById(R.id.p_x7);
//        img7x.setVisibility(View.VISIBLE);
//        ImageView img8x = (ImageView) activity.findViewById(R.id.p_x8);
//        img8x.setVisibility(View.VISIBLE);
//        ImageView img9x = (ImageView) activity.findViewById(R.id.p_x9);
//        img9x.setVisibility(View.VISIBLE);



  /*      ImageView img1o = (ImageView) activity.findViewById(R.id.p_o);
        img1o.setX(0);
        img1o.setY(0);
        img1o.setVisibility(View.VISIBLE);

        ImageView img3o = (ImageView) activity.findViewById(R.id.p_o3);
        img3o.setX(width/3);
        img3o.setY(0);
        img3o.setVisibility(View.VISIBLE);

        ImageView img2o = (ImageView) activity.findViewById(R.id.p_o2);
        img2o.setX(width/3);
        img2o.setY(height/3);
        img2o.setVisibility(View.VISIBLE);

        ImageView img4o = (ImageView) activity.findViewById(R.id.p_o4);
        img4o.setX(width/3);
        img4o.setY(height/3*2);
        img4o.setVisibility(View.VISIBLE);

        ImageView img5o = (ImageView) activity.findViewById(R.id.p_o5);
        img5o.setX(0);
        img5o.setY(height/3);
        img5o.setVisibility(View.VISIBLE);

        ImageView img6o = (ImageView) activity.findViewById(R.id.p_o6);
        img6o.setX(0);
        img6o.setY(height/3*2);
        img6o.setVisibility(View.VISIBLE);

        ImageView img7o = (ImageView) activity.findViewById(R.id.p_o7);
        img7o.setX(width/3*2);
        img7o.setY(0);
        img7o.setVisibility(View.VISIBLE);

        ImageView img8o = (ImageView) activity.findViewById(R.id.p_o8);
        img8o.setX(width/3*2);
        img8o.setY(height/3);
        img8o.setVisibility(View.VISIBLE);

        ImageView img9o = (ImageView) activity.findViewById(R.id.p_o9);
        img9o.setX(width/3*2);
        img9o.setY(height/3*2);
        img9o.setVisibility(View.VISIBLE);
*/
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                drawElt(canvas, gameEngine.getField(i, j), i, j);
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
            float cx = (eltW * x) + eltW / 2;
            float cy = (eltH * y) + eltH / 2;

            canvas.drawCircle(cx, cy, Math.min(eltW, eltH) / 2 - ELT_MARGIN * 2, oPaint);

        //    ImageView img = (ImageView) activity.findViewById(R.id.p_o);
          //  img.setVisibility(View.VISIBLE);

            //img.setX(cx - img.getWidth() / 2);
            //img.setY(cy - img.getHeight() / 2);


        } else if (c == 'X') {
            float startX = (eltW * x) + ELT_MARGIN;
            float startY = (eltH * y) + ELT_MARGIN;
            float endX = startX + eltW - ELT_MARGIN * 2;
            float endY = startY + eltH - ELT_MARGIN;

            canvas.drawLine(startX, startY, endX, endY, xPaint);

            float startX2 = (eltW * (x + 1)) - ELT_MARGIN;
            float startY2 = (eltH * y) + ELT_MARGIN;
            float endX2 = startX2 - eltW + ELT_MARGIN * 2;
            float endY2 = startY2 + eltH - ELT_MARGIN;

            canvas.drawLine(startX2, startY2, endX2, endY2, xPaint);

            //ImageView img = (ImageView) activity.findViewById(R.id.p_x);
            //img.setVisibility(View.VISIBLE);

            //img.setX(startX);
            //img.setY(endY);



        }
    }

}
