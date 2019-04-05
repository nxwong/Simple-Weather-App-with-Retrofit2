package com.nxwong.simpleweatherapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nxwong.simpleweatherapp.WeatherResponse;
import com.nxwong.simpleweatherapp.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    EditText cityName;
    TextView resultTextView;

    final String API_ID = "e08a5ca19e274ff588e808477778787d";
    final String BASEURL = "https://api.openweathermap.org/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cityName = (EditText) findViewById(R.id.cityName);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

    }


    public void onClickWeatherButton(View view){
//        Log.i("cityName", cityName.getText().toString() );

        resultTextView.setText("");

        //hide keyboard
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        String name="";
        name = cityName.getText().toString();
        getWeatherData(name);
    }

    void getWeatherData(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeatherData(cityName, API_ID);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call , Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    String message = "";
                    String main = "";
                    String description = "";

                    main = weatherResponse.weather.get(0).main;
                    description = description = weatherResponse.weather.get(0).description;

                    if (main != "" && description != "") {
                        message += main + ": " + description + "\r\n";

                        if (message != "") {
                            resultTextView.setText(message);
                        } else {
                            Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);
                        }

                    }
                }
            }
            @Override
            public void onFailure(Call<WeatherResponse> call , Throwable t) {
                resultTextView.setText(t.getMessage());
            }
        });
    }


}
