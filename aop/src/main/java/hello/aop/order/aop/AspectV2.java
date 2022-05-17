package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    // @Pointcut 에 포인트컷 표현식을 사용
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() {} // pointcut signature = method name + parameter
    // 메서드의 반환 타입은 void
    // 코드 내용은 비워둔다

    // private , public 같은 접근 제어자는 내부에서만 사용하면 private 을 사용해도 되지만,
    // 다른 애스팩트에서 참고하려면 public 을 사용

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable { // advice
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그치처
        return joinPoint.proceed();
    }

}
