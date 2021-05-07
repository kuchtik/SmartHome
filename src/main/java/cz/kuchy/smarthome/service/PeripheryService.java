package cz.kuchy.smarthome.service;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import org.springframework.stereotype.Service;

@Service
public class PeripheryService {


    // ******************************************************************************** //
    // ********************     Initialisation and termination     ******************** //
    // ******************************************************************************** //

    public void initialise() {

    }

    public void terminate() {

    }


    // ******************************************************************************** //
    // ********************              DHT11 sensor              ******************** //
    // ******************************************************************************** //

    public double readTemperature() {
        return 0d;
    }

    public double readHumidity() {
        return 0d;
    }


    // ******************************************************************************** //
    // ********************       Piezo buzzer LD-BZEG-1203        ******************** //
    // ******************************************************************************** //

    public void makeSound(long duration) {
        Context context = Pi4J.newAutoContext();
        DigitalOutputConfigBuilder buzzerConfig = DigitalOutput.newConfigBuilder(context)
                .id("led")
                .name("LED Flasher")
                .address(26)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");
        DigitalOutput buzzer = context.create(buzzerConfig);
        buzzer.high();
        try {
            Thread.sleep(duration);
        } catch(InterruptedException ignored) {}
        buzzer.low();
        context.shutdown();
    }


    // ******************************************************************************** //
    // ********************               LED strip                ******************** //
    // ******************************************************************************** //

    public void lightLEDs(int red, int green, int blue) {

    }

}
