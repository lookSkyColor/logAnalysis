package com.example.demo.aop;


import com.alibaba.fastjson.JSON;
import com.example.demo.enums.ResultCodeEnum;
import com.example.demo.util.ResultVO;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * 拦截器：记录用户操作日志，检查用户是否登录……
 *
 * @author weylan
 */
@Aspect
@Component
public class ControllerInterceptor {

    //@Autowired
   // RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);

    /**
     * 定义拦截规则：拦截com.huidong.qzh.standard包下面的所有类中，有@RequestMapping注解的方法。
     */
    @Pointcut("execution(* com.example.demo.controller.DemoController.*(..))" +
            " && ( @annotation(org.springframework.web.bind.annotation.RequestMapping )" +
            " || @annotation(org.springframework.web.bind.annotation.GetMapping) " +
            " || @annotation(org.springframework.web.bind.annotation.PostMapping ))")
    public void controllerMethodPointcut() {
    }

    /**
     * 拦截器具体实现
     *
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    @Around("controllerMethodPointcut()") //指定拦截器规则；也可以直接把“execution(* com.xjj.........)”写进这里
    public Object Interceptor(ProceedingJoinPoint pjp) {
        logger.info("start ControllerInterceptor,controller:{},method:{},params:{},value:{}", pjp.getSignature().getDeclaringTypeName(),((MethodSignature) pjp.getSignature()).getMethod().getName(),((MethodSignature) pjp.getSignature()).getParameterNames(), JSON.toJSONString(pjp.getArgs()));

        Method method =((MethodSignature) pjp.getSignature()).getMethod();
        HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Object result = null;

        if (isLoginRequired(method)) {
            ResultVO loginResult = isLogin(request);
            if (loginResult.getCode()!=0) {
                result = loginResult;
            }
        }
        if (result == null) {
            try {
                result = pjp.proceed();
            } catch (Throwable throwable) {
                logger.error("exception ControllerInterceptor,controller:{},method:{},ex:{}", pjp.getSignature().getDeclaringTypeName(),((MethodSignature) pjp.getSignature()).getMethod().getName(), throwable);
                result = new ResultVO(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), ResultCodeEnum.INTERNAL_SERVER_ERROR.getMsg(),throwable.getMessage());
            }
        }
        logger.info("end ControllerInterceptor,controller:{},method:{},result:{}", pjp.getSignature().getDeclaringTypeName(),((MethodSignature) pjp.getSignature()).getMethod().getName(), JSON.toJSONString(result));

        return result;
    }

    /**
     * 判断一个方法是否需要登录
     *
     * @param method 方法
     * @return
     */
    private boolean isLoginRequired(Method method) {
        boolean result = false;
        if (method.isAnnotationPresent(LoginRequired.class)) {
            result = method.getAnnotation(LoginRequired.class).loginRequired();
        }
        return result;
    }

    //判断是否已经登录
    private ResultVO isLogin(HttpServletRequest request) {
        String token = request.getHeader("QZH_TOKEN");
        if (StringUtils.isBlank(token)) {
                return new ResultVO.ResultVoBuilder<String>().code(2).msg("token失效，请重新登录获取").result("").builder();

        }

       // ResponseEntity<ResultVO> responseEntity = restTemplate.getForEntity("http://QZH-SSO/token/{1}", ResultVO.class, token);
        return  new ResultVO.ResultVoBuilder<String>().code(0).msg("token失效，请重新登录获取").result("").builder();
    }
}
