#include <stdio.h>
#include <stdlib.h>
#include <pigpio.h>
#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jint JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_initialise(JNIEnv * env, jobject thisObject);
JNIEXPORT void JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_makeSound(JNIEnv * env, jobject thisObject, jdouble duration);

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

JNIEXPORT jint JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_initialise(JNIEnv * env, jobject thisObject) {
    int init = gpioInitialise();
    if(init > 0) {
        gpioSetMode(GPIO_BUZZER, PI_OUTPUT);
    }
    return (jint) init;
}

JNIEXPORT void JNICALL Java_cz_kuchy_smarthome_service_PeripheryService_makeSound(JNIEnv * env, jobject thisObject, jdouble duration) {
    gpioPWM(GPIO_BUZZER, 255);
    time_sleep((double) duration);
    gpioPWM(GPIO_BUZZER, 0);
}
