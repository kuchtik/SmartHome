package cz.kuchy.smarthome.web;

import com.vaadin.flow.component.AttachEvent;
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

//        Button initialiseButton = new Button("Initialise", click -> peripheryService.initialise());
//        initialiseButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
//        add(initialiseButton);
//
//        Button terminateButton = new Button("Terminate", click -> peripheryService.terminate());
//        terminateButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
//        add(terminateButton);

        add(new H1("Chytrá domácnost"));

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
        makeSoundButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        FlexLayout soundRow = new FlexLayout(soundDuration, plusDurationButton, minusDurationButton, makeSoundButton);
        soundRow.setFlexDirection(FlexDirection.ROW);
        soundRow.setAlignItems(Alignment.BASELINE);
        return soundRow;
    }


    private FlexLayout createLedSection() {
        Button red = new Button(peripheryService.isRedLighting() ? "Zhasni červenou" : "Rozsviť červenou", click -> {
            boolean lighting = peripheryService.isRedLighting();
            peripheryService.lightRed(!lighting);
            click.getSource().setText(!lighting ? "Zhasni červenou" : "Rozsviť červenou");
        });
        red.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        red.getElement().getStyle().set("background-color", "#ffa4a4");

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

        FlexLayout ledRow = new FlexLayout(red, green, blue);
        ledRow.setFlexDirection(FlexDirection.ROW);
        ledRow.setAlignItems(Alignment.BASELINE);
        return ledRow;
    }


    private FlexLayout createPumpSection() {
        Paragraph waterLevelInfo = new Paragraph("Voda v nádrži: " + peripheryService.getWaterLevelSensorValue());
        Paragraph soilMoistureInfo = new Paragraph("Vlhkost půdy: " + peripheryService.getSoilMoistureSensorValue());

        Button pump = new Button("Spustit zalévání manuálně", click -> peripheryService.pumpWater());
        pump.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        FlexLayout ledSection = new FlexLayout(waterLevelInfo, soilMoistureInfo, pump);
        ledSection.setFlexDirection(FlexDirection.COLUMN);
        ledSection.setAlignItems(Alignment.CENTER);
        return ledSection;
    }

}
