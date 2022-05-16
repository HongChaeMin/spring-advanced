package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        // 공통 로직 1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result = {}", result1);
        // 공통 로직 1 종료

        // 공통 로직 2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result = {}", result2);
        // 공통 로직 2 종료

    }

    @Test
    void reflection1() throws Exception {
        // 클래스 정보
        // 클래스 메타정보를 획득, 참고로 내부 클래스는 구분을 위해 $ 를 사용
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        // callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA"); // 해당 클래스의 call 메서드 메타정보를 획득
        Object result1 = methodCallA.invoke(target); // 획득한 메서드 메타정보로 실제 인스턴스의 메서드를 호출
        log.info("result1 : {}", result1);

        // callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB"); // 해당 클래스의 call 메서드 메타정보를 획득
        Object result2 = methodCallB.invoke(target);
        log.info("result2 : {}", result2);

        // 중요한 핵심은 클래스나 메서드 정보를 동적으로 변경
    }

    @Test
    void reflection2() throws Exception {
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        dynamicCall(classHello.getMethod("callA"), target);
        dynamicCall(classHello.getMethod("callB"), target);
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result1 : {}", result);
    }

    @Slf4j
    static class Hello {

        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }

    }

}
