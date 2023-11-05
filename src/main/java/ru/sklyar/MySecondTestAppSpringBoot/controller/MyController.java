package ru.sklyar.MySecondTestAppSpringBoot.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sklyar.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.sklyar.MySecondTestAppSpringBoot.model.*;
import ru.sklyar.MySecondTestAppSpringBoot.service.ModifyOperationUidResponseService;
import ru.sklyar.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.sklyar.MySecondTestAppSpringBoot.service.ValidationService;
import ru.sklyar.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.sklyar.MySecondTestAppSpringBoot.util.DateTimeUtil;
import ru.sklyar.MySecondTestAppSpringBoot.service.ModifyRequestService;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class MyController {
    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final List<ModifyRequestService> modifyRequestService;
    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        List<ModifyRequestService> modifyRequestService)  {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;

    }
    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        log.info("request: {}", request);

        long startTime = System.currentTimeMillis();

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        try {
            if (Objects.equals(request.getUid(), "123")) {
                throw new UnsupportedCodeException("Uid = 123 unsupported");
            }
        } catch (UnsupportedCodeException e) {
            log.error("error: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("request: {}", request);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            log.error("error: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("request: {}", request);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("request: {}", request);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        modifyResponseService.modify(response);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("response: {}", response, executionTime);
        modifyRequestService.forEach(service -> service.modify(request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
