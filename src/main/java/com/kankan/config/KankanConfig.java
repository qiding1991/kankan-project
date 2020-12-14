package com.kankan.config;

import com.kankan.service.MailSender;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KankanConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.mail")
    public MailConfig jMailConfig() {
        return new MailConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "mail.content")
    public MailSender jMailService() {
        return new MailSender();
    }

    @Bean
    public GridFSBucket gridFSBucket(MongoClient mongoClient){
        MongoDatabase database=mongoClient.getDatabase("kankan-fs");
        return GridFSBuckets.create(database);
    }



}
