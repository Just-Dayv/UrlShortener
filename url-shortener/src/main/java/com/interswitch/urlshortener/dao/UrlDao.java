package com.interswitch.urlshortener.dao;

import com.interswitch.urlshortener.model.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;


@Repository
public class UrlDao {


    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String SINGLE_RESULT = "object";
    private SimpleJdbcCall create,getUrlByLong, getUrlByShort;


    @Autowired
    public void setDataSource(@Qualifier(value = "dataSource") DataSource dataSource) {
        logger.info("provided data source using hikari configuration");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
         create = new SimpleJdbcCall(dataSource).withProcedureName("uspCreateUrlShortener").withReturnValue();
         getUrlByLong = new SimpleJdbcCall(jdbcTemplate).withProcedureName("checkIfExistsByLongUrl")
                .returningResultSet(SINGLE_RESULT, new BeanPropertyRowMapper<>(Url.class));
        getUrlByShort = new SimpleJdbcCall(jdbcTemplate).withProcedureName("checkIfExistsByShortUrl")
                .returningResultSet(SINGLE_RESULT, new BeanPropertyRowMapper<>(Url.class));
    }

    public Url findByLongUrl (String longUrlValue) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("LongUrlValue", longUrlValue);
        Map<String, Object> m = getUrlByLong.execute(in);
        List<Url> list = (List<Url>) m.get(SINGLE_RESULT);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Url findByShortUrl (String shortUrlValue) {
        System.out.println(shortUrlValue);
        SqlParameterSource in = new MapSqlParameterSource().addValue("ShortUrlValue", shortUrlValue);
        Map<String, Object> m = getUrlByShort.execute(in);
        List<Url> list = (List<Url>) m.get(SINGLE_RESULT);
        if (list == null || list.isEmpty()) {
            return null;
        }
        System.out.println(list.get(0).getShortUrlValue());
        return list.get(0);

    }

    public Url create(Url model) throws DataAccessException {
        SqlParameterSource in = new BeanPropertySqlParameterSource(model);
        create.execute(in);
        return model;
    }

}
