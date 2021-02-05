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
    private float[][] XY_COORDS = new float[10][2];

    /**
     * SNIIP
     */
    private float magnitudeInitial = 0;
    private float magnitudeSample = 0;
    private float magnitudeFinal = 0;
    private float angleInitial = 0;
    private float angleSample = 0;
    private float angleFinal = 0;
    private boolean isZoomIn = false;
    private boolean isAngleIncreasing = false;
    private boolean isMagnitudeIncreasing = false;
    /**
     * SNIIP
     */

    private int zoomType;
    private boolean Zooming;
    private int circleCounter;
    boolean isZoomIN1;
    boolean isZoomIN2;
    boolean isZoomOUT1;
    boolean isZoomOUT2;


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

        TranslationView.setOnTouchListener(new View.OnTouchListener() {

            float x_0 = 0;
            float y_0 = 0;
            float x = 0;
            float y = 0;
            float i = 0;
            int index = 0;

            String x_vectors_array = null;
            String y_vectors_array = null;

            @Override
            public boolean onTouch(View V, MotionEvent event) {
                x = event.getX();
                y = event.getY();

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x_0 = x;
                    y_0 = y;
                    i = 0;
                    XY_COORDS = new float[10][2];
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Zooming = false;
                }

                XY_COORDS[index][0] = x;
                XY_COORDS[index][1] = y;
                if (x_vectors_array != null) {
                    x_vectors_array = x_vectors_array + "  :::  " + Integer.toString((int) x);
                    y_vectors_array = y_vectors_array + "  :::  " + Integer.toString((int) y);
                } else {
                    x_vectors_array = Integer.toString((int) x);
                    y_vectors_array = Integer.toString((int) y);
                }

                index++;
                if (index == 10) {


                    zoomType = MagAngleZoom();      /** Call function to determine zoom direction */


                    magtext.setText(Float.toString(magnitudeInitial) + "  :::  " + Float.toString(magnitudeSample) + "  :::  " + Float.toString(magnitudeFinal));
                    angleText.setText(Float.toString(angleInitial) + "  :::  " + Float.toString(angleSample) + "  :::  " + Float.toString(angleFinal));
                    index = 0;

                    x_vectors_array = Float.toString(x);
                    y_vectors_array = Float.toString(y);

                    XY_COORDS = new float[10][2];
                }


                if (zoomType == 0) {
                    Zoom.setText("Linear Motion");

                } else if (zoomType == 1) {
                    Zoom.setText("Zooming in");

                } else {
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


    /**
     * MagAngleZoom() and Test1() contain the logic
     */

    private int MagAngleZoom() {
        // Returns 0 for linear, 1 for ZoomIn, 2 for ZoomOut

        float x1, y1, x2, y2, xs, ys;


        x1 = XY_COORDS[0][0];
        y1 = XY_COORDS[0][1];
        xs = XY_COORDS[XY_COORDS.length - (XY_COORDS.length / 2)][0];
        ys = XY_COORDS[XY_COORDS.length - (XY_COORDS.length / 2)][1];
        x2 = XY_COORDS[XY_COORDS.length - 1][0];
        y2 = XY_COORDS[XY_COORDS.length - 1][1];


        // DETERMINE IF MOTION IS LINEAR
        if (!Zooming) {
            if (Math.abs((x2 - x1) * 100 / x2) < 5 || Math.abs((y2 - y1) * 100 / y2) < 5) {
                return 0;
            }
        }


        if (circleCounter == 4) {
            circleCounter = 0;
            if (isZoomIN1 && isZoomIN2) {
                Zooming = true;
            } else if (isZoomOUT1 && isZoomOUT2) {
                Zooming = true;
            } else {
                return 0;
            }
        }


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


        if (Zooming) {
            DirectionOfRotation2();
            if (isZoomIn) {
                return 1;
            } else {
                return 2;
            }
        } else {
            DirectionOfRotation();
            return 0;
        }

    }

    private void DirectionOfRotation() {

        if (angleFinal < angleInitial) {
            if (isAngleIncreasing) {
                if (isMagnitudeIncreasing) {
                    //   isZoomIn = true;
                    isZoomIN1 = true;
                    isZoomOUT1 = false;
                }
            }
            txtANG.setText("Angle: Decrease");
            isAngleIncreasing = false;
            circleCounter++;
        }

        if (angleFinal > angleInitial) {
            if (!isAngleIncreasing) {
                if (isMagnitudeIncreasing) {
                    //   isZoomIn = false;
                    isZoomOUT1 = true;
                    isZoomIN1 = false;
                }
            }
            txtANG.setText("Angle: Increase");
            isAngleIncreasing = true;
            circleCounter++;
        }

        if (magnitudeFinal < magnitudeInitial) {
            if (isMagnitudeIncreasing) {
                if (isAngleIncreasing) {
                    //   isZoomIn = false;
                    isZoomOUT2 = true;
                    isZoomIN2 = false;
                }
            }
            txtMAG.setText("Magnitude: Decrease");
            isMagnitudeIncreasing = false;
            circleCounter++;

        }

        if (magnitudeFinal > magnitudeInitial) {
            if (!isMagnitudeIncreasing) {
                if (isAngleIncreasing) {
                    //   isZoomIn = true;
                    isZoomIN2 = true;
                    isZoomOUT2 = false;
                }
            }
            txtMAG.setText("Magnitude: Increase");
            isMagnitudeIncreasing = true;
            circleCounter++;

        }

    }

    private void DirectionOfRotation2() {

        if (angleFinal < angleInitial) {
            if (isAngleIncreasing) {
                if (isMagnitudeIncreasing) {
                    isZoomIn = true;

                }
            }
            txtANG.setText("Angle: Decrease");
            isAngleIncreasing = false;

        }

        if (angleFinal > angleInitial) {
            if (!isAngleIncreasing) {
                if (isMagnitudeIncreasing) {
                    isZoomIn = false;

                }
            }
            txtANG.setText("Angle: Increase");
            isAngleIncreasing = true;
        }

        if (magnitudeFinal < magnitudeInitial) {
            if (isMagnitudeIncreasing) {
                if (isAngleIncreasing) {
                    isZoomIn = false;

                }
            }
            txtMAG.setText("Magnitude: Decrease");
            isMagnitudeIncreasing = false;

        }

        if (magnitudeFinal > magnitudeInitial) {
            if (!isMagnitudeIncreasing) {
                if (isAngleIncreasing) {
                    isZoomIn = true;

                }
            }
            txtMAG.setText("Magnitude: Increase");
            isMagnitudeIncreasing = true;


        }

    }

    /**
     * performance not as good
     */
    private void Test2() {


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
            if (isMagnitudeIncreasing) {
                if (isAngleIncreasing) {
                    isZoomIn = false;
                }//else{isZoomIn = false;}
            }

            txtMAG.setText("Magnitude: Decrease");
            isMagnitudeIncreasing = false;
        }
        if (magnitudeSample > magnitudeInitial || magnitudeFinal > magnitudeInitial) {
            if (!isMagnitudeIncreasing) {
                if (isAngleIncreasing) {
                    isZoomIn = true;
                }//else{isZoomIn = false;}
            }

            txtMAG.setText("Magnitude: Increase");
            isMagnitudeIncreasing = true;
        }

    }

    /**
     * performance not as good
     */
    private void Test3() {

        int zoomScore = 0;

        if (angleFinal < angleInitial) {
            if (isAngleIncreasing) {
                if (isMagnitudeIncreasing) {

                    //isZoomIn = true;
                    zoomScore++;

                }
            }

            txtANG.setText("Angle: Decrease");
            isAngleIncreasing = false;

        }

        if (angleFinal > angleInitial) {
            if (!isAngleIncreasing) {
                if (isMagnitudeIncreasing) {

                    zoomScore--;


                }
            }

            txtANG.setText("Angle: Increase");
            isAngleIncreasing = true;

        }

        if (magnitudeFinal < magnitudeInitial) {
            if (isMagnitudeIncreasing) {
                if (isAngleIncreasing) {

                    zoomScore--;

                }
            }

            txtMAG.setText("Magnitude: Decrease");
            isMagnitudeIncreasing = false;
        }
        if (magnitudeFinal > magnitudeInitial) {
            if (!isMagnitudeIncreasing) {
                if (isAngleIncreasing) {

                    zoomScore++;


                }
            }

            txtMAG.setText("Magnitude: Increase");
            isMagnitudeIncreasing = true;
        }

        if (zoomScore > 0) {
            isZoomIn = true;
        } else if (zoomScore < 0) {
            isZoomIn = false;
        }

    }


}

