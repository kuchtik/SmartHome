package cz.kuchy.smarthome.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class PeripheryService {

    static {
        System.loadLibrary("smarthome");
        System.out.println("Loading native library finished");
    }


    // ******************************************************************************** //
    // ********************     Initialisation and termination     ******************** //
    // ******************************************************************************** //

    public native void initialise();
    public native void terminate();

    @PostConstruct
    public void automaticInitialise() {
        initialise();
        System.out.println("Initialisation finished");
    }

    @PreDestroy
    public void automaticTerminate() {
        terminate();
        System.out.println("Termination finished");
    }


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
