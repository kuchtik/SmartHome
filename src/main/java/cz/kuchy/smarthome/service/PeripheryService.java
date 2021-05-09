package cz.kuchy.smarthome.service;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class PeripheryService {

    private Context context;
    private DigitalOutput buzzer;
    private DigitalOutput ledRed;
    private DigitalOutput ledGreen;
    private DigitalOutput ledBlue;
    private DigitalOutput pump;
    private DigitalInput waterLevelSensor;
    private DigitalInput soilMoistureSensor;


    @PostConstruct
    public void initialise() {
        context = Pi4J.newAutoContext();

        DigitalOutputConfigBuilder buzzerConfig = DigitalOutput.newConfigBuilder(context)
                .id("buzzer")
                .name("Buzzer")
                .address(26)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");
        buzzer = context.create(buzzerConfig);

        DigitalOutputConfigBuilder ledRedConfig = DigitalOutput.newConfigBuilder(context)
                .id("led_red")
                .name("Red LED")
                .address(17)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");
        ledRed = context.create(ledRedConfig);

        DigitalOutputConfigBuilder ledGreenConfig = DigitalOutput.newConfigBuilder(context)
                .id("led_green")
                .name("Green LED")
                .address(27)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");
        ledGreen = context.create(ledGreenConfig);

        DigitalOutputConfigBuilder ledBlueConfig = DigitalOutput.newConfigBuilder(context)
                .id("led_blue")
                .name("Blue LED")
                .address(22)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");
        ledBlue = context.create(ledBlueConfig);

        DigitalOutputConfigBuilder pumpConfig = DigitalOutput.newConfigBuilder(context)
                .id("pump")
                .name("Pump")
                .address(16)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");
        pump = context.create(pumpConfig);

        DigitalInputConfigBuilder waterLevelConfig = DigitalInput.newConfigBuilder(context)
                .id("water_level")
                .name("Water level sensor")
                .address(25)
                .pull(PullResistance.PULL_DOWN)
                .provider("pigpio-digital-input");
        waterLevelSensor = context.din().create(waterLevelConfig);

        DigitalInputConfigBuilder soilMoistureConfig = DigitalInput.newConfigBuilder(context)
                .id("soil_moisture")
                .name("Soil moisture sensor")
                .address(24)
                .pull(PullResistance.PULL_DOWN)
                .provider("pigpio-digital-input");
        soilMoistureSensor = context.din().create(soilMoistureConfig);
    }


    @PreDestroy
    public void terminate() {
        context.shutdown();
    }


    public void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch(InterruptedException ignored) {}
    }


    public double readTemperature() {
        return 0d;
    }


    public double readHumidity() {
        return 0d;
    }


    public void makeSound(long duration) {
        buzzer.high();
        sleep(duration);
        buzzer.low();
    }


    public void lightRed(boolean light) {
        ledRed.state(light ? DigitalState.HIGH : DigitalState.LOW);
    }


    public void lightGreen(boolean light) {
        ledGreen.state(light ? DigitalState.HIGH : DigitalState.LOW);
    }


    public void lightBlue(boolean light) {
        ledBlue.state(light ? DigitalState.HIGH : DigitalState.LOW);
    }


    public boolean isRedLighting() {
        return ledRed.state() == DigitalState.HIGH;
    }


    public boolean isGreenLighting() {
        return ledGreen.state() == DigitalState.HIGH;
    }


    public boolean isBlueLighting() {
        return ledBlue.state() == DigitalState.HIGH;
    }


    public void pumpWater() {
        pump.high();
        sleep(2500);
        pump.low();
    }


    public DigitalState getWaterLevelSensorValue() {
        return waterLevelSensor.state();
    }


    public DigitalState getSoilMoistureSensorValue() {
        return soilMoistureSensor.state();
    }

}
