package cz.kuchy.smarthome.web;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import cz.kuchy.smarthome.service.PeripheryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Route("")
@Configurable
public class MainPage extends Div {

    @Autowired
    private PeripheryService peripheryService;

    public MainPage() {
        FlexLayout flexLayout = new FlexLayout();
        flexLayout.setFlexDirection(FlexLayout.FlexDirection.COLUMN);

        Button initialiseButton = new Button("Initialise");
        initialiseButton.addClickListener(click -> {
            int result = peripheryService.initialise();
            Notification.show("Initialised with result " + result, 5000, Notification.Position.BOTTOM_CENTER);
        });

        NumberField soundDuration = new NumberField("Sound duration");
        soundDuration.setValue(1d);

        Button makeSoundButton = new Button("Make sound");
        makeSoundButton.addClickListener(click -> peripheryService.makeSound(soundDuration.getValue()));

        flexLayout.add(initialiseButton, soundDuration, makeSoundButton);
    }

}
