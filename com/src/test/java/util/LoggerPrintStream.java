package util;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * PrintStream for directing the output to another PrintStream and a OutputStream. Can be used for printing System.err
 * to screen and to a log file. Writes timestamps for each printed line.
 *
 * @author Esko Luontola
 */
public class LoggerPrintStream extends PrintStream {
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private boolean lineStart = true;

    private PrintStream screen;

    /**
     * Creates a timestamped print stream directed to one output.
     *
     * @param out
     *            an OutputStream to direct all output with timestamps.
     */
    public LoggerPrintStream(OutputStream out) {
        this(out, null, null);
    }

    /**
     * Creates a timestamped print stream directed to two outputs.
     *
     * @param out
     *            an OutputStream to direct all output with timestamps.
     * @param screen
     *            a PrintStream to direct all output with timestamps. Will be ignored if null.
     */
    public LoggerPrintStream(OutputStream out, PrintStream screen) {
        this(out, screen, null);
    }

    /**
     * Creates a timestamped print stream directed to two outputs with a startup message.
     *
     * @param out
     *            an OutputStream to direct all output with timestamps.
     * @param screen
     *            a PrintStream to direct all output with timestamps. Will be ignored if null.
     * @param message
     *            a message to be printed at the creaton of this print stream. This will not be timestamped. Will be ignored
     *            if null.
     */
    public LoggerPrintStream(OutputStream out, PrintStream screen, String message) {
        super(out);
        this.screen = screen;
        if (message != null) {
            if (screen != null) {
                screen.print(message);
                screen.println();
            }
            super.print(message);
            super.println();
        }
    }

    private void timestamp() {
        if (lineStart) {
            String timestamp = dateFormat.format(new Date()) + "        ";
            if (screen != null) {
                screen.print(timestamp);
            }
            super.print(timestamp);
        }
        lineStart = false;
    }

    @Override
    public void print(boolean b) {
        timestamp();
        if (screen != null) {
            screen.print(b);
        }
        super.print(b);
    }

    @Override
    public void print(char c) {
        timestamp();
        if (screen != null) {
            screen.print(c);
        }
        super.print(c);
    }

    @Override
    public void print(int i) {
        timestamp();
        if (screen != null) {
            screen.print(i);
        }
        super.print(i);
    }

    @Override
    public void print(long l) {
        timestamp();
        if (screen != null) {
            screen.print(l);
        }
        super.print(l);
    }

    @Override
    public void print(float f) {
        timestamp();
        if (screen != null) {
            screen.print(f);
        }
        super.print(f);
    }

    @Override
    public void print(double d) {
        timestamp();
        if (screen != null) {
            screen.print(d);
        }
        super.print(d);
    }

    @Override
    public void print(char s[]) {
        timestamp();
        if (screen != null) {
            screen.print(s);
        }
        super.print(s);
    }

    @Override
    public void print(String s) {
        timestamp();
        if (screen != null) {
            screen.print(s);
        }
        super.print(s);
    }

    @Override
    public void print(Object obj) {
        timestamp();
        if (screen != null) {
            screen.print(obj);
        }
        super.print(obj);
    }

    @Override
    public void println() {
        if (screen != null) {
            screen.println();
        }
        super.println();
        lineStart = true;
    }

    @Override
    public void println(boolean x) {
        if (screen != null) {
            screen.println(x);
        }
        super.println(x);
        lineStart = true;
    }

    @Override
    public void println(char x) {
        if (screen != null) {
            screen.println(x);
        }
        super.println(x);
        lineStart = true;
    }

    @Override
    public void println(int x) {
        if (screen != null) {
            screen.println(x);
        }
        super.println(x);
        lineStart = true;
    }

    @Override
    public void println(long x) {
        if (screen != null) {
            screen.println(x);
        }
        super.println(x);
        lineStart = true;
    }

    @Override
    public void println(float x) {
        if (screen != null) {
            screen.println(x);
        }
        super.println(x);
        lineStart = true;
    }

    @Override
    public void println(double x) {
        if (screen != null) {
            screen.println(x);
        }
        super.println(x);
        lineStart = true;
    }

    @Override
    public void println(char x[]) {
        if (screen != null) {
            screen.println(x);
        }
        super.println(x);
        lineStart = true;
    }

    @Override
    public void println(String x) {
        if (screen != null) {
            screen.println(x);
        }
        super.println(x);
        lineStart = true;
    }

    @Override
    public void println(Object x) {
        if (screen != null) {
            screen.println(x);
        }
        super.println(x);
        lineStart = true;
    }
}
