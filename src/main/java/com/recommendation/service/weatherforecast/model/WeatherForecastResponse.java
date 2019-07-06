
package com.recommendation.service.weatherforecast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.recommendation.service.weatherforecast.model.MainJsonPojo;

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
