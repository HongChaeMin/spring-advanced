package hello.advanced.app.V5;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate template;

    public OrderControllerV5(OrderServiceV5 orderService, LogTrace trace) {
        this.orderService = orderService;
        this.template = new TraceTemplate(trace); // trace 의존관계 주입을 받으면서 필요한 TraceTemplate 템플릿을 생성한다
        // 참고로 TraceTemplate 를 처음부터 스프링 빈으로 등록하고 주입받아도 된다. 이 부분은 선택이다
    }

    @GetMapping("/v5/request")
    public String request(String itemId) {
        // 템플릿을 실행하면서 콜백을 전달
        // 여기서는 콜백으로 익명 내부 클래스를 사용
        return template.execute("OrderController.request()", () -> {
            orderService.orderItem(itemId);
            return "ok";
        });
    }
}
