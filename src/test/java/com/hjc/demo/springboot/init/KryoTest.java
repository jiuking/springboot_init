package com.hjc.demo.springboot.init;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import com.esotericsoftware.kryo.serializers.BlowfishSerializer;
import com.hjc.demo.springboot.init.util.KryoUtil;
import lombok.Data;

import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.NoSuchAlgorithmException;

public class KryoTest {

    public static byte[] toByteArray (Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    public static void main1(String[] args) {
        Person1 p1 = new Person1();
        p1.setName("张三");
        p1.setAge("32");
        System.out.println(toByteArray("asdfasdfasdf").length);
        byte[] asdf = KryoUtil.obj2ByteArray("asdfasdfasdf");
        System.out.println(asdf.length);
//        Person1 s = KryoUtil.byteArray2Obj(asdf,Person1.class);
//        System.out.println(s);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        Kryo kryo = new Kryo();
//        byte[] key = KeyGenerator.getInstance("Blowfish").generateKey().getEncoded();
//        kryo.register(Person1.class,new BlowfishSerializer(new BeanSerializer(kryo,Person1.class),key));
        kryo.setReferences(false);
        Person1 person1 = new Person1();
        person1.setAge("23");
        person1.setName("张三");

        Person1 person2 = new Person1();
        person2.setAge("24");
        person2.setName("张3");

        Output output = new Output(new FileOutputStream("E:\\test\\test.bin"));
        kryo.writeObject(output, person1);
        kryo.writeObject(output, person2);
        output.flush();
        output.flush();
        output.close();

        Input input = new Input(new FileInputStream("E:\\test\\test.bin"));
        System.out.println(input.available()>0);
        Person1 p = kryo.readObject(input, Person1.class);
        System.out.println(input.available());
        Person1 p2 = kryo.readObject(input, Person1.class);
        System.out.println(input.available());
        System.out.println(p);
        System.out.println(p2);
    }
}

@Data
class Person1 implements Serializable {
    private String name;
    private String age;
}
