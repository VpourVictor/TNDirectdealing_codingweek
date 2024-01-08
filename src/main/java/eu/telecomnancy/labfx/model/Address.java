package eu.telecomnancy.labfx.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private String city;
    private String streetName;
    private int streetNumber;
    private String Country;
    private int postalCode;
    private String region;

    public Address(int streetNumber, String streetName, int postalCode, String city, String region, String country) {
        this.city = city;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        Country = country;
        this.postalCode = postalCode;
        this.region = region;
    }
}
