package com.interswitch.urlshortener.service;

import com.google.common.hash.Hashing;
import com.interswitch.urlshortener.dao.UrlDao;
import com.interswitch.urlshortener.exception.InvalidRequestException;
import com.interswitch.urlshortener.exception.RequestRejectedException;
import com.interswitch.urlshortener.model.Url;


import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;


@Service
public class UrlGenService {

    @Autowired
    UrlDao urlDao;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Url createUrl(String longUrlValue, HttpServletRequest request){

        logger.info("Receiving long url but checking first to see if url is a valid http or https call");
        UrlValidator urlValidator = new UrlValidator(new String[]{"http","https"});
        if(urlValidator.isValid(longUrlValue))
        {
            logger.info("After validating url,convert into short string using hashing library");
            String shortUrlValue = Hashing.murmur3_32().hashString(longUrlValue, StandardCharsets.UTF_8).toString();
            logger.info("before creating the value in db, check if exists already");
            if (urlDao.findByLongUrl(longUrlValue) == null) {
                Url url = new Url();

                url.setLongUrlValue(longUrlValue);
                url.setShortUrlValue(shortUrlValue);
                logger.info("method call to urlDao create function to insert new model in db");
                return urlDao.create(url);
            }

            logger.info("throw an exception to indicate shortened Url for the link exists in db and return value");
            throw new RequestRejectedException("url already created:   "
                    + request.getRequestURL().toString()
                    .replace(request.getRequestURI(),"/"+urlDao.findByLongUrl(longUrlValue).getShortUrlValue()));
        }
        else
            throw new InvalidRequestException("Enter valid Url");

    }

    public Url getOriginalUrl (String shortUrlValue)
    {
        logger.info("if short url is provide, check if exists");
        if ((urlDao.findByShortUrl(shortUrlValue)).getLongUrlValue()== null) {
            throw new RequestRejectedException("url doesn't exist in record ");
        }
        logger.info("return url model");
         return urlDao.findByShortUrl(shortUrlValue);

    }

}
