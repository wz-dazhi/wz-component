package com.wz.common.util;

import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.PrimitiveSink;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BloomFilterUtilTest {

    @Test
    public void test() {
        BloomFilterUtil<String> bfu = new BloomFilterUtil<>();
        bfu.add("11111");
        bfu.add("22222");
        System.out.println(bfu.contains("11111"));
        System.out.println(bfu.contains("22222"));

        assertTrue(bfu.contains("11111"));
        assertTrue(bfu.contains("22222"));

        BloomFilterUtil<Integer> bfu2 = new BloomFilterUtil<>(Funnels.integerFunnel());
        bfu2.add(1);
        bfu2.add(2);
        assertTrue(bfu2.contains(1));
        assertTrue(bfu2.contains(2));
        assertFalse(bfu2.contains(3));
    }

    @Test
    public void testPerson() {
        BloomFilterUtil<Person> bfu = new BloomFilterUtil<>(PersonFunnel.INSTANCE);
        bfu.add(new Person("张", "三", 20));
        bfu.add(new Person("张", "三", 21));
        bfu.add(new Person("李", "四", 22));

        assertTrue(bfu.contains(new Person("张", "三", 20)));
        assertTrue(bfu.contains(new Person("张", "三", 21)));
        assertTrue(bfu.contains(new Person("李", "四", 22)));
        assertFalse(bfu.contains(new Person("李", "三", 22)));
    }

    public enum PersonFunnel implements Funnel<Person> {
        INSTANCE;

        public void funnel(Person person, PrimitiveSink into) {
            into.putUnencodedChars(person.getFirstName())
                    .putUnencodedChars(person.getLastName())
                    .putInt(person.getAge());
        }
    }

    private static class Person {
        private String firstName;
        private String lastName;
        private int age;

        public Person() {
        }

        public Person(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}