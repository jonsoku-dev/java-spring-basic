package hello.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

/**
 * AppConfig는 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다.
 * AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결) 해준다. -> injection
 * AppConfig를 통해서 관심사를 확실하게 분리했다.
 * 배역, 배우를 생각해보자.
 * AppConfig는 공연 기획자다.
 * AppConfig는 구체 클래스를 선택한다. 배역에 맞는 담당 배우를 선택한다. 애플리케이션이 어떻게 동작해
 * 야 할지 전체 구성을 책임진다.
 * 이제 각 배우들은 담당 기능을 실행하는 책임만 지면 된다.
 * OrderServiceImpl 은 기능을 실행하는 책임만 지면 된다.
 */

// 앱의 구성정보를 담아두는 곳
@Configuration
public class AppConfig {
    // @Bean을 등록하면, 스프링 컨테이너에 등록이 된다.
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
