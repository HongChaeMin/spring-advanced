package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class AdvisorTest {

    @Test
    void advisorTest1() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Advisor 인터페이스의 가장 일반적인 구현체
        // 생성자를 통해 하나의 포인트컷과 하나의 어드바이스를 넣어주면 된다 (항상 true 를 반환하는 포인트컷, 앞서 개발한 TimeAdvice 어드바이스를 제공)
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());

        // 프록시 팩토리에 적용할 어드바이저를 지정한다
        // 어드바이저는 내부에 포인트컷과 어드바이스를 모두 가지고 있다
        // 따라서 어디에 어떤 부가 기능을 적용해야 할지 어드바이스 하나로 알 수 있다
        // 프록시 팩토리를 사용할 때 어드바이저는 필수
        proxyFactory.addAdvice(advisor.getAdvice()); // 강의에는 .getAdvice() 가 없는데 버전이 바뀌어서 그런가봄?
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();

        // 그런데 생각해보면 이전에 분명히 proxyFactory.addAdvice(new TimeAdvice()) 이렇게
        // 어드바이저가 아니라 어드바이스를 바로 적용했다
        // 이것은 단순히 편의 메서드이고 결과적으로 해당 메서드 내부에서 지금 코드와 똑같은 다음 어드바이저가 생성
    }

}
