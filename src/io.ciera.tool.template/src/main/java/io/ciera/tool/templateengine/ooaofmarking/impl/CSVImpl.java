package io.ciera.tool.templateengine.ooaofmarking.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.util.Utility;
import io.ciera.tool.templateengine.ooaofmarking.CSV;

public class CSVImpl<C extends IComponent<C>> extends Utility<C> implements CSV {

    private Scanner sc;
    private String inputFilename;

    public CSVImpl(C context) {
        super(context);
        sc = null;
        inputFilename = "";
    }

    public int readline(final String p_filename, final String[] p_values) {
        // set up input
        if (null == sc || !inputFilename.equals(p_filename)) {
            inputFilename = p_filename;
            if (null != sc)
                sc.close();
            File file = new File(inputFilename);
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                getRunContext().getLog().error(e);
                return 0;
            }
        }
        // get next line of input
        String line = "";
        while (sc.hasNextLine() && "".equals(line)) {
            line = sc.nextLine();
            if (line.trim().startsWith("#")) { // skip comment lines
                line = "";
                continue;
            }
        }
        // split line into values
        if (!"".equals(line)) {
            String[] values = line.split(",");
            for (int i = 0; i < values.length; i++) {
                p_values[i] = values[i].trim();
            }
            return values.length;
        } else
            return 0;
    }

    public int writeline(final String p_filename, final String[] p_values) {
        return 0;
    }

}
