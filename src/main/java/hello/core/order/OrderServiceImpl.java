package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor // => final 키워드가 붙은 필드에 생성자를 만들어준다.
public class OrderServiceImpl implements OrderService{

    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    /*
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); => 정액 할인 정책
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); => 이를 정률 할인 정책으로 변경했다.
    => 선언부의 코드를 수정했기때문에 OCP 위반, interface에만 의존하지 않기때문에 DIP 위반
    OCP (Open/Closed Principle) : 소프트웨어 요소는 확장에는 열려있어야하나, 변경에는 닫혀있어야한다.
    DIP (Dependency inversion Principle) : 소프트웨어는 추상화에 의존해야하며, 구체화에 의존하면 안된다.
    여기서 OCP, DIP를 위반하지 않는 방법은?

    => OCP, DIP의 원칙은 지켰지만 interface만 덜렁있기때문에 당연히 Null Point Exception 발생.
    => 위 인터페이스에 구현체를 누군가가 따로 주입해줘야함. 때문에 별도의 설정 클래스를 생성.
    */

    private final DiscountPolicy discountPolicy;
    private final MemberRepository memberRepository;

    // 생성자에 주입을 사용하면 final 키워드를 사용할 수 있다. 수정자 주입을 포함한 나머지 주입 방식은 모두 생성자 이후에 호출되므로, 필드에 'final'키워드를 사용할 수 없다.
    // 오직 생성자 주입 방식만 'final' 키워드를 사용할 수 있다.

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy){
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    /*
    아하! 롬복때문에 생성자 주입이 생략된거였음! 그래서 그동안 그냥 아묻따 private fianl만 써도 생성자 주입이 됐던거였음!
    이런 구체 클래스에는 직접적으로 @Autowired를 쓰는것을 권장하지 않는다. 스프링이 아닌 자바 코드 자체를 테스트할 수 없기때문.
    */



    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discuontPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discuontPrice);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
