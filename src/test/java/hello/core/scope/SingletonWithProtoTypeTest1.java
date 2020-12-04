package hello.core.scope;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithProtoTypeTest1 {
    @Test
    void protoTypeFind() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, ProtoTypeBean.class);
//        ProtoTypeBean protoTypeBean1 = ac.getBean(ProtoTypeBean.class);
//        protoTypeBean1.addCount();
//        Assertions.assertThat(protoTypeBean1.getCount()).isEqualTo(1);
//
//        ProtoTypeBean protoTypeBean2 = ac.getBean(ProtoTypeBean.class);
//        protoTypeBean2.addCount();
//        Assertions.assertThat(protoTypeBean2.getCount()).isEqualTo(1);

        /**
         * 스프링은 일반적으로 싱글톤 빈을 사용하므로, 싱글톤 빈이 프로토타입 빈을 사용하게 된다. 그런데 싱글톤
         * 빈은 생성 시점에만 의존관계 주입을 받기 때문에, 프로토타입 빈이 새로 생성되기는 하지만, 싱글톤 빈과 함
         * 께 계속 유지되는 것이 문제다.
         * 아마 원하는 것이 이런 것은 아닐 것이다. 프로토타입 빈을 주입 시점에만 새로 생성하는게 아니라, 사용할
         * 때 마다 새로 생성해서 사용하는 것을 원할 것이다.
         * > 참고: 여러 빈에서 같은 프로토타입 빈을 주입 받으면, 주입 받는 시점에 각각 새로운 프로토타입 빈이 생성
         * 된다. 예를 들어서 clientA, clientB가 각각 의존관계 주입을 받으면 각각 다른 인스턴스의 프로토타입 빈을
         * 주입 받는다.
         * > clientA prototypeBean@x01
         * > clientB prototypeBean@x02
         * > 물론 사용할 때 마다 새로 생성되는 것은 아니다.
         */

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);

    }
// // 스프링 기능
//    @Scope("singleton")
//    static class ClientBean {
//        // 직접찾는게 아니라 찾아줌.
//        @Autowired
//        private ObjectProvider<ProtoTypeBean> protoTypeBeanObjectProvider;
//
//        public int logic() {
//            ProtoTypeBean protoTypeBean = protoTypeBeanObjectProvider.getObject();
//            protoTypeBean.addCount();
//            return protoTypeBean.getCount();
//        }
//    }

    // 단순한기능
    @Scope("singleton")
    static class ClientBean {
        // 직접찾는게 아니라 찾아줌.
        @Autowired
        private Provider<ProtoTypeBean> protoTypeBeanObjectProvider;

        public int logic() {
            ProtoTypeBean protoTypeBean = protoTypeBeanObjectProvider.get();
            protoTypeBean.addCount();
            return protoTypeBean.getCount();
        }
    }


    @Scope("prototype")
    static class ProtoTypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("ProtoTypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("ProtoTypeBean.destroy " + this);
        }
    }
}
