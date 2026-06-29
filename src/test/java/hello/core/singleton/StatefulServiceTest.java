package hello.core.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingletone() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        //Thread A : A사용자가 10000원 주문
        statefulService1.order("UserA", 10000);
            
        //Thread B : B사용자가 20000원 주문
        statefulService2.order("UserB", 20000);
        
        //Thread A가 주문금액 조회 
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        org.assertj.core.api.Assertions.assertThat(price).isEqualTo(20000);
        
    }

    @Configuration
    static class TestConfig {

        @Bean
        public StatefulService statefulService () {
            return new StatefulService();
        }
    }

}