package eu.telecomnancy.labfx.model;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Person {
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;

    public Person(String prenom, String nom) {
        this.firstName = new SimpleStringProperty(prenom);
        this.lastName = new SimpleStringProperty(nom);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }
}
