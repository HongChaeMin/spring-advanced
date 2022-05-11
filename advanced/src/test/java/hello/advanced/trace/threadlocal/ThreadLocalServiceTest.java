package hello.advanced.trace.threadlocal;

import hello.advanced.trace.threadlocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static hello.advanced.trace.threadlocal.code.FieldService.sleep;

@Slf4j
public class ThreadLocalServiceTest {

    private final ThreadLocalService threadLocalService = new ThreadLocalService();

    @Test
    void field() {
        log.info("main start");

        Runnable userA = () -> {
            threadLocalService.logic("userA");
        };
        Runnable userB = () -> {
            threadLocalService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        sleep(100);
        threadB.start();

        sleep(3000);
        log.info("main exit");
    }

    // 실행 결과
    // 저장 name : userA -> nameStore : java.lang.ThreadLocal@3e519677
    // 저장 name : userB -> nameStore : java.lang.ThreadLocal@3e519677
    // 조회 nameStore : userA
    // 조회 nameStore : userB

}
