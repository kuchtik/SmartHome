#include <stdio.h>
#include <stdlib.h>
#include <pigpio.h>
#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_initialise(JNIEnv * env, jobject thisObject);
JNIEXPORT void JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_terminate(JNIEnv * env, jobject thisObject);
JNIEXPORT jdouble JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_readTemperature(JNIEnv * env, jobject thisObject);
JNIEXPORT jdouble JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_readHumidity(JNIEnv * env, jobject thisObject);
JNIEXPORT void JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_makeSound(JNIEnv * env, jobject thisObject, jdouble duration);
JNIEXPORT void JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_lightLEDs(JNIEnv * env, jobject thisObject, jint red, jint green, jint blue);

#ifdef __cplusplus
}
#endif

#define GPIO_LED_RED 17
#define GPIO_LED_GREEN 27
#define GPIO_LED_BLUE 22
#define GPIO_PUMP 16
#define GPIO_WATER_LEVEL 25
#define GPIO_MOISTURE 24
#define GPIO_DHT 23
#define GPIO_BUZZER 26


// ******************************************************************************** //
// ********************     Initialization and termination     ******************** //
// ******************************************************************************** //

JNIEXPORT void JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_initialise(JNIEnv * env, jobject thisObject) {
    int init = gpioInitialise();
    printf("GPIO initialised: %d\n", init);
    if(init > 0) {
        gpioSetMode(GPIO_LED_RED, PI_OUTPUT);
        gpioSetMode(GPIO_LED_GREEN, PI_OUTPUT);
        gpioSetMode(GPIO_LED_BLUE, PI_OUTPUT);
        gpioSetMode(GPIO_PUMP, PI_OUTPUT);
        gpioSetMode(GPIO_WATER_LEVEL, PI_INPUT);
        gpioSetMode(GPIO_MOISTURE, PI_INPUT);
        gpioSetMode(GPIO_BUZZER, PI_OUTPUT);
    }
}

JNIEXPORT void JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_terminate(JNIEnv * env, jobject thisObject) {
    gpioTerminate();
    printf("GPIO terminated\n");
}


// ******************************************************************************** //
// *****************************     DHT11 sensor     ***************************** //
// ******************************************************************************** //

JNIEXPORT jdouble JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_readTemperature(JNIEnv * env, jobject thisObject) {
    double temperature = 0;
    printf("Temperature: %f\n", temperature);
    return (jdouble) temperature;
}

JNIEXPORT jdouble JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_readHumidity(JNIEnv * env, jobject thisObject) {
    double humidity = 0;
    printf("Humidity: %f\n", humidity);
    return (jdouble) humidity;
}


// ******************************************************************************** //
// **********************     Piezo buzzer LD-BZEG-1203     *********************** //
// ******************************************************************************** //

JNIEXPORT void JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_makeSound(JNIEnv * env, jobject thisObject, jdouble duration) {
    gpioPWM(GPIO_BUZZER, 255);
    time_sleep((double) duration);
    gpioPWM(GPIO_BUZZER, 0);
    printf("Made sound for %f\n", (double) duration);
}


// ******************************************************************************** //
// ******************************     LED strip     ******************************* //
// ******************************************************************************** //

JNIEXPORT void JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_lightLEDs(JNIEnv * env, jobject thisObject, jint red, jint green, jint blue) {
    gpioPWM(GPIO_LED_RED, (int) red);
    gpioPWM(GPIO_LED_GREEN, (int) green);
    gpioPWM(GPIO_LED_BLUE, (int) blue);
    printf("LEDs lighted - red %d, green %d, blue %d\n", (int) red, (int) green, (int) blue);
}
