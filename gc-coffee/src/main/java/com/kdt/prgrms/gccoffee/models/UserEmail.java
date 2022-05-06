package com.kdt.prgrms.gccoffee.models;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

public class UserEmail {

    private final String address;


    public UserEmail(String address) {

        Assert.notNull(address, "address should not be null");
        Assert.isTrue(address.length() >= 4 && address.length() <= 50, "address length must be between 5 and 50");
        Assert.isTrue(checkedAddress(address), "invalid format email");
        this.address = address;
    }

    private boolean checkedAddress(String address) {

        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEmail email = (UserEmail) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(address);
    }

    @Override
    public String toString() {

        return "Email{" +
                "address='" + address + '\'' +
                '}';
    }

    public String getAddress() {

        return address;
    }
}
