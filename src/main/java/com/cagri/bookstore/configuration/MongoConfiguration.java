package com.cagri.bookstore.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;

/**
 * Created by cagri.dursun on 9.6.2016.
 */
@Configuration
@EnableMongoRepositories
@ComponentScan(basePackageClasses = {ApplicationConfig.class})
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "bookStoreDb";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(new ArrayList<ServerAddress>() {{ add(new ServerAddress("127.0.0.1", 27017)); }});

    }

    @Override
    protected String getMappingBasePackage() {
        return "com.cagri.bookstore.model";
    }


}
