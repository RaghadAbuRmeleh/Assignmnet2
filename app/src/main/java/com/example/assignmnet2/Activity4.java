package com.example.assignmnet2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity4 extends AppCompatActivity {

    private EditText edtWeight;
    private EditText edtHeight;
    private EditText edtAge;
    private RequestQueue queue;
    private TextView txtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        edtWeight = findViewById(R.id.edtWeight);
        edtHeight = findViewById(R.id.edtHeight);
        edtAge = findViewById(R.id.edtAge);
        txtResult = findViewById(R.id.txtResult2);
        queue = Volley.newRequestQueue(this);
    }

    public void btnShow_Click(View view) {
        String height = "";
        String weight = "";
        String age = "";
        height = edtHeight.getText().toString();
        weight = edtWeight.getText().toString();
        age = edtAge.getText().toString();

        if (TextUtils.isEmpty(height) || TextUtils.isEmpty(weight) || TextUtils.isEmpty(age)) {
            txtResult.setText("Enter Desired info");
        }
        else{
            String url = "https://fitness-calculator.p.rapidapi.com/bmi?age=" + age + "&weight=" + weight + "&height=" + height;
            Log.d("API_REQUEST", "URL: " + url); // Log the URL

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("API_RESPONSE", "Response: " + response); // Log the API response

                            //txtResult.setText(response);
                            String result = "";
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject dataObj = jsonObject.getJSONObject("data");

                                double bmi = dataObj.getDouble("bmi");
                                String health = dataObj.getString("health");
                                String healthRange = dataObj.getString("healthy_bmi_range");

                                // Log the parsed values
                                Log.d("API_PARSING", "BMI: " + bmi + ", Health: " + health + ", Range: " + healthRange);

                                 result = "BMI = " + bmi;
                                result += "\nHealth: " + health;
                                result += "\nHealth BMI range: " + healthRange;

                                txtResult.setText(result);

                                //close keyboard
                                InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                input.hideSoftInputFromWindow(view.getWindowToken(), 0);

                            } catch (JSONException exception) {
                                Log.e("API_PARSING", "Error parsing JSON: " + exception.getMessage());
                                exception.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("API_ERROR", "Error: " + error.getMessage()); // Log API errors

                    Toast.makeText(Activity4.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("X-RapidAPI-Key", "515bcadc34msh0415d2999540012p1cde12jsn6cdca9a79630");
                    headers.put("X-RapidAPI-Host", "fitness-calculator.p.rapidapi.com");
                    return headers;
                }
            };



            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }
}