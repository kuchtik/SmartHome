package cz.kuchy.smarthome.service;

import org.springframework.stereotype.Service;

@Service
public class PeripheryService {

    static {
        System.loadLibrary("smarthome");
        System.out.println("Loading native library finished");
    }


    // ******************************************************************************** //
    // ********************     Initialisation and termination     ******************** //
    // ******************************************************************************** //

    public native int initialise();
    public native void terminate();


    // ******************************************************************************** //
    // ********************              DHT11 sensor              ******************** //
    // ******************************************************************************** //

    public native double readTemperature();
    public native double readHumidity();


    // ******************************************************************************** //
    // ********************       Piezo buzzer LD-BZEG-1203        ******************** //
    // ******************************************************************************** //

    public native void makeSound(double duration);


    // ******************************************************************************** //
    // ********************               LED strip                ******************** //
    // ******************************************************************************** //

    public native void lightLEDs(int red, int green, int blue);

}
