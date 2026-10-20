package com.example.JavaTricksShowcase;

import java.io.*;

/**
 * Serialization, transient keyword, and custom serialization.
 */
public class SerializationDemo {
    static class Person implements Serializable {
        private static final long serialVersionUID = 1L;
        String name;
        transient String password; // not serialized
        int age;
        Person(String name, String password, int age) {
            this.name = name; this.password = password; this.age = age;
        }
    }

    static class CustomSerialization implements Serializable {
        private static final long serialVersionUID = 1L;
        String data;
        transient String computed;
        CustomSerialization(String data) { this.data = data; this.computed = data.toUpperCase(); }
        private void writeObject(ObjectOutputStream oos) throws IOException {
            oos.defaultWriteObject();
            oos.writeObject(computed); // manually serialize transient field
        }
        private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
            ois.defaultReadObject();
            computed = (String) ois.readObject(); // manually deserialize
        }
    }

    public static void main(String[] args) throws Exception {
        example1_transientField();
        example2_serialVersionUID();
        example3_customSerialization();
        example4_staticFieldNotSerialized();
        challenge();
    }

    static void example1_transientField() throws Exception {
        System.out.println("\n[SerializationDemo - Example 1] transient keyword");
        Person p1 = new Person("Alice", "secret123", 30);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(p1);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        Person p2 = (Person) ois.readObject();
        System.out.println("name: " + p2.name + ", password: " + p2.password + ", age: " + p2.age);
        System.out.println("Prediction: password=null ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: transient fields are not serialized; restored as default values.");
    }

    static void example2_serialVersionUID() {
        System.out.println("\n[SerializationDemo - Example 2] serialVersionUID importance");
        System.out.println("serialVersionUID: " + Person.serialVersionUID);
        System.out.println("Prediction: 1 ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: serialVersionUID ensures version compatibility during deserialization.");
    }

    static void example3_customSerialization() throws Exception {
        System.out.println("\n[SerializationDemo - Example 3] Custom serialization methods");
        CustomSerialization obj1 = new CustomSerialization("hello");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj1);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        CustomSerialization obj2 = (CustomSerialization) ois.readObject();
        System.out.println("data: " + obj2.data + ", computed: " + obj2.computed);
        System.out.println("Prediction: hello, HELLO ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: writeObject/readObject allow custom serialization logic.");
    }

    static class WithStatic implements Serializable {
        private static final long serialVersionUID = 1L;
        static int count = 0;
        int id;
        WithStatic(int id) { this.id = id; count++; }
    }

    static void example4_staticFieldNotSerialized() throws Exception {
        System.out.println("\n[SerializationDemo - Example 4] Static fields not serialized");
        WithStatic.count = 100;
        WithStatic obj1 = new WithStatic(1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ObjectOutputStream(baos).writeObject(obj1);
        WithStatic.count = 0; // reset
        WithStatic obj2 = (WithStatic) new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray())).readObject();
        System.out.println("obj2.id: " + obj2.id + ", count: " + WithStatic.count);
        System.out.println("Prediction: id=1, count=0 ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Static fields belong to class, not instance; not serialized.");
    }

    static void challenge() {
        System.out.println("\n[SerializationDemo - Challenge] What if class doesn't implement Serializable?");
        System.out.println("Hidden");
        // class X {} new ObjectOutputStream(...).writeObject(new X()); // NotSerializableException
        System.out.println("Interview Hint: Must implement Serializable or get exception.");
    }
}
