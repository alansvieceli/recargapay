package br.com.alan.walletServiceAssignment.utils;

import br.com.alan.walletServiceAssignment.annotations.OnTopic;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import org.apache.logging.log4j.util.InternalException;

import java.util.UUID;

public class WalletUtils {

    public static String generateCodeTransaction() {
        return "w" + UUID.randomUUID().toString().replace("-", "").substring(0, 15);
    }

    public static String getTopicName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(OnTopic.class)) {
            OnTopic annotation = clazz.getAnnotation(OnTopic.class);
            return annotation.name();
        }
        throw new InternalException("topic name not defined");
    }
}
