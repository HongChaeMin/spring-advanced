package hello.advanced.trace.threadlocal;

import hello.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static hello.advanced.trace.threadlocal.code.FieldService.sleep;

@Slf4j
public class FieldServiceTest {

    private final FieldService fieldService = new FieldService();

    @Test
    void field() {
        log.info("main start");

        Runnable userA = () -> {
            fieldService.logic("userA");
        };
        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        sleep(100); // 동시성 문제 발생 O
        threadB.start();

        // 실행 결과
        // 저장 name : userA -> nameStore : null
        // 저장 name : userB -> nameStore : userA
        // 조회 nameStore : userB
        // 조회 nameStore : userB

        sleep(3000); // 메인 쓰레드 종료 대기
        log.info("main exit");
    }

    /* 동시성 발생 X 코드
    threadA.start();
    sleep(2000); // 동시성 문제 발생 X
    threadB.start();

    sleep(3000); // 메인 쓰레드 종료 대기
    log.info("main exit");

    // 실행 결과
    // 저장 name : userA -> nameStore : null
    // 조회 nameStore : userA
    // 저장 name : userB -> nameStore : userA
    // 조회 nameStore : userB
    */

}
