package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**This class acts as the class for the second activity.
 *Displays advice and image to the user based on BMI.
 * @author Anthony Pensak and Cesar Herrera
 */
public class Activity2 extends AppCompatActivity {

    private TextView displayAdvice;
    private ImageView imageView;


    /**
     * Called when the second activity is starting. This method initializes the data members and other startup activities.
     * @param savedInstanceState Bundle  If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     * @author Anthony Pensak and Cesar Herrera
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        displayAdvice = findViewById(R.id.bmi_index);

        setTitle("Advice Based on the BMI");
        imageView = (ImageView) findViewById(R.id.BMIimage);


        Intent intent = getIntent();
        Double bmiResult = intent.getDoubleExtra(MainActivity.EXTRA_BMI,0);
        displayAdvice(displayAdvice, bmiResult);

    }

    /**
     * This method displays the advice and image to the user based on the BMI
     * @param temp Temp TextView object
     * @param userBMI The users BMI for which we output text or change image.
     * @author Anthony Pensak and Cesar Herrera
     */
    private void displayAdvice(TextView temp, Double userBMI){
        if (userBMI >= 30){
            temp.setText("Obese");
            imageView.setImageResource(R.drawable.ob);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        else if (userBMI < 30 && userBMI >=25){
            temp.setText("Overweight");
            imageView.setImageResource(R.drawable.ow);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        else if (userBMI < 25 && userBMI >= 18.5){
            temp.setText("Normal");
            imageView.setImageResource(R.drawable.hw);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        else{
            temp.setText("Underweight");
            imageView.setImageResource(R.drawable.uw);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }
}
