package com.example.anddroid.touchanddrag;import android.graphics.Point;import android.os.Bundle;import android.os.CountDownTimer;import android.os.Handler;import android.support.v7.app.AppCompatActivity;import android.util.Log;import android.view.Display;import android.view.MotionEvent;import android.view.View;import android.widget.ImageView;import android.widget.RelativeLayout;import android.widget.TextView;import java.util.Random;public class MainActivity extends AppCompatActivity {    Handler handler = new Handler();    Runnable runnable = new Runnable() {        @Override        public void run() {            changeImage();        }    };    ImageView image;    ImageView image2;    int[] circleArray = new int[]{R.drawable.ball1, R.drawable.ball2, R.drawable.ball3, R.drawable.ball4,            R.drawable.ball5, R.drawable.ball6, R.drawable.ball7};    int[] squareArray = new int[]{R.drawable.square1, R.drawable.square2, R.drawable.square3, R.drawable.square4,            R.drawable.square5, R.drawable.square6, R.drawable.square7};    TextView text_score;    private int score = 0;    Random random = new Random();    RelativeLayout.LayoutParams layoutParams;    private int xDelta;    private int yDelta;    //ekran boyutunun değerlerini alır    Display display;    Point size;    int width;    int height;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        image = (ImageView) findViewById(R.id.imageView);        image2 = (ImageView) findViewById(R.id.imageView2);        text_score = (TextView) findViewById(R.id.score);        //ekran boyutunu hesaplar        display = getWindowManager().getDefaultDisplay();        size = new Point();        display.getSize(size);        width = size.x;        height = size.y;        runnable.run();    }    private void changeImage() {        int randomPosition = random.nextInt(300);        Log.v("MainActivity", "randomPosition: " + randomPosition);        int randomIndex = random.nextInt(7);        Log.v("MainActivity", "randomIndex: " + randomIndex);        int rndImage = random.nextInt(2) + 1;        Log.v("MainActivity", "randomImage: " + rndImage);        if (rndImage == 1) {            image.setImageResource(circleArray[randomIndex]);            image.setVisibility(View.VISIBLE);            countDown(image, 1 , image2);            changeImagePosition(image);        } else if (rndImage == 2) {            image2.setImageResource(squareArray[randomIndex]);            image2.setVisibility(View.VISIBLE);            countDown(image, 1 , image2);            changeImagePosition(image2);        }        handler.postDelayed(runnable, 3000);    }    public void countDown(final ImageView img, final int i , final ImageView img2) {        new CountDownTimer(2000, 100) {            @Override            public void onTick(long millisUntilFinished) {                img.setOnClickListener(new View.OnClickListener() {                    @Override                    public void onClick(View v) {                        score++;                        text_score.setText("score: " + score * 1);                        Log.v("Score: ", "score: " + score);                    }                });                img2.setOnTouchListener(new View.OnTouchListener() {                    @Override                    public boolean onTouch(View v, MotionEvent event) {                        layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();                        int x_cord = (int) event.getRawX();                        int y_cord = (int) event.getRawY();                        switch (event.getAction()) {                            case MotionEvent.ACTION_DOWN:                                xDelta = x_cord - layoutParams.leftMargin;                                yDelta = y_cord - layoutParams.topMargin;                                break;                            case MotionEvent.ACTION_UP:                                // v.performClick();                                break;                            case MotionEvent.ACTION_POINTER_DOWN:                                break;                            case MotionEvent.ACTION_POINTER_UP:                                break;                            case MotionEvent.ACTION_MOVE:                                score++;                                text_score.setText("score: " + score * 1);                                Log.v("MainActivity", "Coordinates: " + layoutParams.leftMargin+ " " + layoutParams.topMargin);                                Log.v("MainActivity", "Coordinates: " + x_cord+ " " + y_cord);                                if (x_cord > width) {                                    x_cord = width;                                }                                if (y_cord > height) {                                    y_cord = height;                                }                                Log.v("MainActivity", "Coordinates2: " + x_cord+ " " + y_cord);                                layoutParams.leftMargin = x_cord - xDelta;                                layoutParams.topMargin = y_cord - yDelta;                                layoutParams.rightMargin = -250;                                layoutParams.bottomMargin = -250;                                img2.setLayoutParams(layoutParams);                                break;                            default:                                break;                        }                        return true;                    }                });                Log.v("onClick", "set");            }            @Override            public void onFinish() {                img.setVisibility(View.GONE);                img2.setVisibility(View.GONE);            }        }.start();    }        private void changeImagePosition(ImageView img) {        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) img.getLayoutParams();        int rndInt = random.nextInt(width - img.getWidth() - 32);        int rndInt2 = random.nextInt(height - 2 * img.getHeight() - 32);        params.setMargins(rndInt, rndInt2, 0, 0);        img.setLayoutParams(params);    }}