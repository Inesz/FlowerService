package com.example.db;

import com.example.flower.model.Flower;
import com.example.flower.repository.FlowerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DBTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    EntityManager entityManager;

    @Autowired
    FlowerRepository flowerRepository;

    @Test
    void printBeans() {
        for (String name : applicationContext.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        assertNotNull(applicationContext);
    }

    @Test
    void getFromDB() {
        List result = entityManager.createNativeQuery("SELECT name FROM flowers").getResultList();

        Iterator it = result.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        Assertions.assertNotNull(entityManager);
    }

    @Test
    void getByJPA() {
        List<Flower> flowers = flowerRepository.findAll();
        flowers.forEach(f -> System.out.println(f.getItems().size()));
        ListIterator<Flower> it = flowers.listIterator();
        int flowersSum = 0;

        while (it.hasNext()) {
            flowersSum += it.next().getItems().size();
        }

        assertTrue(flowersSum > 0);
    }

    @Test
    void functionTest() {
        Object result = entityManager.createNativeQuery("SELECT get_name(cast(1 as bigint))").getSingleResult();
        assertEquals("fuchsia", result);
    }

    @Test
    void triggerTest(){
        Object result = entityManager.createNativeQuery("SELECT count(*) FROM flowers_history WHERE new_name LIKE 'tmp%' OR old_name LIKE 'tmp%'").getSingleResult();
        assertEquals(new BigInteger("3"), result);
    }

}
