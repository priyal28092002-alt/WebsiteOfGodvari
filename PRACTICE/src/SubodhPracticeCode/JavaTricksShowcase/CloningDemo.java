package com.example.JavaTricksShowcase;

/**
 * Shallow vs deep copy and Cloneable pitfalls.
 */
public class CloningDemo {
    static class Address {
        String city;
        Address(String city) { this.city = city; }
    }

    static class PersonShallow implements Cloneable {
        String name;
        Address address;
        PersonShallow(String name, Address address) { this.name = name; this.address = address; }
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone(); // shallow copy
        }
    }

    static class PersonDeep implements Cloneable {
        String name;
        Address address;
        PersonDeep(String name, Address address) { this.name = name; this.address = address; }
        @Override
        protected Object clone() throws CloneNotSupportedException {
            PersonDeep cloned = (PersonDeep) super.clone();
            cloned.address = new Address(this.address.city); // deep copy
            return cloned;
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        example1_shallowCopy();
        example2_deepCopy();
        example3_copyConstructor();
        example4_cloneableContract();
        challenge();
    }

    static void example1_shallowCopy() throws CloneNotSupportedException {
        System.out.println("\n[CloningDemo - Example 1] Shallow copy problem");
        PersonShallow p1 = new PersonShallow("Alice", new Address("NYC"));
        PersonShallow p2 = (PersonShallow) p1.clone();
        p2.address.city = "LA";
        System.out.println("p1.address.city: " + p1.address.city);
        System.out.println("Prediction: LA ✅  Actual: " + p1.address.city + " ✅");
        System.out.println("Key Takeaway: Shallow copy shares mutable objects; changes affect both.");
    }

    static void example2_deepCopy() throws CloneNotSupportedException {
        System.out.println("\n[CloningDemo - Example 2] Deep copy solution");
        PersonDeep p1 = new PersonDeep("Bob", new Address("NYC"));
        PersonDeep p2 = (PersonDeep) p1.clone();
        p2.address.city = "LA";
        System.out.println("p1.address.city: " + p1.address.city);
        System.out.println("Prediction: NYC ✅  Actual: " + p1.address.city + " ✅");
        System.out.println("Key Takeaway: Deep copy creates independent copies of mutable objects.");
    }

    static class PersonCopy {
        String name;
        Address address;
        PersonCopy(String name, Address address) { this.name = name; this.address = address; }
        PersonCopy(PersonCopy other) {
            this.name = other.name;
            this.address = new Address(other.address.city);
        }
    }

    static void example3_copyConstructor() {
        System.out.println("\n[CloningDemo - Example 3] Copy constructor alternative");
        PersonCopy p1 = new PersonCopy("Charlie", new Address("NYC"));
        PersonCopy p2 = new PersonCopy(p1);
        p2.address.city = "LA";
        System.out.println("p1.address.city: " + p1.address.city);
        System.out.println("Prediction: NYC ✅  Actual: " + p1.address.city + " ✅");
        System.out.println("Key Takeaway: Copy constructors are clearer and safer than clone().");
    }

    static void example4_cloneableContract() {
        System.out.println("\n[CloningDemo - Example 4] Cloneable is a marker interface");
        try {
            Object obj = new Object();
            // obj.clone(); // protected, can't call
            System.out.println("Object doesn't implement Cloneable");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        System.out.println("Prediction: Can't clone Object ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Cloneable is marker; clone() is in Object but protected.");
    }

    static void challenge() {
        System.out.println("\n[CloningDemo - Challenge] What if you don't implement Cloneable?");
        System.out.println("Hidden");
        // class X { protected Object clone() throws CloneNotSupportedException { return super.clone(); } }
        // new X().clone(); // throws CloneNotSupportedException
        System.out.println("Interview Hint: Must implement Cloneable or get exception.");
    }
}
