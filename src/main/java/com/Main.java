package com;
import com.handlers.AppHandler;
import com.service.FilePersistenceService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main entry point for the Bank Account Management System.
 */
public class Main {

    public static void main(String[] args) throws IOException {
//        ArrayList<List> rd= FilePersistenceService.readFromFile("account");
//        System.out.println(rd.size());
//        for (int i =0;i<rd.size();i++){
//            for(int j=0; j < rd.get(0).size();j++){
////                System.out.println(rd.get(0).get(0));
////            System.out.println(rd.get(0).get(1));
////            System.out.println(rd.get(0).get(2));
////            System.out.println(rd.get(0).get(3));
////            System.out.println(rd.get(0).get(4));
//            System.out.println(rd.get(i).get(j));
//
//            }
//        }
        AppHandler app = new AppHandler();
        app.start();
    }
}
