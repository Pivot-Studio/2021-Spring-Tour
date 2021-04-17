package main;

import mail_master.monitor.Monitor;
import star.request.Request;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Monitor monitor =new Monitor();
        Request request=new Request();
        monitor.start();
        request.request();
    }
}
