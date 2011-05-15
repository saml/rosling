package rosling.util;

import org.omg.CORBA.OBJECT_NOT_EXIST;
import rosling.annotation.JcrNode;
import rosling.annotation.JcrProp;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author saml
 */
public class SlingAdaptableUtil {
    private static String getSetterName(String fieldName) {
        if (fieldName.isEmpty()) {
            return fieldName;
        }
        if (fieldName.length() == 1) {
            return "set" + fieldName.toUpperCase();
        }
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private static Method getSetter(Class<?> clazz, String fieldName, Class<?> fieldType) throws NoSuchMethodException {
        return clazz.getDeclaredMethod(getSetterName(fieldName), fieldType);
    }

    private static Object getRawValue(Node node, String propName, Class<?> type) {
        try {
            final Property property = node.getProperty(propName);
            final Object value = JcrUtil.getRawValue(property, type);
            return value;
        } catch (RepositoryException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
        return null;
    }

    public static <T> T convert(Node node, Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, RepositoryException {
        if (null == clazz.getAnnotation(JcrNode.class)) {
            return null;
        }

        final T model = clazz.newInstance();

        for (final Field field : clazz.getDeclaredFields()) {
            final Class<?> type = field.getType();
            final String name = field.getName();
            final JcrProp prop = field.getAnnotation(JcrProp.class);
            final JcrNode child = field.getAnnotation(JcrNode.class);
            if (prop != null) {
                final Object value = getRawValue(node, name, type);
                final Method setter = getSetter(clazz, name, type);
                setter.invoke(model, value);
            } else if (child != null) {
                final Object childModel = convert(node.getNode(name), type);
                final Method setter = getSetter(clazz, name, type);
                setter.invoke(model, childModel);
            }
        }

        return model;
    }
}
