package cz.kuchy.smarthome.web;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.kuchy.smarthome.service.PeripheryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configurable
@Route("")
@PageTitle("Chytrá domácnost")
@CssImport("./css/styles.css")
public class MainPage extends FlexLayout {

    @Autowired
    private PeripheryService peripheryService;


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        addClassName("body");

        setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        setAlignItems(Alignment.CENTER);

        add(new H1("Chytrá domácnost"));
        Paragraph timeInfo = new Paragraph("Čas: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE d. M. yyyy HH:mm", new Locale("cs"))));
        Paragraph temperatureInfo = new Paragraph(String.format("Teplota uvnitř: %1f", peripheryService.getTemperature()));
        Paragraph humidityInfo = new Paragraph(String.format("Vlhkost uvnitř: %1f", peripheryService.getHumidity()));
        add(timeInfo, temperatureInfo, humidityInfo);

        add(new H2("Bzučák"));
        add(createSoundSection());

        add(new H2("LED diody"));
        add(createLedSection());

        add(new H2("Automatické zalévání"));
        add(createPumpSection());
    }

    private FlexLayout createSoundSection() {
        IntegerField soundDuration = new IntegerField("Délka pípnutí");
        soundDuration.setValue(1000);
        soundDuration.setMin(0);
        soundDuration.setMax(10000);

        Button plusDurationButton = new Button(new Icon(VaadinIcon.PLUS), click -> {
            if(soundDuration.getValue() == null) {
                soundDuration.setValue(0);
            } else {
                soundDuration.setValue(soundDuration.getValue() + 100);
            }
        });

        Button minusDurationButton = new Button(new Icon(VaadinIcon.MINUS), click -> {
            if(soundDuration.getValue() == null) {
                soundDuration.setValue(0);
            } else {
                soundDuration.setValue(soundDuration.getValue() - 100);
            }
        });

        Button makeSoundButton = new Button("Pípni", click -> {
            if(soundDuration.getValue() == null) {
                Notification.show("Není zadaný čas!", 4000, Notification.Position.BOTTOM_CENTER);
            } else if(soundDuration.getValue() < 0) {
                Notification.show("Čas nesmí být záporná hodnota!", 4000, Notification.Position.BOTTOM_CENTER);
            } else {
                peripheryService.makeSound(soundDuration.getValue());
            }
        });

        return createFlexLayout(FlexDirection.ROW, Alignment.BASELINE, soundDuration, plusDurationButton, minusDurationButton, makeSoundButton);
    }


    private FlexLayout createLedSection() {
        Button red = new Button(peripheryService.isRedLighting() ? "Zhasni červenou" : "Rozsviť červenou", click -> {
            boolean lighting = peripheryService.isRedLighting();
            peripheryService.lightRed(!lighting);
            click.getSource().setText(!lighting ? "Zhasni červenou" : "Rozsviť červenou");
        });
        red.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        red.getElement().getStyle().set("background-color", "#ffbbbb");

        Button green = new Button(peripheryService.isGreenLighting() ? "Zhasni zelenou" : "Rozsviť zelenou", click -> {
            boolean lighting = peripheryService.isGreenLighting();
            peripheryService.lightGreen(!lighting);
            click.getSource().setText(!lighting ? "Zhasni zelenou" : "Rozsviť zelenou");
        });
        green.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        green.getElement().getStyle().set("background-color", "#a4ffb6");

        Button blue = new Button(peripheryService.isBlueLighting() ? "Zhasni modrou" : "Rozsviť modrou", click -> {
            boolean lighting = peripheryService.isBlueLighting();
            peripheryService.lightBlue(!lighting);
            click.getSource().setText(!lighting ? "Zhasni modrou" : "Rozsviť modrou");
        });
        blue.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        blue.getElement().getStyle().set("background-color", "#a4d3ff");

        return createFlexLayout(FlexDirection.ROW, Alignment.BASELINE, red, green, blue);
    }


    private FlexLayout createPumpSection() {
        Paragraph waterLevelInfo = new Paragraph("Voda v nádrži: " + (peripheryService.isWaterInBarrel() ? "ano" : "ne"));
        Paragraph soilMoistureInfo = new Paragraph("Vlhkost půdy: " + peripheryService.getSoilMoistureSensorValue());
        Paragraph nextWateringInfo = new Paragraph("Příští automatické zalévání: " + peripheryService.getNextAutomaticWateringTime().format(
                DateTimeFormatter.ofPattern("EEEE d. M. yyyy HH:mm", new Locale("cs"))));

        Button pump = new Button("Spustit zalévání manuálně", click -> {
            if(peripheryService.isWaterInBarrel()) {
                peripheryService.pumpWater();
            } else {
                Notification.show("V nádrži není voda, doplň!", 4000, Notification.Position.BOTTOM_CENTER);
            }
        });

        return createFlexLayout(FlexDirection.COLUMN, Alignment.CENTER, waterLevelInfo, nextWateringInfo, pump);
    }


    private FlexLayout createFlexLayout(FlexDirection flexDirection, Alignment alignItems, Component... children) {
        FlexLayout flexLayout = new FlexLayout(children);
        flexLayout.setFlexDirection(flexDirection);
        flexLayout.setAlignItems(alignItems);
        flexLayout.setFlexWrap(FlexWrap.WRAP);
        flexLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        return flexLayout;
    }

}
