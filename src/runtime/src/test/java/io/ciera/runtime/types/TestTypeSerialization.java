package io.ciera.runtime.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import io.ciera.runtime.exceptions.DeserializationException;

public class TestTypeSerialization {

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
    }

    @Test
    public void testDevice() {
        Device c = Device.console();
        String s1 = c.toString();
        assertEquals("CONSOLE", s1);

        Exception e = assertThrows(DeserializationException.class, () -> {
            Device.fromString(s1);
        });
        assertEquals("'Device' type is not serializable.", e.getMessage());
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