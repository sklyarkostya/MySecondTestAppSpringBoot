package ru.sklyar.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.sklyar.MySecondTestAppSpringBoot.model.Request;

@Service
public class ModifySourceRequestService implements ModifyRequestService {
    @Override
    public void modify(Request request) {
        request.setSource("Изменение source");
    }
}
