package com.example.giso.tadm_sharedpreferences;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener{

   /* EditText email;
    RadioButton male,female;

    */

//https://www.codeproject.com/Articles/1121942/Android-Input-and-Shared-Preferences
    private String email;
    private String genero;
    private String hobbies;
    private String zodiaco;
    final String NAME = "";
    RadioGroup radioGender;
    CheckBox coding, writting, jogging;
    Spinner spinnerZodiacal;
    Button saveMe,getMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        email = findViewById(R.id.email);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        */
        coding = findViewById(R.id.coding);
        writting = findViewById(R.id.writting);
        jogging = findViewById(R.id.jogging);
        spinnerZodiacal = findViewById(R.id.spinZodiacal);
        saveMe = findViewById(R.id.saveMe);
        getMe = findViewById(R.id.getMe);
        radioGender = findViewById(R.id.radioGender);
        radioGender.setOnCheckedChangeListener(this);

        //carga de ArrayList signoZodiacal
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.signoZodiacal/*String-array*/, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZodiacal.setAdapter(adapter);
        spinnerZodiacal.setOnItemSelectedListener(this);

        //onClickListeners
        coding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked(v);
            }
        });
        writting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked(v);
            }
        });
        jogging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked(v);
            }
        });
        saveMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             guardarPreferencias();
            }
        });
        getMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confPreferencias();
            }
        });
    }

    //Metodos Autogenerados (RadioGroup)
    @Override
    public void onCheckedChanged(RadioGroup group, int i) {
        int radioButtonId = group.getCheckedRadioButtonId();
        RadioButton radioButton = group.findViewById(radioButtonId);
        genero = radioButton.getText().toString();
    }

    //Metodos Autogenerados (CheckBox)
    public void onCheckboxClicked(View view) {

        StringBuilder stringBuilder = new StringBuilder();

        if (jogging.isChecked()) {
            stringBuilder.append(", " + jogging.getText());
        }

        if (coding.isChecked()) {
            stringBuilder.append(", " + coding.getText());
        }

        if (writting.isChecked()) {
            stringBuilder.append(", " + writting.getText());
        }

        if (stringBuilder.length() > 0) {
            hobbies = stringBuilder.deleteCharAt(stringBuilder.indexOf(",")).toString();
        } else {
            hobbies = "";
        }
    }

    //Metodos AutoGenerados (Spinner)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        zodiaco = parent.getItemAtPosition(position).toString();
    }
    @Override


    public void onNothingSelected(AdapterView<?> parent) {
        // interface callback
    }

    public void guardarPreferencias() {
        // Captura el contenido del EditText email
        email = ((EditText)findViewById(R.id.txtemail)).getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("hobbies", hobbies);
        editor.putString("zodiac", zodiaco);

        editor.apply();

        Toast.makeText(this, "Se Han guardado los datos", Toast.LENGTH_SHORT).show();


    }

    public void confPreferencias() {

        SharedPreferences sharedPreferences = getSharedPreferences(NAME, MODE_PRIVATE);

        email = sharedPreferences.getString("email", "");
        genero = sharedPreferences.getString("gender", "");
        hobbies = sharedPreferences.getString("hobbies", "");
        zodiaco = sharedPreferences.getString("zodiac", "");

        cargarPreferencias(); // El metodo carga sharedPreferences
    }

    private void cargarPreferencias() {

        ((EditText)findViewById(R.id.txtemail)).setText(email);

        //Para seccion de RadioGroup
        RadioButton radMale = findViewById(R.id.male);
        RadioButton radFemale =findViewById(R.id.female);

        if (genero.equals("Male")){
            radMale.setChecked(true);
        } else if (genero.equals("Female")){
            radFemale.setChecked(true);
        } else {
            radMale.setChecked(false);
            radFemale.setChecked(false);
        }

        coding.setChecked(false);
        writting.setChecked(false);
        jogging.setChecked(false);

        if (hobbies.contains("Coding")) {
            coding.setChecked(true);
        }

        if (hobbies.contains("Writing")) {
            writting.setChecked(true);
        }

        if (hobbies.contains("Jogging")) {
            jogging.setChecked(true);
        }

        Resources resource = getResources();
        String[] zodiacArray = resource.getStringArray(R.array.signoZodiacal);
        int a = zodiacArray.length;
        for(int j = 0; j < a ; j++){
            if(zodiacArray[j].equals(zodiaco)){
                ((Spinner)findViewById(R.id.spinZodiacal)).setSelection(j);
            }
        }

    }

}
