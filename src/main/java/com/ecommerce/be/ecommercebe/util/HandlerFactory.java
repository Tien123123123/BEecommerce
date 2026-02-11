package com.ecommerce.be.ecommercebe.util;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import com.ecommerce.be.ecommercebe.service.UserService;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HandlerFactory {
    private final List<Handler<?>> allHandlers;
    private final Map<Class<?>, Handler<?>> chainCache = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(HandlerFactory.class);

    @SuppressWarnings("unchecked")
    public <T extends BaseValidate> Handler<T> getChain(Class<T> dtoType) {
        return (Handler<T>) chainCache.computeIfAbsent(dtoType, key -> buildChain(dtoType));
    }

    private <T extends BaseValidate> Handler<T> buildChain(Class<T> dtoType) {
        List<Handler<?>> candidates = allHandlers.stream().filter(handler->{
            Type genericSuper = handler.getClass().getGenericSuperclass();
            if (genericSuper instanceof ParameterizedType pt) {
                Type[] types = pt.getActualTypeArguments();
                return types.length > 0 && dtoType.equals(types[0]);
            }
            return false;
        })
                .sorted(Comparator.comparingInt(h -> {
                    Order ann = h.getClass().getAnnotation(Order.class);
                    return ann != null ? ann.value() : Integer.MAX_VALUE;
                }))
                .collect(Collectors.toList());
        if (candidates.isEmpty()) {
            logger.warn("Cannot find any handlers for DTO type: {}", dtoType.getSimpleName());
            return null;
        }
        Handler<T> head = (Handler<T>) candidates.get(0);
        Handler<T> current = head;

        for (int i = 1; i < candidates.size(); i++) {
            current.setNext((Handler<T>) candidates.get(i));
            current = (Handler<T>) candidates.get(i);
        }

        logger.debug("Build chain for {} with {} handler", dtoType.getSimpleName(), candidates.size());
        return head;
    }
}
