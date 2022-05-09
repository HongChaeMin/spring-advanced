package hello.advanced.trace;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TraceId {

    // 로그 추적기는 트랜잭션ID와 깊이를 표현하는 방법이 필요하다.
    // 여기서는 트랜잭션ID와 깊이를 표현하는 level 묶어서 TraceId 라는 개념을 만들었다.
    // TraceId 는 단순히 id (트랜잭션ID)와 level 정보를 함께 가지고 있다.

    private final String id;
    private final int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    public TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        // ab99e16f-3cde-4d24-8241-256108c203a2 생성된 UUID
        // ab99e16f 앞 8자리만 사용
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }

    public TraceId createPreviousId() {
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }

}
