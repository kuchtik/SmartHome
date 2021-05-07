package cz.kuchy.smarthome.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
public class PeripheryService {

    static {
        System.loadLibrary("smarthome");
    }


    // ******************************************************************************** //
    // ********************             Initialization             ******************** //
    // ******************************************************************************** //

    public native int initialise();


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


    @PostConstruct
    public void automaticInitialise() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        int result = initialise();
        System.out.println("Initialisation result: " + result);
    }
}
