package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderServiceTest {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    void BeforeEach(){
//        AppConfig appconfig = new AppConfig();
//        memberService = appconfig.memberService();
//        orderService = appconfig.orderService();
        memberService = ac.getBean("memberService", MemberService.class);
        orderService = ac.getBean("orderService", OrderService.class);
    }

    @Test
    @DisplayName("모든 Bean 출력하기")
    void findAllBean() {
        String[] beanDifinitionNames = ac.getBeanDefinitionNames();

        for(String value : beanDifinitionNames){
            Object bean = ac.getBean(value);
            System.out.println("name = " + beanDifinitionNames + ", object = " + bean);
        }
    }

    @Test
    void createOrder (){
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 20000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
