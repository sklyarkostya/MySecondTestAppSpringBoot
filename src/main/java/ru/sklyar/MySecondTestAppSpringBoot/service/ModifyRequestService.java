package ru.sklyar.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.sklyar.MySecondTestAppSpringBoot.model.Request;
@Service
public interface ModifyRequestService {
    void modify (Request request);
}
