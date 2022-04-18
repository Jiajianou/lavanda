package com.lavanda.lavashop.config;

import com.lavanda.lavashop.entity.Product;
import com.lavanda.lavashop.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager _entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager){
        _entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        //disable Http mehthods for products
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure(((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions) ));

        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure(((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions) ));

        //call an internal helper method to expose the id
        exposeIds(config);
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        //expose entity ids

        // get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = _entityManager.getMetamodel().getEntities();

        //create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // get the entity types for the entities
        for (EntityType entityType : entities){

            entityClasses.add(entityType.getJavaType());
        }

        // expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
