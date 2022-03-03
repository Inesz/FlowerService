package com.example.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DBTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    EntityManager entityManager;

    @Test
    void printBeans() {
        for (String name : applicationContext.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        assertNotNull(applicationContext);
    }

    @Test
    void getFromDB() {
        List result = entityManager.createNativeQuery("SELECT flower FROM flower").getResultList();

        Iterator it = result.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        Assertions.assertNotNull(entityManager);
    }

}
