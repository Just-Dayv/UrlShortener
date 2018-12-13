package com.interswitch.urlshortener.controller;

import com.interswitch.urlshortener.controller.model.Response;
import com.interswitch.urlshortener.controller.model.UrlResponse;
import com.interswitch.urlshortener.model.Url;
import com.interswitch.urlshortener.service.UrlGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class UrlController {
    private final UrlGenService urlGenService;

    @Autowired
    public UrlController(UrlGenService urlGenService) {
        this.urlGenService = urlGenService;
    }

    @PostMapping("/shortener")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Response response (@RequestBody @Validated String longUrl, HttpServletRequest request){

        Url  urlCreated = urlGenService.createUrl(longUrl);
        return new UrlResponse("200","SUCCESSFUL",null,urlCreated.getShortUrlValue());
    }

    @GetMapping("/{returnLongValue}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity value (@PathVariable String returnLongValue)
    {
       String longUrl = urlGenService.getOriginalUrl(returnLongValue).getLongUrlValue();

          ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION,longUrl).build();

          return responseEntity;
    }
}
