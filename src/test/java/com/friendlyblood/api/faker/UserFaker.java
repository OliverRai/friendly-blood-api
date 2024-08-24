package com.friendlyblood.api.faker;

import java.util.UUID;

import com.friendlyblood.api.domain.enums.BloodType;
import com.friendlyblood.api.domain.models.Address;
import com.friendlyblood.api.domain.models.User;
import com.friendlyblood.api.dtos.User.LoginRequestDTO;
import com.friendlyblood.api.dtos.User.UserRequestDTO;
import com.github.javafaker.Faker;

public class UserFaker {

  private static Faker faker = new Faker();

  public static User getUserFaker() {

    Address address = new Address();
    address.setStreet(faker.address().streetName());
    address.setNumber(faker.number().numberBetween(1, 1000));
    address.setPostalCode(faker.number().numberBetween(10000, 99999));
    address.setDistrict(faker.address().city());
    address.setState(faker.address().state());

    User user = new User();
    user.setEmail(faker.internet().emailAddress());
    user.setPassword(faker.internet().password());
    user.setName(faker.name().fullName());
    user.setBloodType(faker.options().option(BloodType.class));
    user.setAddress(address);
    user.setId(UUID.randomUUID());
    return user;
  }

  public static UserRequestDTO getUserRequestDTOFaker() {
    Address address = new Address();
    address.setStreet(faker.address().streetName());
    address.setNumber(faker.number().numberBetween(1, 1000));
    address.setPostalCode(faker.number().numberBetween(10000, 99999));
    address.setDistrict(faker.address().city());
    address.setState(faker.address().state());

    return new UserRequestDTO(
        faker.internet().emailAddress(),
        faker.internet().password(),
        faker.name().fullName(),
        faker.options().option(BloodType.class),
            address);
  }

  public static LoginRequestDTO getLoginRequestDTOFaker() {
    return new LoginRequestDTO(
        faker.internet().emailAddress(),
        faker.internet().password());
  }
}
