/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 *
 * @author ravindu
 */
public interface ControllerFileInterface extends Remote {

    public List<File> getFiles() throws RemoteException;
    
    public byte[] downloadFile(String fileName) throws RemoteException, Exception;

    public void uploadFile(byte[] filedata, String filename) throws RemoteException, Exception;

    

}
