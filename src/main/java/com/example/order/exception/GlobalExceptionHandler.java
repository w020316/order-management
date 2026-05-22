package com.example.order.exception;

import com.example.order.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        logger.warn("业务异常: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        logger.warn("参数校验异常: {}", message);
        return Result.error(400, message);
    }

    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e) {
        String message = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        logger.warn("参数绑定异常: {}", message);
        return Result.error(400, message);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleMissingParamException(MissingServletRequestParameterException e) {
        logger.warn("缺少请求参数: {}", e.getParameterName());
        return Result.error(400, "缺少请求参数: " + e.getParameterName());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<?> handleNotFoundException(NoHandlerFoundException e) {
        return Result.error(404, "请求的资源不存在");
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        logger.error("系统异常", e);
        return Result.error("系统内部错误");
    }
}
