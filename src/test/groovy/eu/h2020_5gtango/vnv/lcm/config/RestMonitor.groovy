package eu.h2020_5gtango.vnv.lcm.config

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class RestMonitor {

    List<JoinPoint> requests=[]

    @AfterReturning("""
@annotation(org.springframework.web.bind.annotation.GetMapping) 
|| @annotation(org.springframework.web.bind.annotation.PostMapping) 
|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)
""")
    void keepResult(JoinPoint joinPoint) {
        requests.push(joinPoint)
    }

}