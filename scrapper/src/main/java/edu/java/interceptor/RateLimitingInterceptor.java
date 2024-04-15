package edu.java.interceptor;

import edu.java.exception.TooManyRequestsException;
import edu.java.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RateLimitingInterceptor implements HandlerInterceptor {
    private final RateLimiterService rateLimiterService;

    public boolean preHandle(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull Object handler
    ) throws TooManyRequestsException {
        String clientIp = request.getRemoteAddr();

        if (!rateLimiterService.tryConsume(clientIp)) {
            throw new TooManyRequestsException();
        }

        return true;
    }
}
