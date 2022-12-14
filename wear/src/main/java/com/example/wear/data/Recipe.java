package com.example.wear.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe {
    @SerializedName("Calorie")
    @Expose
    public int Calorie;

    @SerializedName("Time")
    @Expose
    public int Time;

    @SerializedName("Name")
    @Expose
    public String Name;

    @SerializedName("Ingredients")
    @Expose
    public String Ingredients;

    @SerializedName("Difficulty")
    @Expose
    public int Difficulty;
}
