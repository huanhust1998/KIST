package tacos.entity;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;


@Data
@RequiredArgsConstructor
public class Order {
    private String id;
    @NotNull(message = "Name is required")
    private String name;
    @NotBlank(message = "Street is required")
    private String street;
    @NotBlank(message="City is required")
    private String city;
    @NotBlank(message="State is required")
    private String state;
    @NotBlank(message="Zip code is required")
    private String zip;
    //@CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;
    //@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",message="Must be formatted MM/YY")
    private String ccExpiration;
    //@Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    public Order(@NotNull(message = "Name is required") String name, @NotBlank(message = "Street is required") String street, @NotBlank(message = "City is required") String city, @NotBlank(message = "State is required") String state, @NotBlank(message = "Zip code is required") String zip, String ccNumber, String ccExpiration, String ccCVV) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.ccNumber = ccNumber;
        this.ccExpiration = ccExpiration;
        this.ccCVV = ccCVV;
    }
}