package com;
import com.handlers.AppHandler;
import java.io.IOException;

/**
 * Main entry point for the Bank Account Management System.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        AppHandler app = new AppHandler();
        app.start();
    }
}
