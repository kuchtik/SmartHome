gcc -Wall -pedantic -shared -I/usr/lib/jvm/default-java/include -I/usr/lib/jvm/default-java/include/linux -o libsmarthome.so src/main/c/smart_home.c -lpigpio
cp libsmarthome.so /opt/smart_home/libsmarthome.so
mvn package -DskipTests -Pproduction
cp target/smarthome*.jar /opt/smart_home/smarthome.jar