package com.interswitch.urlshortener.service;

import com.google.common.hash.Hashing;
import com.interswitch.urlshortener.dao.UrlDao;
import com.interswitch.urlshortener.exception.InvalidRequestException;
import com.interswitch.urlshortener.exception.RequestRejectedException;
import com.interswitch.urlshortener.model.Url;


import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

@Service
public class UrlGenService {

    @Autowired
    UrlDao urlDao;

    public Url createUrl(String longUrlValue){

//        UrlValidator urlValidator = new UrlValidator(new String[]{"http"});
//        if(urlValidator.isValid(longUrlValue)){
//            System.out.println(longUrlValue);
       // }
        String [] validate = longUrlValue.split("//");
        if (validate.length > 1 && (validate[0].contains("http")||validate[0].contains("https")))
            /** The validator class and library I tried to use wasn't working so I did a simple validation to constrain value entry
         * */
            {
            String shortUrlValue = Hashing.murmur3_32().hashString(longUrlValue, StandardCharsets.UTF_8).toString();

            if (urlDao.findByLongUrl(longUrlValue) == null) {
                Url url = new Url();
                url.setLongUrlValue(longUrlValue);
                url.setShortUrlValue(shortUrlValue);
                return urlDao.create(url);
            }
            throw new RequestRejectedException("url already created");
        }
        else
            throw new InvalidRequestException("Enter valid Url");

    }

    public Url getOriginalUrl (String shortUrlValue)
    {
        if ((urlDao.findByShortUrl(shortUrlValue)) == null) {
            throw new RequestRejectedException("url doesn't exist in record ");
        }



         return urlDao.findByShortUrl(shortUrlValue);

    }

}
