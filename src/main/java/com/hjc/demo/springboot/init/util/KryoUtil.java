package com.hjc.demo.springboot.init.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KryoUtil {
    private final static String UTF_8 = "UTF-8";

    private static ThreadLocal<Kryo> threadLocal = new ThreadLocal<Kryo>(){
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.setReferences(false);
            return kryo;
        }
    };

    public static <T> byte[] obj2ByteArray(T obj) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Kryo kryo = getKryo();
        Output output = new Output(outputStream);
        kryo.writeObject(output, obj);
        output.flush();
        return outputStream.toByteArray();
    }

    public static <T> List<T> byteArray2Obj(byte[] bytes, Class<T> clazz) throws IOException {
        List<T> list = new ArrayList<>();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Kryo kryo = getKryo();
        Input input = new Input(inputStream);
        T obj;
        while (input.available() > 0) {
            obj = kryo.readObject(input, clazz);
            list.add(obj);
        }
        return list;
    }

    private static Kryo getKryo() {
        return threadLocal.get();
    }
}
