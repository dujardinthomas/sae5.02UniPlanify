package fr.uniplanify.views;


import java.io.*;
import java.util.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class CharResponseWrapper extends HttpServletResponseWrapper {
    private CharArrayWriter charWriter;

    public CharResponseWrapper(HttpServletResponse response) {
        super(response);
        charWriter = new CharArrayWriter();
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(charWriter);
    }

    @Override
    public String toString() {
        return charWriter.toString();
    }
}
