package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({ParameterTest.ParameterAspect.class})
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService proxy : {}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember() {}

        // joinPoint.getArgs()[0] 와 같이 매개변수를 전달 받는다
        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1] : {}, [arg] : {}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        // args(arg,..) 와 같이 매개변수를 전달 받는다
        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2] : {}, [arg] : {}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        // @Before 를 사용한 축약 버전이다. 추가로 타입을 String 으로 제한했다
        @Before("allMember() && args(arg, ..)")
        public void logArgs3(String arg){
            log.info("[arg] : {}", arg);
        }

        // 프록시 객체를 전달 받는다
        @Before(value = "allMember() && this(obj)", argNames = "joinPoint,obj")
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this] : {}, [obj] : {}", joinPoint.getSignature(), obj.getClass());
        }

        // 실제 대상 객체를 전달 받는다
        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[target] : {}, [obj] : {}", joinPoint.getSignature(), obj.getClass());
        }

        // 타입의 애노테이션을 전달 받는다
        @Before("allMember() && @target(annotation)")
        public void atThis(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target] : {}, obj : {}", joinPoint.getSignature(), annotation);
        }

        @Before("allMember() && @within(annotation)")
        public void atAnnotation(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within] : {}, obj : {}", joinPoint.getSignature(), annotation);
        }

        // 메서드의 애노테이션을 전달 받는다
        // 여기서는 annotation.value() 로 해당 애노테이션의 값을 출력하는 모습을 확인
        @Before("allMember() && @annotation(annotation)")
        public void atWithin(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation] : {}, annotation value :{}", joinPoint.getSignature(), annotation.annotationType());
        }

    }

}
