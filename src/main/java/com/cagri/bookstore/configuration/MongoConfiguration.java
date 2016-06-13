package com.cagri.bookstore.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;

/**
 * Created by cagri.dursun on 9.6.2016.
 */
@Configuration
@EnableMongoRepositories
@ComponentScan(basePackageClasses = {ApplicationConfig.class})
@PropertySource("classpath:local.properties")
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Autowired
    Environment env;

    @Override
    protected String getDatabaseName() {
        return env.getProperty("mongodb.dbname");
    }

    @Override
    public Mongo mongo() throws Exception {
        final MongoCredential mongoCredential = MongoCredential.createCredential(env.getProperty("mongodb.user"),getDatabaseName(),env.getProperty("mongodb.password").toCharArray());
        return new MongoClient(new ArrayList<ServerAddress>() {{ add(new ServerAddress(env.getProperty("mongodb.server.address"), Integer.parseInt(env.getProperty("mongodb.server.port")))); }},
                new ArrayList<MongoCredential>(){{add(mongoCredential);}});
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.cagri.bookstore.model";
    }


}
