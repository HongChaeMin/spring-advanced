package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute();
        client.execute();
        client.execute();

        // 실행 결과
        // CacheProxy - 프록시 호출
        // RealSubject - 실제 객체 호출
        // CacheProxy - 프록시 호출
        // CacheProxy - 프록시 호출

        // 1. client의 cacheProxy 호출 cacheProxy에 캐시 값이 없다. -> realSubject를 호출, 결과를 캐시에 저장 (1초)
        // 2. client의 cacheProxy 호출 cacheProxy에 캐시 값이 있다. -> cacheProxy에서 즉시 반환 (0초)
        // 3. client의 cacheProxy 호출 cacheProxy에 캐시 값이 있다. -> cacheProxy에서 즉시 반환 (0초)
    }

}
