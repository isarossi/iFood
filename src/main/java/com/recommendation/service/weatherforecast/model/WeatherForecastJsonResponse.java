
package com.recommendation.service.weatherforecast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherForecastJsonResponse {

    @SerializedName("main")
    @Expose
    private MainTemperatureComponent main;

    public MainTemperatureComponent getMain() {
        return main;
    }

    public void setMain(MainTemperatureComponent main) {
        this.main = main;
    }


}
