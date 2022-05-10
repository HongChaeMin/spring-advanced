package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder.get();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void syncTraceId() {
        TraceId traceId = traceIdHolder.get();
        traceIdHolder.set(traceId == null ? new TraceId() : traceId.createNextId());
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time = {}ms",
                    traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()),
                    status.getMessage(),
                    resultTimeMs
            );
        } else {
            log.info("[{}] {}{} time = {}ms ex = {}",
                    traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()),
                    status.getMessage(), resultTimeMs, e.toString()
            );
        }

        releaseTraceId();
    }
    // 이제 더 이상 TraceId 값을 추적하지 않아도 된다
    // 그래서 traceId.isFirstLevel() ( level==0 )인 경우
    // ThreadLocal.remove() 를 호출해서 쓰레드 로컬에 저장된 값을 제거해준다
    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId.isFirstLevel()) {
            traceIdHolder.remove(); // destroy
        } else {
            traceIdHolder.set(traceId.createPreviousId());
        }
    }

    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append( (i == level - 1) ? "|" + prefix : "| ");
        }
        return sb.toString();
    }
  
    // 실행 결과
    // [57eb2700] OrderController.request()
    // [57eb2700] |-->OrderService.request()
    // [57eb2700] | |-->OrderRepository.request()
    // [3093a481] | |<--OrderRepository.request() time = 1007ms
    // [3093a481] |<--OrderService.request() time = 1007ms
    // [3093a481] OrderController.request() time = 1007ms
    // [675b9897] OrderController.request()
    // [675b9897] |-->OrderService.request()
    // [675b9897] | |-->OrderRepository.request()
    // [8c0485d0] | |<--OrderRepository.request() time = 1015ms
    // [8c0485d0] |<--OrderService.request() time = 1015ms
    // [8c0485d0] OrderController.request() time = 1015ms
    // [32af1861] OrderController.request()
}
