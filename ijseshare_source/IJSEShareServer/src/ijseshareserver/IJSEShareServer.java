///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package ijseshareserver;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.util.StringTokenizer;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import rmi.ControllerFileInterface;
//import rmi.RemoteFileController;
//
//
///**
// *
// * @author ravindu
// */
//public class IJSEShareServer {
//
// 
//    private static String port;
//    private static String ip;
//    private static String path = "/home/ravindu/ijseshare/bin/";
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//
//
//   
//
//
//        readConfig();
//        System.out.println("at port : "+port +" ip : " + ip);
//        try {
//            Thread t = new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    bf.setText("creating registry...");
//                }
//            });
//            t.start();
//            Registry reg = LocateRegistry.createRegistry(Integer.parseInt(port));
//
//            ControllerFileInterface fi = new RemoteFileController();
//            System.out.print("binding FileInterface...\t");
//            bf.setText("binding FileInterface...");
//            reg.rebind("fi", fi);
//            System.out.println("[done]");
//            System.out.println("server is up and running.......");
//            bf.setText("server is up and running.......", 2000L);
//            bf.close();
//        } catch (RemoteException ex) {
//            ex.printStackTrace();
//            bf.setErrorMessage(ex.getMessage());
//
//        } catch (Exception e) {
//            bf.setErrorMessage(e.getMessage());
//
//        } finally {
//            bf.close();
//        }
//    }
//
//    private static void readConfig() {
//        try {
//            File file = new File(path + "ijseshare.config");
//            BufferedReader br = new BufferedReader(new FileReader(file));
//
//            br.readLine();
//            String s = "";
//            while ((s = br.readLine()) != null && !s.trim().isEmpty()) {
//                StringTokenizer st = new StringTokenizer(s, "=");
//                String w = st.nextToken().trim();
//                if (w.equals("port")) {
//                    String p = st.nextToken().trim();
//                    port = p;
//                } else if (w.trim().equals("ip")) {
//                    String p = st.nextToken().trim();
//                    ip = p;
//                }
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            bf.setErrorMessage("config file is corrupted!");
//            System.exit(0);
//        }
//    }
//}
