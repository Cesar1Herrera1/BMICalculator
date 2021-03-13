package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/**This class acts as the class for the main activity.
 *Takes in the height, weight and button input.git
 * @author Anthony Pensak and Cesar Herrera
 */
public class MainActivity extends AppCompatActivity {
    //Variables for calculation
    public static final String EXTRA_BMI = "com.example.bmicalculator.example.EXTRA_BMI";
    private final int ENGLISH = 703;
    private double weight;
    private double height;
    private double bmi;

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private EditText inputWeight;
    private EditText inputHeight;

    private TextView outputBMI;

    private Button calculateBMIButton;
    private Button getAdviceButton;


    /**
     *Called when the main activity is starting. This method initializes the data members and other startup activities.
     * @param savedInstanceState Bundle If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     * @author Anthony Pensak and Cesar Herrera
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radio_group);
        outputBMI = findViewById(R.id.bmi_result);

        inputWeight = findViewById(R.id.weight_input);
        inputHeight = findViewById(R.id.height_input);

        //setting character length to 5 max
        inputWeight.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        inputHeight.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});

        //To make sure fields are populated before user can click
        inputWeight.addTextChangedListener(confirmTextInput);
        inputHeight.addTextChangedListener(confirmTextInput);

        outputBMI = findViewById(R.id.bmi_result);

        getAdviceButton = findViewById(R.id.get_advice_button);

        calculateBMIButton = findViewById(R.id.calculate_bmi_button);
        calculateBMIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weight = Double.valueOf(inputWeight.getText().toString());
                height = Double.valueOf(inputHeight.getText().toString());

                //checks to see which radio button is selected
                checkRadioSelection(v);
                // Calculate BMI
                bmi = calculateBMI(radioButton, weight, height);

                // Output calculated BMI
                outputBMI.setText(String.format("%.2f", bmi));

                if (Double.parseDouble(outputBMI.getText().toString()) >= 0.0){
                    getAdviceButton.setEnabled(true);
                }

                getAdviceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openAdvice();
                    }
                });

                //showToast(String.valueOf(weight));
                //showToast(String.valueOf(height));
            }
        });

    }

    /**
     * This method serves to open the second activity using the Intent object for the advice.
     * @author Anthony Pensak and Cesar Herrera
     */
    public void openAdvice(){
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra(EXTRA_BMI,bmi);
        startActivity(intent);
    }

    /**
     * This method verifies that the EditText input areas have gotten valid input enabling the get BMI and advice button(s),
     * otherwise the button is disabled.
     * @author Anthony Pensak and Cesar Herrera
     */
    private TextWatcher confirmTextInput = new TextWatcher(){

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String weightInput = inputWeight.getText().toString().trim();
            String heightInput = inputHeight.getText().toString().trim();

            try{

                if(!weightInput.isEmpty() && weightInput.equals(".")){
                    inputWeight.setError("Error");
                    throw new IllegalArgumentException("ERROR: First value cannot be a \".\"");
                }
               else if(!heightInput.isEmpty() && heightInput.equals(".")){
                    inputHeight.setError("Error");
                    throw new IllegalArgumentException("ERROR: First value cannot be a \".\"");
                }

                if(!weightInput.isEmpty()&&Double.parseDouble(weightInput)==0){
                    inputWeight.setError("Error");
                    showToast("Weight cannot be 0!");
                }

                if(!heightInput.isEmpty()&&Double.parseDouble(heightInput)==0){
                    inputHeight.setError("Error");
                    showToast("Height cannot be 0!");
                }
            }
            catch (IllegalArgumentException e){
                showToast("Value cannot be \".\"");
                return;
            }

            calculateBMIButton.setEnabled(!weightInput.isEmpty() && !heightInput.isEmpty() &&
                                            Double.parseDouble(weightInput)!=0 && Double.parseDouble(heightInput)!=0);
            getAdviceButton.setEnabled(false);

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    /**
     * This method gets the ID of the selected radio button and sets the proper hint.
     * @param v View object
     * @author Anthony Pensak and Cesar Herrera
     */
    public void checkRadioSelection(View v){
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);

        if (findViewById(radioID) == findViewById(R.id.english_button)){
            inputWeight.setHint("enter weight in pounds");
            inputHeight.setHint("enter height in inches");
        }
        else{
            inputWeight.setHint("enter weight in kilograms");
            inputHeight.setHint("enter height in meters");
        }
    }

    /**
     * This method calculates the BMI by taking the entered weight, height and returns the BMI
     * @param rb RadioButton object to see which one was selected
     * @param userWeight User's weight to calculate BMI
     * @param userHeight User's height to calculate BMI
     * @return Calculated BMI Calculated BMI to be returned
     * @author Anthony Pensak and Cesar Herrera
     */
    private double calculateBMI(RadioButton rb, double userWeight, double userHeight){
        showToast("You can now click on the \"Get Advice\" button!");
        if(rb == findViewById(R.id.english_button)){
            return (userWeight * ENGLISH) / (userHeight * userHeight);
        }
        else {
            return (userWeight) / (userHeight * userHeight);
        }

    }

    /**
     * This method displays Toast messages to the user.
     * @param text Text to be displayed using Toast
     * @author Anthony Pensak and Cesar Herrera
     */
    private void showToast(String text){
        Toast.makeText(MainActivity.this,text, Toast.LENGTH_SHORT).show();
    }
}
