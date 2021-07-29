package org.zerock.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

// Advice 객체 : 공통 기능을 수행하는 객체
@Aspect			// Advice 클래스 스프링에게 알림
@Log4j
@Component		// 스프링에게 주입받는 대상
public class logAdvice {	// 로깅 기능을 수행하는 Advice 객체
	@Before("execution(* org.zerock.service.SampleService*.*(..))")	// Before Advice
	public void logBefore() {
		log.info("==========================");
	}
	
	@Before("execution(* org.zerock.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1:" + str1);
		log.info("str2:" + str2);
	}
	@AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService*.*(..))",
			throwing="exception")
	public void logException(Exception exception) {
		log.info("Exception....!!!!");
		log.info("exception: " + exception);
	}
	@Around("execution(* org.zerock.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();		// 현재시간을 구한다.
		log.info("Target: " + pjp.getTarget());
		log.info("Param: " + Arrays.toString(pjp.getArgs()));
		// invoke method
		Object result = null;
		try {
			result = pjp.proceed();	// method 실행 결과를 응답 받음
		} catch (Throwable e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();	// 현재 시간을 구한다.
		log.info("TIME: " + (end - start));		// method의 실행시간을 구한다.
		return result;
	}
}
/*
 pointcut : Advice가 적용되는 joinPoint가 무엇인지를 나타내는 기준
 * org.zerock.service.SampleService*.*(..)
 * : 접근 제한자 (public, private, protected)
 org.zerock.service.SampleService* : SampleService.java, SampleServiceImpl.java
 .* : 모든 매소드
 (..) : 매개변수
 * */
