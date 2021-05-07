package cz.kuchy.smarthome.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PeripheryService {

    static {
        System.loadLibrary("smarthome");
    }


    // ******************************************************************************** //
    // ********************             Initialization             ******************** //
    // ******************************************************************************** //

    public native int initialise();

    @PostConstruct
    public void automaticInitialise() {
        int result = initialise();
        System.out.println("Initialisation result: " + result);
    }


    // ******************************************************************************** //
    // ********************              DHT11 sensor              ******************** //
    // ******************************************************************************** //

//    public native double readTemperature();
//    public native double readHumidity();


    // ******************************************************************************** //
    // ********************       Piezo buzzer LD-BZEG-1203        ******************** //
    // ******************************************************************************** //

    public native void makeSound(double duration);


    // ******************************************************************************** //
    // ********************               LED strip                ******************** //
    // ******************************************************************************** //

//    public native void lightLEDs(int red, int green, int blue);

}
