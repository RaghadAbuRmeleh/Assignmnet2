package com.example.assignmnet2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity5 extends AppCompatActivity {

    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private RequestQueue queue;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);
        queue = Volley.newRequestQueue(this);
        txtResult = findViewById(R.id.txtResult2);
        txtResult.setMovementMethod(new ScrollingMovementMethod());

        spinner1 = findViewById(R.id.spn1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.type,
                android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(adapter1);

        spinner2 = findViewById(R.id.spn2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.muscle,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner3 = findViewById(R.id.spn3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this,
                R.array.difficulty,
                android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

    }

    public void btnExc_Click(View view) {
        String type = "";
        String muscle = "";
        String difficulty = "";
        type = spinner1.getSelectedItem().toString();
        muscle = spinner2.getSelectedItem().toString();
        difficulty = spinner3.getSelectedItem().toString();

        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(muscle) || TextUtils.isEmpty(difficulty)) {
            txtResult.setText("Enter Desired info");
        }
        else{
            String apiUrl = "https://exercises-by-api-ninjas.p.rapidapi.com/v1/exercises";

            String selectedType = spinner1.getSelectedItem().toString();
            String selectedMuscle = spinner2.getSelectedItem().toString();
            String selectedDifficulty = spinner3.getSelectedItem().toString();

            StringBuilder urlBuilder = new StringBuilder(apiUrl);

            if (!TextUtils.isEmpty(selectedType)) {
                urlBuilder.append("?type=").append(selectedType);
            }

            if (!TextUtils.isEmpty(selectedMuscle)) {
                if (urlBuilder.toString().contains("?")) {
                    urlBuilder.append("&muscle=").append(selectedMuscle);
                } else {
                    urlBuilder.append("?muscle=").append(selectedMuscle);
                }
            }

            if (!TextUtils.isEmpty(selectedDifficulty)) {
                if (urlBuilder.toString().contains("?") || urlBuilder.toString().contains("&")) {
                    urlBuilder.append("&difficulty=").append(selectedDifficulty);
                } else {
                    urlBuilder.append("?difficulty=").append(selectedDifficulty);
                }
            }

            String finalUrl = urlBuilder.toString();
            Log.d("API_REQUEST", "URL: " + finalUrl); // Log the URL

            StringRequest stringRequest = new StringRequest(Request.Method.GET, finalUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = "";
                            try {
                                JSONArray exercisesArray = new JSONArray(response);

                                if (exercisesArray.length() > 0) {
                                    for (int i = 0; i < exercisesArray.length(); i++) {
                                        JSONObject exercise = exercisesArray.getJSONObject(i);

                                        String name = exercise.getString("name");
                                        String type = exercise.getString("type");
                                        String muscle = exercise.getString("muscle");
                                        String equipment = exercise.getString("equipment");
                                        String difficulty = exercise.getString("difficulty");
                                        String instructions = exercise.getString("instructions");

                                        result = "Name = " + name;
                                        result += "\nType: " + type;
                                        result += "\nMuscle: " + muscle;
                                        result += "\nEquipment: " + equipment;
                                        result += "\nDifficulty: " + difficulty;
                                        result += "\nInstructions: " + instructions;

                                        txtResult.setText(result);
                                    }
                                } else {
                                    Log.d("API_PARSING", "Empty response array");
                                    txtResult.setText("No exercises were found that meet these criteria");
                                }
                            } catch (JSONException e) {
                                Log.e("API_PARSING", "Error parsing JSON: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("API_ERROR", "Error: " + error.getMessage()); // Log API errors

                    Toast.makeText(Activity5.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("X-RapidAPI-Key", "515bcadc34msh0415d2999540012p1cde12jsn6cdca9a79630");
                    headers.put("X-RapidAPI-Host", "exercises-by-api-ninjas.p.rapidapi.com");
                    return headers;
                }
            };



            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }
}