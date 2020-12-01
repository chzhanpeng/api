package com.capitalone.dashboard.config;

import com.capitalone.dashboard.model.Commit;
import com.capitalone.dashboard.rest.HomeController;
import com.capitalone.dashboard.settings.ApiSettings;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.ff4j.FF4j;
import org.ff4j.audit.repository.InMemoryEventRepository;
import org.ff4j.core.FeatureStore;
import org.ff4j.mongo.store.FeatureStoreMongo;
import org.ff4j.mongo.store.PropertyStoreMongo;
import org.ff4j.property.store.InMemoryPropertyStore;
import org.ff4j.store.InMemoryFeatureStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ff4j.*;

@Configuration
public class FF4jConfig {

    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoConfig mongoConfig;

    @Autowired
    ApiSettings apiSettings;

    private static final Logger LOGGER = LoggerFactory.getLogger(FF4jConfig.class);

    @Bean
    public FF4j getFF4j() {

        FF4j ff4j = new FF4j();

        try {
            MongoDatabase mongoDB = mongoConfig.mongo().getDatabase(mongoConfig.getDatabaseName());
//            MongoCollection<Document> commits = mongoDB.getCollection("commits");
//            LOGGER.info(String.valueOf(commits.count()));
//            mongoDB.getCollection(apiSettings.getFF4JFeatureCollection());

            ff4j.setFeatureStore(new FeatureStoreMongo(mongoDB.getCollection(apiSettings.getFF4JFeatureCollection())));
            ff4j.setPropertiesStore(new PropertyStoreMongo(mongoDB.getCollection(apiSettings.getFF4JPropertiesCollection())));
//            ff4j.createFeature("feature1", false);
//            ff4j.createFeature("feature2", false);
//            ff4j.createFeature("feature_reject_issue", true);
//            MongoCollection<Document> features = fsm.getFeaturesCollection();
//            mongoDB.getCollection(apiSettings.getFF4JFeatureCollection()).update
        } catch ( Exception e) {

            LOGGER.info(e.toString());
        }

        // Enabling audit and monitoring, default value is false
        ff4j.audit(true);

        // When evaluting not existing features, ff4j will create then but disabled
        ff4j.autoCreate(true);

        // To define RBAC access, the application must have a logged user
        //ff4j.setAuthManager(...);

        // To define a cacher layer to relax the DB, multiple implementations
        //ff4j.cache([a cache Manager]);

        return ff4j;
    }
}