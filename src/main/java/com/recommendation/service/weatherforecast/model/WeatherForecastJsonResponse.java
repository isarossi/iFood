
package com.recommendation.service.weatherforecast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherForecastJsonResponse {

    @SerializedName("main")
    @Expose
    private Temperature main;
    @SerializedName("coord")
    @Expose
    private Coordinates coord;
    @SerializedName("name")
    @Expose
    private String cityName;

    public Temperature getMain() {
        return main;
    }

    public void setMain(Temperature main) {
        this.main = main;
    }

    public Coordinates getCoord() {
        return coord;
    }
    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
