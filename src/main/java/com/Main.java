package com;
import com.handlers.AppHandler;
import com.models.*;
import com.service.AccountManagement;
import com.service.CustomerManagement;
import com.service.FilePersistenceService;
import com.service.TransactionManagement;
import com.utilities.ValidationUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main entry point for the Bank Account Management System.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        AppHandler app = new AppHandler();
        app.start();
    }
}
