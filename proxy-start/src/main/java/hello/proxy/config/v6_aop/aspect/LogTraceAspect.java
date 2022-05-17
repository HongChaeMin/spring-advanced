package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Slf4j
@Aspect // 애노테이션 기반 프록시를 적용할 때 필요
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    // @Around 의 값에 포인트컷 표현식을 넣는다. 표현식은 AspectJ 표현식을 사용
    // @Around 의 메서드는 어드바이스( Advice )가 된다
    @Around("execution(* hello.proxy.app..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;

        // ProceedingJoinPoint
        // - 어드바이스에서 살펴본 MethodInvocation invocation과 유사한 기능이다
        // - 내부에 실제 호출 대상, 전달 인자, 그리고 어떤 객체와 어떤 메서드가 호출되었는지 정보가 포함

        // 실제 호출 대상 : joinPoint.getTarget()
        // 전달 인자 : joinPoint.getArgs()
        // join point : joinPoint.getSignature()

        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // target 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }

}
