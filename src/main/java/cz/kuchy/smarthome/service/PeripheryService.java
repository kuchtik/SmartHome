package cz.kuchy.smarthome.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class PeripheryService {

    static {
        System.loadLibrary("smarthome");
    }


    // ******************************************************************************** //
    // ********************     Initialization and termination     ******************** //
    // ******************************************************************************** //

    @PostConstruct
    public native void initialise();

    @PreDestroy
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
