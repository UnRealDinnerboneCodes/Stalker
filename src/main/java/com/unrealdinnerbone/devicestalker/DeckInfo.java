package com.unrealdinnerbone.devicestalker;

public record DeckInfo(PersonalInfo personalInfo) {
    public record PersonalInfo(double elapsedTimePercentage) {}
}
