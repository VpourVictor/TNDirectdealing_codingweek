package eu.telecomnancy.labfx.model;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Person {
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    @Getter
    @Setter
    private static int nbPersons = 0;

    public Person(String prenom, String nom) {
        this.firstName = new SimpleStringProperty(prenom);
        this.lastName = new SimpleStringProperty(nom);
        this.email = new SimpleStringProperty("");
        nbPersons++;
    }

    public Person(String prenom, String nom, String email) {
        this.firstName = new SimpleStringProperty(prenom);
        this.lastName = new SimpleStringProperty(nom);
        this.email = new SimpleStringProperty(email);
        nbPersons++;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String name){
        firstName = new SimpleStringProperty(name);
    }

    public void setLastName(String name){
        lastName = new SimpleStringProperty(name);
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

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }
}
