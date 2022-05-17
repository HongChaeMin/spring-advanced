package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    // hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {}

    // 클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService() {} // 외부 사용 불가

    // allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}

}
