package com.friendlyblood.api.utils.enums;

public enum BloodType {
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-");

    private final String value;

    BloodType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static BloodType fromString(String text) {
        for (BloodType bloodType : BloodType.values()) {
            if (bloodType.value.equalsIgnoreCase(text)) {
                return bloodType;
            }
        }
        throw new IllegalArgumentException("Unknown blood type: " + text);
    }
}