package fr.todooz.aop;

import java.util.Arrays;

import javax.inject.Inject;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import fr.todooz.annotation.FlushCache;
import fr.todooz.annotation.UseCache;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

@Aspect
@Component
public class CacheAspect {
    @Inject
    private Ehcache postsCache;

    @Around("@annotation(useCache)")
    public Object cache(ProceedingJoinPoint pjp, UseCache useCache) throws Throwable {
        System.out.println("Going through cache for " + pjp.getSignature());

        CacheKey key = new CacheKey(pjp);

        Element element = postsCache.get(key);

        if (element != null) {
            System.out.println("found result in cache");

            return element.getValue();
        }

        Object result = pjp.proceed();

        postsCache.put(new Element(key, result));

        return result;
    }

    @Around("@annotation(flushCache)")
    public Object flush(ProceedingJoinPoint pjp, FlushCache flushCache) throws Throwable {
        postsCache.removeAll();

        return pjp.proceed();
    }

    private class CacheKey {
        private String signature;

        private Object[] args;

        public CacheKey(ProceedingJoinPoint pjp) {
            signature = pjp.getSignature().toString();
            args = pjp.getArgs();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CacheKey cacheKey = (CacheKey) o;

            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            if (!Arrays.equals(args, cacheKey.args)) return false;
            if (signature != null ? !signature.equals(cacheKey.signature) : cacheKey.signature != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = signature != null ? signature.hashCode() : 0;
            result = 31 * result + (args != null ? Arrays.hashCode(args) : 0);
            return result;
        }
    }
}
