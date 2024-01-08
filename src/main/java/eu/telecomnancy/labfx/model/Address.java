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

}
