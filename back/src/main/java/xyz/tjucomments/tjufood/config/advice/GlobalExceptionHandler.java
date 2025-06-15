package xyz.tjucomments.tjufood.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.tjucomments.tjufood.dto.Result;

/**
 * 全局异常处理器
 * @RestControllerAdvice 注解表示这是一个处理 Controller 层异常的切面
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理JSR-303校验失败的异常
     * 当 DTO 前面用了 @Validated 或 @Valid，并且校验失败时，会抛出这个异常
     * @param e a MethodArgumentNotValidException
     * @return a Result object
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 返回400错误码
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 从异常中获取第一个校验失败的错误信息
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("参数校验失败: {}", message);
        return Result.fail(message);
    }

    /**
     * 统一处理所有未被捕获的 Exception
     * @param e 异常实例
     * @return 统一的错误响应
     */
    @ExceptionHandler(Exception.class) // 拦截所有类型的 Exception
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 默认返回500错误码
    public Result handleException(Exception e) {
        // 在服务器端打印详细的错误日志，方便排查问题
        log.error("系统出现未捕获异常", e);
        // 向前端返回一个通用的、不暴露系统内部细节的错误信息
        return Result.fail("服务器开小差了，请稍后再试~");
    }
}