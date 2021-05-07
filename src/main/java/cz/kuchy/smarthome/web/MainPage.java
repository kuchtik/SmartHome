package cz.kuchy.smarthome.web;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
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

        setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        setAlignItems(Alignment.START);

        H1 header = new H1("Chytrá domácnost");
        setAlignSelf(Alignment.CENTER, header);
        add(header);

        add(createSoundSection());
        add(createLedSection());
    }

    private FlexLayout createSoundSection() {
        NumberField soundDuration = new NumberField("Délka pípnutí");
        soundDuration.setMin(0d);
        soundDuration.setMax(10d);

        Button plusDurationButton = new Button(new Icon(VaadinIcon.PLUS), click -> {
            if(soundDuration.getValue() == null) {
                soundDuration.setValue(0d);
            } else {
                soundDuration.setValue(soundDuration.getValue() + .1);
            }
        });

        Button minusDurationButton = new Button(new Icon(VaadinIcon.MINUS), click -> {
            if(soundDuration.getValue() == null) {
                soundDuration.setValue(0d);
            } else {
                soundDuration.setValue(soundDuration.getValue() - .1);
            }
        });

        Button makeSoundButton = new Button("Pípni", click -> {
            if(soundDuration.getValue() == null) {
                Notification.show("Není zadaný čas!", 4000, Notification.Position.BOTTOM_CENTER);
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
        IntegerField red = new IntegerField("Červená");
        red.setValue(0);
        red.setMin(0);
        red.setMax(255);

        IntegerField green = new IntegerField("Zelená");
        green.setValue(0);
        green.setMin(0);
        green.setMax(255);

        IntegerField blue = new IntegerField("Modrá");
        blue.setValue(0);
        blue.setMin(0);
        blue.setMax(255);

        Button lightLEDsButton = new Button("Rozsviť", click -> {
            if(red.getValue() == null || green.getValue() == null || blue.getValue() == null) {
                Notification.show("Nejsou zadané hodnoty!", 4000, Notification.Position.BOTTOM_CENTER);
            } else {
                //peripheryService.lightLEDs(red.getValue(), green.getValue(), blue.getValue());
            }
        });
        lightLEDsButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        FlexLayout ledRow = new FlexLayout(red, green, blue, lightLEDsButton);
        ledRow.setFlexDirection(FlexDirection.ROW);
        ledRow.setAlignItems(Alignment.BASELINE);
        return ledRow;
    }

}