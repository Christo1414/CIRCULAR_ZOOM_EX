package com.example.a490_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.strictmode.CredentialProtectedWhileLockedViolation;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Vector;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private View TranslationView = null;
    private TextView X_Value = null;
    private TextView Y_Value = null;
    private TextView X = null;
    private TextView Y = null;
    private TextView txtMAG = null;
    private TextView txtANG = null;
    private TextView I_Value = null;
    private TextView Zoom = null;
    private TextView magtext = null;
    private TextView angleText = null;
    private float[][] XY_COORDS = new float [10][2];

    /** SNIIP */
    private float magnitudeInitial = 0;
    private float magnitudeSample = 0;
    private float magnitudeFinal = 0;
    private float angleInitial = 0;
    private float angleSample = 0;
    private float angleFinal = 0;
    private boolean isZoomIn = false;
    private boolean isAngleIncreasing = false;
    private boolean isMagnitudeIncreasing = false;
    /** SNIIP */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TranslationView = (View) findViewById(R.id.Layout1);
        X_Value = (TextView) findViewById(R.id.X_Value);
        Y_Value = (TextView) findViewById(R.id.Y_Value);
        txtANG = (TextView) findViewById(R.id.txtANG);
        txtMAG = (TextView) findViewById(R.id.txtMAG);
        X = (TextView) findViewById(R.id.X);
        Y = (TextView) findViewById(R.id.Y);
        I_Value = (TextView) findViewById(R.id.I_Value);
        Zoom = (TextView) findViewById(R.id.Zoomin);
        magtext = (TextView) findViewById(R.id.magnitude);
        angleText = (TextView) findViewById(R.id.angle);

        TranslationView.setOnTouchListener(new View.OnTouchListener()     {

            float x_0 = 0;
            float y_0 = 0;
            float x = 0;
            float y = 0;
            float i = 0;
            int index = 0;

            String x_vectors_array = null;
            String y_vectors_array = null;

            @Override
            public boolean onTouch(View V, MotionEvent event){
                x = event.getX();
                y = event.getY();

                if(event.getAction() == MotionEvent.ACTION_DOWN)    {
                    x_0 = x;
                    y_0 = y;
                    i = 0;
                    XY_COORDS = new float [10][2];
                }

                XY_COORDS[index][0] = x;
                XY_COORDS[index][1] = y;
                if(x_vectors_array != null) {
                    x_vectors_array = x_vectors_array +"  :::  "+ Integer.toString((int)x);
                    y_vectors_array = y_vectors_array +"  :::  "+ Integer.toString((int)y);
                }else{
                    x_vectors_array = Integer.toString((int)x);
                    y_vectors_array = Integer.toString((int)y);
                }

                index++;
                if (index == 10) {


                    isZoomIn = MagAngleZoom();      /** Call function to determine zoom direction */


                    magtext.setText(Float.toString(magnitudeInitial) + "  :::  " + Float.toString(magnitudeSample) + "  :::  " + Float.toString(magnitudeFinal));
                    angleText.setText(Float.toString(angleInitial) + "  :::  " + Float.toString(angleSample) + "  :::  " + Float.toString(angleFinal));
                    index = 0;

                    x_vectors_array = Float.toString(x);
                    y_vectors_array = Float.toString(y);

                    XY_COORDS = new float [10][2];
                }


                if(isZoomIn){
                    Zoom.setText("Zooming in");
                }else{
                    Zoom.setText("Zooming out");
                }

                X_Value.setText(x_vectors_array);
                Y_Value.setText(y_vectors_array);
                X.setText("X:  " + Float.toString(x));
                Y.setText("Y:  " + Float.toString(y));
                I_Value.setText(Float.toString(i));
                i++;

                return true;
            }
        });
    }


    /** MagAngleZoom() and Test1() contain the logic */

    private boolean MagAngleZoom() {
        float x1, y1, x2, y2, xs, ys;

        x1 = XY_COORDS[0][0];
        y1 = XY_COORDS[0][1];
        xs = XY_COORDS[XY_COORDS.length - (XY_COORDS.length / 2)][0];
        ys = XY_COORDS[XY_COORDS.length - (XY_COORDS.length / 2)][1];
        x2 = XY_COORDS[XY_COORDS.length - 1][0];
        y2 = XY_COORDS[XY_COORDS.length - 1][1];

        magnitudeInitial = (float) Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2));
        magnitudeSample = (float) Math.sqrt(Math.pow(xs, 2) + Math.pow(ys, 2));
        magnitudeFinal = (float) Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2));

        if (y1 != 0) {
            angleInitial = (float) Math.atan(x1 / y1);
        }
        if (ys != 0) {
            angleSample = (float) Math.atan(xs / ys);
        }
        if (y2 != 0) {
            angleFinal = (float) Math.atan(x2 / y2);
        }
        angleInitial = (float) Math.toDegrees(angleInitial);
        angleSample = (float) Math.toDegrees(angleSample);
        angleFinal = (float) Math.toDegrees(angleFinal);


        Test1();


        return isZoomIn;
    }

    private void Test1(){

        if (angleFinal < angleInitial) {
//            if(isAngleIncreasing){
//                if (isMagnitudeIncreasing) {
//                    isZoomIn = true;
//                }
//            }
            txtANG.setText("Angle: Decrease");
            isAngleIncreasing = false;
        }

        if (angleFinal > angleInitial) {
//            if(!isAngleIncreasing){
//                if (isMagnitudeIncreasing) {
//                    isZoomIn = false;
//                }
//            }
            txtANG.setText("Angle: Increase");
            isAngleIncreasing = true;
        }

        if (magnitudeFinal < magnitudeInitial) {
            if(isMagnitudeIncreasing){
                if (isAngleIncreasing) {
                    isZoomIn = false;
                }//else{isZoomIn = true;}
            }
            txtMAG.setText("Magnitude: Decrease");
            isMagnitudeIncreasing = false;
        }

        if (magnitudeFinal > magnitudeInitial) {
            if(!isMagnitudeIncreasing){
                if (isAngleIncreasing) {
                    isZoomIn = true;
                }//else{isZoomIn = false;}
            }
            txtMAG.setText("Magnitude: Increase");
            isMagnitudeIncreasing = true;
        }

    }

    /** performance not as good */
    private void Test2(){


        if (angleSample < angleInitial || angleFinal < angleInitial) {
//            if(isAngleIncreasing){
//                if (isMagnitudeIncreasing) {
//                    isZoomIn = true;
//                }
//            }

            txtANG.setText("Angle: Decrease");
            isAngleIncreasing = false;

        }

        if (angleSample > angleInitial || angleFinal > angleInitial) {
//            if(!isAngleIncreasing){
//                if (isMagnitudeIncreasing) {
//                    isZoomIn = false;
//                }
//            }

            txtANG.setText("Angle: Increase");
            isAngleIncreasing = true;

        }

        if (magnitudeSample < magnitudeInitial || magnitudeFinal < magnitudeInitial) {
            if(isMagnitudeIncreasing){
                if (isAngleIncreasing) {
                    isZoomIn = false;
                }//else{isZoomIn = false;}
            }

            txtMAG.setText("Magnitude: Decrease");
            isMagnitudeIncreasing = false;
        }
        if (magnitudeSample > magnitudeInitial || magnitudeFinal > magnitudeInitial) {
            if(!isMagnitudeIncreasing){
                if (isAngleIncreasing) {
                    isZoomIn = true;
                }//else{isZoomIn = false;}
            }

            txtMAG.setText("Magnitude: Increase");
            isMagnitudeIncreasing = true;
        }

    }

    /** performance not as good */
    private void Test3(){

        int zoomScore = 0;

        if (angleFinal < angleInitial) {
            if(isAngleIncreasing){
                if (isMagnitudeIncreasing) {

                    //isZoomIn = true;
                    zoomScore++;

                }
            }

            txtANG.setText("Angle: Decrease");
            isAngleIncreasing = false;

        }

        if (angleFinal > angleInitial) {
            if(!isAngleIncreasing){
                if (isMagnitudeIncreasing) {

                    zoomScore--;


                }
            }

            txtANG.setText("Angle: Increase");
            isAngleIncreasing = true;

        }

        if (magnitudeFinal < magnitudeInitial) {
            if(isMagnitudeIncreasing){
                if (isAngleIncreasing) {

                    zoomScore--;

                }
            }

            txtMAG.setText("Magnitude: Decrease");
            isMagnitudeIncreasing = false;
        }
        if (magnitudeFinal > magnitudeInitial) {
            if(!isMagnitudeIncreasing){
                if (isAngleIncreasing) {

                    zoomScore++;


                }
            }

            txtMAG.setText("Magnitude: Increase");
            isMagnitudeIncreasing = true;
        }

        if(zoomScore>0){
            isZoomIn = true;
        }else if(zoomScore <0){
            isZoomIn = false;
        }

    }


}

