package hello.core.singletone;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;
import hello.core.member.Member;
import hello.core.member.MemberService;

public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        //1.조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        //2.조회: 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다른 것을 확인 - 계속해서 다른 객체가 생성되는것은 안된다.
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        //memberService1 !== memberService2
        //요청할때마다 객체를 계속 생성하게된다. -> 무리...
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
        /**
         * 우리가 만들었던 스프링 없는 순수한 DI 컨테이너인 AppConfig는 요청을 할 때 마다 객체를 새로 생성한다.
         * 고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸된다! 메모리 낭비가 심하다.
         * 해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 된다. 싱글톤 패턴 -> 다음 테스트
         */
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("instance1 = " + singletonService1);
        System.out.println("instance2 = " + singletonService2);

        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
        //same == (instance비교)
        //equal
    }

    @Test
    @DisplayName("스프링컨테이너와 싱글톤")
    void springContainer() {
        /**
         * 싱글톤 컨테이너
         * 스프링 컨테이너는 싱글톤 패턴의 문제점을 해결하면서, 객체 인스턴스를 싱글톤(1개만 생성)으로 관리한
         * 다.
         * 지금까지 우리가 학습한 스프링 빈이 바로 싱글톤으로 관리되는 빈이다.
         * 싱글톤 컨테이너
         * 스프링 컨테이너는 싱글턴 패턴을 적용하지 않아도, 객체 인스턴스를 싱글톤으로 관리한다.
         * 이전에 설명한 컨테이너 생성 과정을 자세히 보자. 컨테이너는 객체를 하나만 생성해서 관리한다.
         * 스프링 컨테이너는 싱글톤 컨테이너 역할을 한다. 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤
         * 레지스트리라 한다.
         * 스프링 컨테이너의 이런 기능 덕분에 싱글턴 패턴의 모든 단점을 해결하면서 객체를 싱글톤으로 유지할 수
         * 있다.
         * 싱글톤 패턴을 위한 지저분한 코드가 들어가지 않아도 된다.
         * DIP, OCP, 테스트, private 생성자로 부터 자유롭게 싱글톤을 사용할 수 있다.
         */
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);

        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }
}

/*
private으로 new 키워드를 막아두었다.
호출할 때 마다 같은 객체 인스턴스를 반환하는 것을 확인할 수 있다.
> 참고: 싱글톤 패턴을 구현하는 방법은 여러가지가 있다. 여기서는 객체를 미리 생성해두는 가장 단순하고 안
전한 방법을 선택했다.
싱글톤 패턴을 적용하면 고객의 요청이 올 때 마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를 공유
해서 효율적으로 사용할 수 있다. 하지만 싱글톤 패턴은 다음과 같은 수 많은 문제점들을 가지고 있다.
싱글톤 패턴 문제점
싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
의존관계상 클라이언트가 구체 클래스에 의존한다. DIP를 위반한다.
클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
테스트하기 어렵다.
내부 속성을 변경하거나 초기화 하기 어렵다.
private 생성자로 자식 클래스를 만들기 어렵다.
결론적으로 유연성이 떨어진다.
안티패턴으로 불리기도 한다.
 */
