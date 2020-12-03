package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        /**
         * 어노테이션이 붙은놈은 다 스캔하고, 예외적으로, class형은 제외한다 (AppConfig때문에)
         * 컴포넌트 스캔을 사용하면 @Configuration 이 붙은 설정 정보도 자동으로 등록되기 때문에,
         * AppConfig, TestConfig 등 앞서 만들어두었던 설정 정보도 함께 등록되고, 실행되어 버린다. 그래서
         * excludeFilters 를 이용해서 설정정보는 컴포넌트 스캔 대상에서 제외했다. 보통 설정 정보를 컴포넌트
         * 스캔 대상에서 제외하지는 않지만, 기존 예제 코드를 최대한 남기고 유지하기 위해서 이 방법을 선택했다.
        */
//        basePackages = "hello.core", // 기본위치 지정
//        basePackageClasses = AutoAppConfig.class, // 이 클래스가 있는 패키지가 기본
        // 두개다 지정하지않으면 AutoAppConfig의 위치가 기본으로 !
        // 그래서 설정 정보 클래스의 위치를 프로젝트 최상단에 두는것이 기본
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
}
