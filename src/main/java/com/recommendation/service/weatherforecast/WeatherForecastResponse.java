
package com.recommendation.service.weatherforecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherForecastResponse {

    @SerializedName("main")
    @Expose
    private MainJsonPojo main;

    public MainJsonPojo getMain() {
        return main;
    }

    public void setMain(MainJsonPojo main) {
        this.main = main;
    }


}
