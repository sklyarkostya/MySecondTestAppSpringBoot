package ru.sklyar.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.sklyar.MySecondTestAppSpringBoot.model.Positions;

import java.util.Calendar;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnnualBonusServiceImplTest {

    @Test
    void calculate() {
        // given
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        // when
        double result = new AnnualBonusServiceImpl().calculate(position, salary, bonus, workDays);

        // then
        double expected = 360493.8271604938;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateQuarterlyBonus() {
        // given
        Positions position = Positions.MANAGER;
        double bonus = 2.0;
        double salary = 100000.00;

        // when
        double result = new AnnualBonusServiceImpl().calculateQuarterlyBonus(position, salary, bonus);

        // then
        double expected = 36043.82;
        assertThat(result).isEqualTo(expected);
    }
}