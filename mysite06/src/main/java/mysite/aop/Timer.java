package mysite.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class Timer {
    @Around(value = "execution(* mysite.repository.*.*(..))")
    public Object measureElapsedTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        long elapsedTime = stopWatch.getTotalTimeMillis();
        System.out.println(
            "[Execution Time][" +
                proceedingJoinPoint.getSignature().getName() +
                "] Finished Execution in " +
                elapsedTime +
                "ms"
        );

        return result;
    }
}
