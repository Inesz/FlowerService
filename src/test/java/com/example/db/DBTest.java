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
import java.util.Arrays;
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
    void triggerTest() {
        Object result = entityManager.createNativeQuery("SELECT count(*) FROM flowers_history WHERE new_name LIKE 'tmp%' OR old_name LIKE 'tmp%'").getSingleResult();
        assertEquals(new BigInteger("3"), result);
    }

    @Test
    void joinTest() {
        //-- get count of orders for each flower
        String query1 = "SELECT public.flowers.name, count(DISTINCT public.items.order_id) as count_of_orders " +
                "FROM public.flowers " +
                "LEFT JOIN public.items ON flowers.id = public.items.flower_id " +
                "GROUP BY public.flowers.name;";

        //--get name of the flower, name of the customer, count current flower item in order
        String query2 = "SELECT public.flowers.name, orders.consumer, count( public.items.order_id) AS count_in_order " +
                "FROM public.flowers " +
                "INNER JOIN public.items ON flowers.id = public.items.flower_id " +
                "LEFT JOIN orders ON items.order_id = orders.id " +
                "GROUP BY public.items.order_id,public.flowers.name, orders.consumer;";

        //--get summary quantity of each order
        String query3 = "SELECT public.orders.id, SUM( public.items.quantity)AS flowers_quantity " +
                "FROM orders " +
                "RIGHT JOIN items on orders.id = items.order_id " +
                "GROUP BY public.orders.id;";

        //-- get names of flowers in asc odrer
        String query4 = "SELECT STRING_AGG( public.flowers.name, ', ' ORDER BY public.flowers.name ASC)FROM flowers;";

        printResultList(entityManager.createNativeQuery(query1).getResultList());
        printResultList(entityManager.createNativeQuery(query2).getResultList());
        printResultList(entityManager.createNativeQuery(query3).getResultList());
        printResultList(entityManager.createNativeQuery(query4).getResultList());

        assertTrue(true);
    }

    void printResultList(List result) {
        ListIterator iterator = result.listIterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o instanceof Object[]) {
                System.out.println(Arrays.deepToString((Object[]) o));
            } else {
                System.out.println(o);
            }
        }
    }

}
