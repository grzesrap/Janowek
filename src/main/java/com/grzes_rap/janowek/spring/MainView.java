package com.grzes_rap.janowek.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
@StyleSheet("frontend://styles/styles.css")
public class MainView extends VerticalLayout {

    public MainView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addClassName("main-view");

        H1 header = new H1("Janówek Drytooling Base");
        header.getElement().getThemeList().add("dark");

        add(header);
        askUsername();
    }


    private void askUsername() {
        HorizontalLayout layout = new HorizontalLayout();

        TextField usernameField = new TextField();
        Button startButton = new Button("Start");

        layout.add(usernameField, startButton);

        add(layout);
    }

}
