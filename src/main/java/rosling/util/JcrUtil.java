package rosling.util;

import javax.jcr.Binary;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author saml
 */
public class JcrUtil {
    private static Object getRawValue(Value value, Class<?> returnType) throws RepositoryException {
        if (Boolean.class.isAssignableFrom(returnType)) {
            return value.getBoolean();
        }
        if (String.class.isAssignableFrom(returnType)) {
            return value.getString();
        }
        if (Long.class.isAssignableFrom(returnType)) {
            return value.getLong();
        }
        if (Double.class.isAssignableFrom(returnType)) {
            return value.getDouble();
        }
        if (BigDecimal.class.isAssignableFrom(returnType)) {
            return value.getDecimal();
        }
        if (Binary.class.isAssignableFrom(returnType)) {
            return value.getBinary();
        }
        if (Calendar.class.isAssignableFrom(returnType)) {
            return value.getDate();
        }
        return null;
    }

    public static Object getRawValue(Property property, Class<?> returnType) throws RepositoryException, IllegalAccessException, InstantiationException {
        if (property.isMultiple()) {
            final Value[] values = property.getValues();
            final Object[] arr = new Object[values.length];
            for (int i = 0; i < values.length; i++) {
                arr[i] = getRawValue(values[i], returnType);
            }
            return arr;
        }

        final Value value = property.getValue();
        return getRawValue(value, returnType);
    }
}
