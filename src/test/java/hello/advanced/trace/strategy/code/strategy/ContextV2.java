package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 전략을 파라미터로 전달 받는 방식
 **/
@Slf4j
public class ContextV2 {

    // Context 와 Strategy 를 '선 조립 후 실행'하는 방식이 아니라 Context 를 실행할 때 마다 전략을 인수로 전달

    // 클라이언트는 Context 를 실행하는 시점에 원하는 Strategy 를 전달할 수 있다
    // 따라서 이전 방식과 비교해서 원하는 전략을 더욱 유연하게 변경

    public void execute(Strategy strategy) {
        long startTime = System.currentTimeMillis();

        strategy.call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime : " + resultTime);
    }

}
