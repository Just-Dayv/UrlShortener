package com.interswitch.urlshortener.controller;

import com.interswitch.urlshortener.controller.model.Response;
import com.interswitch.urlshortener.controller.model.UrlResponse;
import com.interswitch.urlshortener.model.Url;
import com.interswitch.urlshortener.service.UrlGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/")
public class UrlController {

    @Autowired
    UrlGenService urlGenService;

    @PostMapping("shortener")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Response response (@RequestBody @Validated String longUrl, HttpServletRequest request){

        Url  urlCreated = urlGenService.createUrl(longUrl);
        return new UrlResponse("200","SUCCESSFUL",null,request.getRequestURL()+urlCreated.getShortUrlValue());
    }

    @GetMapping("returnLongValue")
    @ResponseStatus(HttpStatus.OK)
    public Response value (String shortValue)
    {

        return new UrlResponse("200","",null, urlGenService.getOriginalUrl(shortValue).getLongUrlValue());
    }
}
