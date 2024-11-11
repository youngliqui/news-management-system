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


/**
 * Аспект для логирования доступа к методам контроллеров, сервисов и репозиториев.
 * <p>
 * Этот класс использует AspectJ для перехвата вызовов методов в указанных пакетах
 * и логирует информацию о входе и выходе из методов, а также их аргументы и результаты.
 * </p>
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    /**
     * Перехватывает вызовы методов в контроллерах и логирует информацию о доступе.
     *
     * @param joinPoint точка соединения, представляющая вызов метода
     * @return результат выполнения метода
     * @throws Throwable если возникает ошибка при выполнении метода
     */
    @Around("execution(* ru.clevertec.api.controller..*(..))")
    public Object logControllerAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint, "Controller");
    }

    /**
     * Перехватывает вызовы методов в сервисах и логирует информацию о доступе.
     *
     * @param joinPoint точка соединения, представляющая вызов метода
     * @return результат выполнения метода
     * @throws Throwable если возникает ошибка при выполнении метода
     */
    @Around("execution(* ru.clevertec.core.service..*(..))")
    public Object logServiceAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint, "Service");
    }

    /**
     * Перехватывает вызовы методов в репозиториях и логирует информацию о доступе.
     *
     * @param joinPoint точка соединения, представляющая вызов метода
     * @return результат выполнения метода
     * @throws Throwable если возникает ошибка при выполнении метода
     */
    @Around("execution(* ru.clevertec.core.repository..*(..))")
    public Object logRepositoryAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint, "Repository");
    }

    /**
     * Логирует выполнение метода, включая его имя, аргументы и результат.
     *
     * @param joinPoint точка соединения, представляющая вызов метода
     * @param layer     название слоя (например, "Controller", "Service" или "Repository")
     * @return результат выполнения метода
     * @throws Throwable если возникает ошибка при выполнении метода
     */
    private Object logMethodExecution(ProceedingJoinPoint joinPoint, String layer) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String methodName = method.getName();
        String argsInfo = getArgumentsInfo(method.getParameters(), joinPoint.getArgs());

        log.info("Entering {} method: {} with arguments: {}", layer, methodName, argsInfo);

        Object result = joinPoint.proceed();
        log.info("Exiting {} method: {} with result: {}", layer, methodName, result);

        return result;
    }

    /**
     * Получает строку с информацией об аргументах метода.
     *
     * @param parameters массив параметров метода
     * @param args       массив аргументов, переданных методу
     * @return строка с информацией об аргументах в формате "имя: значение"
     */
    private String getArgumentsInfo(Parameter[] parameters, Object[] args) {
        return IntStream.range(0, parameters.length)
                .mapToObj(i -> parameters[i].getName() + ": " + args[i])
                .collect(Collectors.joining(", "));
    }
}
