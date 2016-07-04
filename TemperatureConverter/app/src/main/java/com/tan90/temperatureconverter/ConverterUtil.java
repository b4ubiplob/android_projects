package com.tan90.temperatureconverter;

/**
 * Created by I320626 on 10/3/2015.
 */
public class ConverterUtil {

    public static float convertFahrenheitToCelsius(float fahrenheit) {
        return ((fahrenheit - 32) * 5 / 9);
    }

    public static float convertCelsiusToFahrenheit(float celsius) {
        return ((celsius * 9) / 5) + 32;
    }
}
