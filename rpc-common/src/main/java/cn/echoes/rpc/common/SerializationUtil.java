package cn.echoes.rpc.common;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * -------------------------------------
 * 序列化与反序列化
 * -------------------------------------
 * Created by liutao on 2017/3/29 23:51.
 */
public class SerializationUtil {
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();
    private static Objenesis objenesis = new ObjenesisStd(true);

    private SerializationUtil() {
    }

    /**
     * 获取类定义的schema
     *
     * @param cls
     * @param <T>
     * @return
     */
    private static <T> Schema<T> getScheam(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            if (schema != null) {
                cachedSchema.put(cls, schema);
            }
        }
        return schema;

    }

    /**
     * 将对象序列化
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> byte[] serialize(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getScheam(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);

        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    /**
     * 将对象反序列化
     * @param bytes
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T deSerialize(byte[] bytes, Class<T> cls) {
        try {
            T message = objenesis.newInstance(cls);
            Schema<T> schema = getScheam(cls);
            ProtostuffIOUtil.mergeFrom(bytes, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(),e);
        }
    }
}

