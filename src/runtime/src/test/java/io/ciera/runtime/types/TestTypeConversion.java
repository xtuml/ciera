package io.ciera.runtime.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.ciera.runtime.exceptions.TypeConversionException;

public class TestTypeConversion {

    @Test
    public void intToReal() {
        // basic test
        int input = 18;
        double output = ModelType.castTo(Double.class, input);
        assertEquals(18.0d, output);

        // negative value
        input = -37;
        output = ModelType.castTo(Double.class, input);
        assertEquals(-37.0d, output);

    }

    @Test
    public void realToInt() {
        // basic test
        double input = 7.0d;
        int output = ModelType.castTo(Integer.class, input);
        assertEquals(7, output);

        // lossy test
        input = 4.5d;
        output = ModelType.castTo(Integer.class, input);
        assertEquals(4, output);
    }

    @Test
    public void intToTimeStamp() {
        int input = Integer.MAX_VALUE;
        TimeStamp output = ModelType.castTo(TimeStamp.class, input);
        assertEquals(Integer.MAX_VALUE, output.getValue());
    }

    @Test
    public void timeStampToReal() {
        TimeStamp input = new TimeStamp(987654321000000l);
        double output = input.castTo(Double.class);
        assertEquals(987654321000000.0d, output);
    }

    @Test
    public void durationToDate() {
        Duration input = new Duration(5555l);
        Date output = input.castTo(Date.class);
        assertEquals(5555l, output.getValue());
    }

    @Test
    public void dateToDuration() {
        Date input = new Date(123456789l);
        Duration output = input.castTo(Duration.class);
        assertEquals(123456789l, output.getValue());
    }

    @Test
    public void dateToDevice() {
        Date input = new Date(123456789l);
        Exception e = assertThrows(TypeConversionException.class, () -> {
            input.castTo(Device.class);
        });
        assertEquals("Could not cast type 'io.ciera.runtime.types.Date' to 'io.ciera.runtime.types.Device'",
                e.getMessage());
    }

}
