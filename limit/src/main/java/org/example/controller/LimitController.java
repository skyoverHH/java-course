import lombok.RequiredArgsConstructor;
import org.example.dto.LimitDto;
import org.example.dto.LimitOperationRequestDto;
import org.example.service.LimitService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/limits/v1")
@RequiredArgsConstructor
public class LimitController {

    private final LimitService limitService;

    public LimitController(LimitService limitService) {
        this.limitService = limitService;
    }

    @GetMapping("/user/{userId}")
    public LimitDto getUserLimit(@PathVariable Long userId) {
        return limitService.getLimitByUserId(userId);
    }

    @PostMapping("/decrease")
    public LimitDto decreaseLimit(
            @RequestBody LimitOperationRequestDto request
    ) {
        return limitService.decreaseLimit(request);
    }

    @PostMapping("/revert/{operationId}")
    public LimitDto revertLimit(
            @PathVariable UUID operationId
    ) {
        return limitService.revertLimit(operationId);
    }
}