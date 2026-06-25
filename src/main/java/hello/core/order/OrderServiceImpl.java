package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.*;

public class OrderServiceImpl implements OrderService{

    // 역할과 구현은 분리하였지만, 객체지향 설계원칙을 충실히 준수하지 못했다.
    // DIP : 주문서비스 (OrderServiceImpl)은 DiscountPolicy 인터페이스에 의존하면서 구현체 클래스에도 의존하고 있음.
    // OCP : 지금 코드의 기능 [Repository (DB,Memory), DiscountPolicy (Fix, Rate)]을 변경하면 클라이언트 코드에 영향을 준다.
    // -> OCP 위반 ( 예- 할인정책 변경 시 OrderServiceImpl 코드 수정해야 함.)
    // -> 해결책 : 추상에만 의존하도록 변경 (인터페이스에만 의존하도록) / 누군가 구현객체를 대신 생성하고 주입하도록 변경
    //private final MemberRepository memberRepository;
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.disCount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
