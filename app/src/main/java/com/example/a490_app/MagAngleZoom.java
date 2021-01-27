package com.example.a490_app;

public class MagAngleZoom {

    private float[][] XY_COORDS = new float [10][2];

    /**     Final draft, not part of MainActivity   */

    private boolean MagnitudeAngleZoom(){
        // returns true for clockwise (zoom in), false for CCW

        float x1, y1,x2, y2;
        float magnitudeInitial = 0;
        float magnitudeFinal = 0;
        float angleInitial = 0;
        float angleFinal = 0;
        boolean isZoomCW = false;
        boolean isAngleIncreasing = false;
        boolean isMagnitudeIncreasing = false;

        x1 = XY_COORDS[0][0];
        y1 = XY_COORDS[0][1];
        x2 = XY_COORDS[XY_COORDS.length-1][0];
        y2 = XY_COORDS[XY_COORDS.length-1][1];

        magnitudeInitial = (float) Math.sqrt(Math.pow(x1,2) + Math.pow(y1,2));
        magnitudeFinal = (float) Math.sqrt(Math.pow(x2,2) + Math.pow(y2,2));
        if (y1 != 0) {
            angleInitial = (float) Math.atan(x1/y1);    }
        if (y2 != 0) {
            angleFinal = (float) Math.atan(x2/y2);      }


        if (angleFinal < angleInitial) {
//            if(isAngleIncreasing){
//                if (isMagnitudeIncreasing) {
//                    isZoomCW = true;
//                }
//            }
            isAngleIncreasing = false;
        }

        if (angleFinal > angleInitial) {
//            if(!isAngleIncreasing){
//                if (isMagnitudeIncreasing) {
//                    isZoomCW = false;
//                }
//            }
            isAngleIncreasing = true;
        }

        if (magnitudeFinal < magnitudeInitial) {
            if(isMagnitudeIncreasing){
                if (isAngleIncreasing) {
                    isZoomCW = false;
                }//else{isZoomIn = true;}
            }
            isMagnitudeIncreasing = false;
        }
        if (magnitudeFinal > magnitudeInitial) {
            if(!isMagnitudeIncreasing){
                if (isAngleIncreasing) {
                    isZoomCW = true;
                }//else{isZoomIn = false;}
            }
            isMagnitudeIncreasing = true;
        }

        return isZoomCW;
    }






}
