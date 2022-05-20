package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV2Test {

    @Autowired
    CallServiceV2 callServiceV2;

    @Test
    void external() {
        callServiceV2.external();

        // CallLogAspect : aop=void hello.aop.internalcall.CallServiceV2.external()
        // CallServiceV2 : call external
        // CallLogAspect : aop=void hello.aop.internalcall.CallServiceV2.internal()
        // CallServiceV2 : call internal
    }
}