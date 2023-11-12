package ru.sklyar.MySecondTestAppSpringBoot.service;

import ru.sklyar.MySecondTestAppSpringBoot.model.Positions;

import java.time.Year;

public class AnnualBonusServiceImpl implements AnnualBonusService {
    @Override
    public double calculate(Positions position, double salary, double bonus, int workDays) {
        int daysInYear = Year.now().isLeap() ? 366 : 365;
        return salary * bonus * daysInYear * position.getPositionCoefficient() / workDays;
    }

    @Override
    public double calculateQuarterlyBonus(Positions position, double salary, double bonus) {
        if (position.isManager()) {
            int quartersInYear = 4;
            return salary * bonus * position.getPositionCoefficient() / quartersInYear;
        }
        return 0;
    }
}