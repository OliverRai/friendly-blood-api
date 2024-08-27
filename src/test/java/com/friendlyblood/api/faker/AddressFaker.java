package com.friendlyblood.api.faker;

import com.friendlyblood.api.domain.models.Address;
import com.friendlyblood.api.dtos.Address.AddressRequestDTO;
import com.github.javafaker.Faker;

public class AddressFaker {

    private static Faker faker = new Faker();

    public static Address getAddressFaker() {
        Address address = new Address();
        address.setStreet(faker.address().streetName());
        address.setNumber(faker.number().numberBetween(1, 1000));
        address.setPostalCode(faker.number().numberBetween(10000, 99999));
        address.setDistrict(faker.address().city());
        address.setState(faker.address().state());

        return address;
    }

    public static AddressRequestDTO getAddressRequestDTOFaker(){
        AddressRequestDTO addressRequestDTO = new AddressRequestDTO(
                faker.address().streetName(),
                faker.number().numberBetween(1, 1000),
                faker.number().numberBetween(10000, 99999),
                faker.address().state(), faker.address().city());

        return addressRequestDTO;
    }
}
