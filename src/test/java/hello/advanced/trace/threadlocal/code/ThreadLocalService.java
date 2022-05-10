package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

import static hello.advanced.trace.threadlocal.code.FieldService.sleep;

@Slf4j
public class ThreadLocalService {

    // ThreadLocal 사용법
    // 값 저장: ThreadLocal.set(xxx)
    // 값 조회: ThreadLocal.get()
    // 값 제거: ThreadLocal.remove()
    private final ThreadLocal<String> nameStore = new ThreadLocal<>();

    public void logic(String name) {
        log.info("저장 name : {} -> nameStore : {}", name, nameStore);
        nameStore.set(name);
        sleep(1000);
        log.info("조회 nameStore : {}", nameStore.get());
        nameStore.get();
    }

    // 주의
    // 해당 쓰레드가 쓰레드 로컬을 모두 사용하고 나면 ThreadLocal.remove() 를 호출해서 쓰레드 로컬에
    // 저장된 값을 제거해주어야 한다.

}
