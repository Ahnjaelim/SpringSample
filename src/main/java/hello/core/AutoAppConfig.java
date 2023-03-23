package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.*;

/*
보통은 설정정보가 스캔대상에서 제외되나, 기존의 예제코드 보존을 위해 excludeFilters 사용
* 디폴트값 : @ComponentScan이 붙은 클래스의 하위 스캔
<권장 방법>
패키지의 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것이다. 스프링 부트도 이와 같은 방법을 사용.
ex) hello.core 바로 아래 설정 클래스를 생성, @ComponentScan 어노테이션을 붙이고 basePackages 속성은 생략.
=> hello.core를 포함, 하위 클래스들이 모두 스캔의 대상이된다.
cf) 스프링부트의 시작 정보인 @SpringBootApplication이 붙은 클래스 역시 프로젝트 최상위에 위치해있다.
(그리고 그 설정안에 @ComponentScan이 들어있다. 때문에 부트에선 @ComponentScan을 쓸 필요 X)

@Component 뿐만아니라 아래와 같은 내용도 스캔 대상에 포함된다.
@Repository
@Service
@Controller
@Configuration

 */

@Configuration
@ComponentScan(
        // basePackages = "hello.core",
        // basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
    /*
    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

    => 수동등록 빈이 우선순위가 더 높다. 때문에 자동 Overriding 되므로 빈이 중복돼도 오류 X
    실무에선 일부러 중복을 만든다기보단 의도치 않게 중복되는 경우가 발생함.
    * 스프링 부트 오류 메세지 : The bean 'memoryMemberRepository', defined in class path resource [hello/core/AutoAppConfig.class], could not be registered. A bean with that name has already been defined in file [C:\Users\306\IdeaProjects\core\out\production\classes\hello\core\member\MemoryMemberRepository.class] and overriding is disabled.
    * 스프링 부트 설정에서 설정을 변경할 수 있음 : spring.main.allow-bean-definition-overriding=true
    */

}

