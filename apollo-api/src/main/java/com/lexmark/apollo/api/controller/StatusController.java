package com.lexmark.apollo.api.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lexmark.apollo.api.dto.StatusDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class StatusController {
    
    @RequestMapping(value = "/status", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public StatusDto getStatus(){
        StatusDto statusDto = new StatusDto();
        statusDto.setCurrentDate(new Date());
        statusDto.setVersion("0.0.1");
        return statusDto;
    }
}
