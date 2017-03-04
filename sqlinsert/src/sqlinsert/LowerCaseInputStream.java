package sqlinsert;

import java.io.*;

public class LowerCaseInputStream extends FilterInputStream {

    protected LowerCaseInputStream(InputStream in) {
        super(in);
    }
    
    public int read() throws IOException {
        int c = super.read();
        if ( c >= 'A' && c <= 'Z' ) {
            c += ('a'-'A');
        }
        return c;
    }

    public int read(byte[] b) throws IOException {
        int num = super.read(b);
        for ( int i = 0; i < b.length; i++ ) {
            char c = (char)b[i];
            if ( c >= 'A' && c <= 'Z' ) {
                c += ('a'-'A');
            }
            b[i] = (byte)c;
        }
        return num;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int num = super.read(b, off, len);
        for ( int i = 0; i < b.length; i++ ) {
            char c = (char)b[i];
            if ( c >= 'A' && c <= 'Z' ) {
                c += ('a'-'A');
            }
            b[i] = (byte)c;
        }
        return num;
    }

}
