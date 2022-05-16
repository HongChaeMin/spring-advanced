package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("interface가 있으면 dynamic proxy JDK 사용")
    void interceptor() {
        ServiceInterface target = new ServiceImpl();

        // 프록시 팩토리를 생성할 때, 생성자에 프록시의 호출 대상을 함께 넘겨준다
        // 프록시 팩토리는 이 인스턴스 정보를 기반으로 프록시를 만들어낸다
        // isInterface ? JDK dynamic proxy : CGLIB dynamic proxy
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // 프록시 팩토리를 통해서 만든 프록시가 사용할 부가 기능 로직을 설정한다
        // JDK 동적 프록시가 제공하는 InvocationHandler 와 CGLIB가 제공하는 MethodInterceptor 의 개념과 유사하다
        // 이렇게 프록시가 제공하는 부가 기능 로직을 어드바이스 ( Advice )라 한다. 번역하면 조언을 해준다고 생각
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass : {}", target.getClass());
        log.info("proxyClass : {}", proxy.getClass());

        proxy.save();

        // 프록시 팩토리를 통해서 프록시가 생성되면 JDK 동적 프록시나, CGLIB 모두 참
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        // 프록시 팩토리를 통해서 프록시가 생성되고, JDK 동적 프록시인 경우 참
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        // 프록시 팩토리를 통해서 프록시가 생성되고, CGLIB 동적 프록시인 경우 경우 참
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }
}
