# Chytrá domácnost

Tento projekt vznikl jako semestrální práce do předmětu NI-IOT na ČVUT v Praze.

V projektu bych chtěl vytvořit:
- systém pro automatické zalévání květiny se senzorem vlhkosti půdy a senzorem hladiny vody v nádrži, aby se hlídalo, že voda došla
- rozsvěcení a nastavování barvy pásu LED diod
- měření teploty a vlhkosti vzduchu v pokoji

Projekt bude postavený na zařízení Raspberry Pi 3B. Na zařízení poběží webový server, který bude zobrazovat informace a bude z něj možné ovládat připojené součástky. Server i ovládání bude napsaný v Javě.

Schéma zapojení součástek do Raspberry:

![alt text](https://github.com/kuchtik/SmartHome/blob/master/circuit_described.png)
