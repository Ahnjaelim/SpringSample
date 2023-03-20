package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;
    /*
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); => 정액 할인 정책
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); => 이를 정률 할인 정책으로 변경했다.
    => 선언부의 코드를 수정했기때문에 OCP 위반, interface에만 의존하지 않기때문에 DIP 위반
    OCP (Open/Closed Principle) : 소프트웨어 요소는 확장에는 열려있어야하나, 변경에는 닫혀있어야한다.
    DIP (Dependency inversion Principle) : 소프트웨어는 추상화에 의존해야하며, 구체화에 의존하면 안된다.
    여기서 OCP, DIP를 위반하지 않는 방법은?
    */
    private final DiscountPolicy discountPolicy;
    /*
    => OCP, DIP의 원칙은 지켰지만 interface만 덜렁있기때문에 당연히 Null Point Exception 발생.
    => 위 인터페이스에 구현체를 누군가가 따로 주입해줘야함. 때문에 별도의 설정 클래스를 생성.
    */

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy){
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discuontPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discuontPrice);
    }

}
