/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;


import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import view.MainFrame;



/**
 *
 * @author ravindu
 */
public class RemoteFileController extends UnicastRemoteObject implements ControllerFileInterface {
    
    private static String path = ".." ;// "/home/ravindu/ijseshare"; 
    private List<File> files;
  
    
    public RemoteFileController() throws RemoteException{
      
        File d = new File(path+"/upfiles");
        File f[] = d.listFiles();
        if (f == null) {
            f = new File[0];
            System.out.println("files null");
        }
        files = new Vector<File>();
        for (File file : f) {
            files.add(file);
        }
    }

    public byte[] downloadFile(String fileName) throws RemoteException,Exception {
        try {
            File file = new File(path+"/upfiles/"+fileName);
            byte buffer[] = new byte[(int) file.length()];
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
            input.read(buffer, 0, buffer.length);
            input.close();
            return (buffer);
        } catch (Exception e) {
            System.out.println("FileImpl: " + e.getMessage());
            e.printStackTrace();
            return (null);
        }
    }
    
    public void uploadFile(byte[] filedata,String filename) throws RemoteException,Exception{
        System.out.println("in upload method");
         File file = new File(path+"/upfiles/"+filename);
         BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file.getAbsolutePath()));
         System.out.println(file.getName());
         output.write(filedata,0,filedata.length);
         System.out.println("length "+filedata.length);
         output.flush();
         output.close();
         System.out.println("done");
         for (File f : files) {
             if (f.getAbsolutePath().equals(file.getAbsolutePath())) {
                 files.remove(f);
                 break;
             }
         }
         files.add(file);
    }

    public List<File> getFiles() throws RemoteException{
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
    
    
    public boolean deleteFile(File file){
       boolean deleted= file.delete();
        if (deleted) {
            files.remove(file);
        } 
       return deleted;
    }
    
    
    
}
