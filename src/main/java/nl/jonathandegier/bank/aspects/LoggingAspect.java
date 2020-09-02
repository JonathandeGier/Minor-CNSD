package nl.jonathandegier.bank.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(public nl.jonathandegier.bank.* *(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String packageName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        long starttime = System.nanoTime();
        Object o = joinPoint.proceed();
        long endtime = System.nanoTime();

        System.out.println("Execution time of " + packageName +"."+ methodName + ": " + (endtime - starttime) + " ns");

        return o;
    }
}
