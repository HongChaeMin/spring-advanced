package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 필드에 전략을 보관하는 방식
 **/
@Slf4j
public class ContextV1 {

    // ContextV1 은 변하지 않는 로직을 가지고 있는 템플릿 역할을 하는 코드이다
    // 전략 패턴에서는 이것을 컨텍스트(문맥)이라 한다

    // 쉽게 이야기해서 컨텍스트(문맥)는 크게 변하지 않지만, 그 문맥 속에서 strategy 를 통해 일부 전략이 변경된다 생각


    /**
     * 전략 패턴의 핵심은 Context 는 Strategy 인터페이스에만 의존한다는 점이다
     * 덕분에 Strategy 의 구현체를 변경하거나 새로 만들어도 Context 코드에는 영향을 주지 않는다.
    **/

    // 스프링에서 의존관계 주입에서 사용하는 방식이 바로 전략 패턴 -> 헐 미쳤다

    private final Strategy strategy;

    // 변하는 부분인 Strategy 의 구현체를 주입
    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();

        strategy.call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime : " + resultTime);
    }

}
