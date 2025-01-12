package it.discovery.bpp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;

@RequiredArgsConstructor
public class CustomInitBeanPostProcessor implements BeanPostProcessor {

    private final ApplicationContext context;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        var clz = bean.getClass();
        Arrays.stream(ReflectionUtils.getAllDeclaredMethods(clz))
                .filter(m -> m.isAnnotationPresent(Init.class))
                .forEach(m -> {
                    m.setAccessible(true);
                    if (m.getParameters().length == 0) {
                        ReflectionUtils.invokeMethod(m, bean);
                    } else if (m.getParameters().length == 1 &&
                            m.getParameters()[0].getType().isAssignableFrom(context.getClass())) {
                        ReflectionUtils.invokeMethod(m, bean, context);
                    } else {
                        throw new IllegalStateException("Incorrect number of arguments or argument type not supported");
                    }
                });

        return bean;
    }
}
