package ru.sklyar.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.sklyar.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;

@Service
public class UnsupportedCodeValidationService {
    public void isValid(int uid) throws UnsupportedCodeException {
        if (uid == 123) {
            throw new UnsupportedCodeException();
        }
    }
}
