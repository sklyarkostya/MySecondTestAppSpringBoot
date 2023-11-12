package ru.sklyar.MySecondTestAppSpringBoot.service;

import ru.sklyar.MySecondTestAppSpringBoot.model.Positions;

public interface AnnualBonusService {
    double calculate(Positions position, double salary, double bonus, int workDays);

    double calculateQuarterlyBonus(Positions position, double salary, double bonus);
}
