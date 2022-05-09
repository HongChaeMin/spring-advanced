package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceStatus;

public interface LogTrace {

    // LogTrace 인터페이스에는 로그 추적기를 위한 최소한의 기능인 begin() , end() , exception() 를 정의

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);

}
