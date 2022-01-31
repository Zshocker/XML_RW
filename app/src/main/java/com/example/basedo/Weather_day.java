package com.example.basedo;

public class Weather_day
{
    public static final String UnitWind="mph",UnitTemp="C";
    private String Day;
    private Double windSpeed,temp;

    public Weather_day(String day, Double windSpeed, Double temp) {
        Day = day;
        this.windSpeed = windSpeed;
        this.temp = temp;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }
}
