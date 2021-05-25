package com.company.DAO;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LogFile {

    private static PrintWriter printWriter;

    public LogFile() throws FileNotFoundException
    {
        printWriter = new PrintWriter("log.txt");
    }

    public static PrintWriter getPrintWriter()
    {
        return printWriter;
    }

}
