package cz.kuchy.smarthome;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("")
public class MainPage extends Div {
    public MainPage() {
        setText("Smart home");
    }
}
