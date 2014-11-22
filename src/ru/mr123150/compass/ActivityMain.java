package ru.mr123150.compass;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityMain extends Activity implements SensorEventListener {

    protected TextView dirText;
    protected ImageView compassImage;
    protected SensorManager sensorManager;
    
    private int currentAngle=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dirText=(TextView)findViewById(R.id.dir_text);
        compassImage=(ImageView)findViewById(R.id.compass_image);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        int angle = Math.round(event.values[0]);

        dirText.setText(Integer.toString(angle));

        // create a rotation animation (reverse turn angle angles)
        RotateAnimation ra = new RotateAnimation(
                currentAngle,
                -angle,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        compassImage.startAnimation(ra);
        currentAngle = -angle;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

}
