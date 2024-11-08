package ru.clevertec.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* ru.clevertec.api.controller..*(..))")
    public Object logControllerAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint, "Controller");
    }

    @Around("execution(* ru.clevertec.core.service..*(..))")
    public Object logServiceAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint, "Service");
    }

    @Around("execution(* ru.clevertec.core.repository..*(..))")
    public Object logRepositoryAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint, "Repository");
    }

    private Object logMethodExecution(ProceedingJoinPoint joinPoint, String layer) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String methodName = method.getName();
        String argsInfo = getArgumentsInfo(method.getParameters(), joinPoint.getArgs());

        log.info("Entering {} method: {} with arguments: {}", layer, methodName, argsInfo);

        Object result = joinPoint.proceed();
        log.info("Exiting {} method: {} with result: {}", layer, methodName, result);

        return result;
    }

    private String getArgumentsInfo(Parameter[] parameters, Object[] args) {
        return IntStream.range(0, parameters.length)
                .mapToObj(i -> parameters[i].getName() + ": " + args[i])
                .collect(Collectors.joining(", "));
    }
}
