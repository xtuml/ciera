package io.ciera.runtime.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import io.ciera.runtime.exceptions.DeserializationException;

public class TestTypeSerialization {
    
    private static class MyString {
        private final String str;
        public MyString(String str) {
            this.str = str;
        }
        @Override
        public String toString() {
            return "str: '" + str + "'";
        }
    }
    
    private static class MyInt {
        private final int num;
        public MyInt(int num) {
            this.num = num;
        }
        public MyString toMyString() {
            return new MyString(Integer.toString(num));
        }
        
        public Stream<MyString> toMyString2() {
            return Stream.generate(() -> new MyString("poop")).limit(num);
        }
    }
    
    @Test
    public void testStreams() {
        Stream<MyString> stream = Stream.of(new MyInt(1), new MyInt(2), new MyInt(3), new MyInt(4)).map(MyInt::toMyString);
        stream.forEach(s -> {
            System.out.println("LEVI " + s);
        });
        Stream<MyString> stream2 = Stream.of(new MyInt(1), new MyInt(2), new MyInt(3), new MyInt(4)).flatMap(MyInt::toMyString2);
        stream2.forEach(s -> {
            System.out.println("LEVI " + s);
        });
    }

    @Test
    public void testTimeStamp() {
        TimeStamp t1 = new TimeStamp(1234l);
        String s1 = t1.toString();
        assertEquals("1234", s1);

        String s2 = "5678";
        TimeStamp t2 = TimeStamp.fromString(s2);
        assertEquals(5678l, t2.getValue());
    }

    @Test
    public void testDuration() {
        Duration d1 = new Duration(123456789000000l);
        String s1 = d1.toString();
        assertEquals("PT34H17M36.789S", s1);

        String s2 = "PT274H20M54.321S";
        Duration d2 = Duration.fromString(s2);
        assertEquals(987654321000000l, d2.getValue());
    }

    @Test
    public void testDate() {
        Date d1 = new Date(1637624161000000000l);
        String s1 = d1.toString();
        assertEquals("2021-11-22T23:36:01Z", s1);

        String s2 = "2021-11-21T23:36:01Z";
        Date d2 = Date.fromString(s2);
        assertEquals(1637537761000000000l, d2.getValue());

        String s3 = "2021-11-21T18:36:01-05:00";
        Date d3 = Date.fromString(s3);
        assertEquals(1637537761000000000l, d3.getValue());
    }

    @Test
    public void testDevice() {
        Device c = Device.console();
        String s1 = c.toString();
        assertEquals("CONSOLE", s1);

        Exception e = assertThrows(DeserializationException.class, () -> {
            Device.fromString(s1);
        });
        assertEquals("'Device' type is not serializable", e.getMessage());
    }

    @Test
    public void testUniqueId() {
        UniqueId id1 = new UniqueId(UUID.fromString("a3080669-a8d4-4588-986c-7d7a7c676005"));
        String s1 = id1.toString();
        assertEquals("a3080669-a8d4-4588-986c-7d7a7c676005", s1);

        String s2 = "7fdc406f-2939-49c0-8fab-99de27b70193";
        UniqueId id2 = UniqueId.fromString(s2);
        assertEquals(new UniqueId(UUID.fromString("7fdc406f-2939-49c0-8fab-99de27b70193")), id2);
    }

}
