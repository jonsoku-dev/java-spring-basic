package hello.core.web;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogDemoService {
    //    private final ObjectProvider<MyLogger> myLoggerObjectProvider;
    private final MyLogger myLogger;

    public void logic(String id) {
//        MyLogger myLogger = myLoggerObjectProvider.getObject();

        // 스코프에 프록시를 사용하면, 가짜로 등록했다가, 실행되는 시점인 이때 진짜를 찾아서 등록한다.
        myLogger.log("service id = " + id);
    }
}
