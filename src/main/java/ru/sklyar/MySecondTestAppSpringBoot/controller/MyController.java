package ru.sklyar.MySecondTestAppSpringBoot.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sklyar.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.sklyar.MySecondTestAppSpringBoot.model.Request;
import ru.sklyar.MySecondTestAppSpringBoot.model.Response;
import ru.sklyar.MySecondTestAppSpringBoot.service.ValidationService;
import ru.sklyar.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.sklyar.MySecondTestAppSpringBoot.service.UnsupportedCodeValidationService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class MyController {
    private final ValidationService validationService;
    private final UnsupportedCodeValidationService unsupportedCodeValidationService;
    @Autowired
    public MyController(ValidationService validationService, UnsupportedCodeValidationService unsupportedCodeValidationService) {
        this.validationService = validationService;
        this.unsupportedCodeValidationService = unsupportedCodeValidationService;
    }
    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(simpleDateFormat.format(new Date()))
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();
        try {
            unsupportedCodeValidationService.isValid(Integer.parseInt(request.getUid()));
        } catch (UnsupportedCodeException e) {
            response.setCode("failed");
            response.setErrorCode("UnsupportedCodeException");
            response.setErrorMessage("uid = 123");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage("Произошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
