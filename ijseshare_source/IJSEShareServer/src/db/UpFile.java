/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;


/**
 *
 * @author ravindu
 */

public class UpFile implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String fileName;
    private String fileExtention;
    private Long fileSize;
    private String uploadedName;
    private String uploadedDate;
    private String uploadedTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileExtention() {
        return fileExtention;
    }

    public void setFileExtention(String fileExtention) {
        this.fileExtention = fileExtention;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }



    public String getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(String uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getUploadedName() {
        return uploadedName;
    }

    public void setUploadedName(String uploadedName) {
        this.uploadedName = uploadedName;
    }

    public String getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(String uploadedTime) {
        this.uploadedTime = uploadedTime;
    }
    
    public void setFile(File file){
        String filename = file.getName();
        StringTokenizer st = new StringTokenizer(filename, ".");
        setFileName(st.nextToken());
        String ext = "";
        while (st.hasMoreTokens()) {            
            ext += "." + st.nextToken();
        }
        setFileExtention(ext);
        setFileSize(file.length());
        setUploadedName(System.currentTimeMillis()+"");
        Date d = new Date();
         
        setUploadedDate(new SimpleDateFormat("dd-MM-yyyy").format(d));
        setUploadedTime(new SimpleDateFormat("hh:mm aa").format(d));
      
        
        
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UpFile)) {
            return false;
        }
        UpFile other = (UpFile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "per.UpFile[ id=" + id + " ]";
    }
    
}
