package ru.sklyar.MySecondTestAppSpringBoot.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ru.sklyar.MySecondTestAppSpringBoot.model.Request;
import ru.sklyar.MySecondTestAppSpringBoot.model.Systems;
import ru.sklyar.MySecondTestAppSpringBoot.service.ModifyRequestService;
import org.springframework.stereotype.Service;

@Service
public class ModifySystemNameRequestService implements ModifyRequestService{
    public void modify(Request request) {
        request.setSystemName(Systems.Service1);

        HttpEntity<Request> httpEntity = new HttpEntity<>(request);
        new RestTemplate().exchange("http://localhost:8084/feedback",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
    }
}
