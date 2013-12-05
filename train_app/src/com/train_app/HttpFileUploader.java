package com.train_app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Environment;
import android.util.Log;

public class HttpFileUploader  {

        URL connectURL;
        String params;
        String responseString;
        String fileName;
        byte[] dataToServer;
        FileInputStream fileInputStream = null;

        HttpFileUploader(String urlString, String params, String fileName) {
                try {
                        connectURL = new URL(urlString);
                } catch (Exception ex) {
                        Log.i("URL FORMATION", "MALFORMATED URL");
                }
                this.params = params + "=";
                this.fileName = fileName;

        }
        

        
        void doStart(FileInputStream stream) {
                fileInputStream = stream;
                Log.e("stream", fileInputStream.toString());
                String selectedPath = fileName;
Log.e("path", fileName);
                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                DataInputStream inStream = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary =  "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1*1024*1024;
                String responseFromServer = "";
                String urlString = "http://domaine-berge-de-sainte-rose.com/train-app/upload_sound.php";
                try
                {
                 //------------------ CLIENT REQUEST
                FileInputStream fileInputStream = new FileInputStream(new File(selectedPath) );
                 // open a URL connection to the Servlet
                 URL url = new URL(urlString);
                 // Open a HTTP connection to the URL
                 conn = (HttpURLConnection) url.openConnection();
                 // Allow Inputs
                 conn.setDoInput(true);
                 // Allow Outputs
                 conn.setDoOutput(true);
                 // Don't use a cached copy.
                 conn.setUseCaches(false);
                 // Use a post method.
                 conn.setRequestMethod("POST");
                 conn.setRequestProperty("Connection", "Keep-Alive");
                 conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
                 dos = new DataOutputStream( conn.getOutputStream() );
                 dos.writeBytes(twoHyphens + boundary + lineEnd);
                 dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + selectedPath + "\"" + lineEnd);
                 dos.writeBytes(lineEnd);
                 // create a buffer of maximum size
                 bytesAvailable = fileInputStream.available();
                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
                 buffer = new byte[bufferSize];
                 // read file and write it into form...
                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                 while (bytesRead > 0)
                 {
                  dos.write(buffer, 0, bufferSize);
                  bytesAvailable = fileInputStream.available();
                  bufferSize = Math.min(bytesAvailable, maxBufferSize);
                  bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                 }
                 // send multipart form data necesssary after file data...
                 dos.writeBytes(lineEnd);
                 dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                 // close streams
                 Log.e("Debug","File is written");
                 fileInputStream.close();
                 dos.flush();
                 dos.close();
                }
                catch (MalformedURLException ex)
                {
                     Log.e("Debug", "error: " + ex.getMessage(), ex);
                }
                catch (IOException ioe)
                {
                     Log.e("Debug", "error: " + ioe.getMessage(), ioe);
                }
                //------------------ read the SERVER RESPONSE
                try {
                      inStream = new DataInputStream ( conn.getInputStream() );
                      String str;
         
                      while (( str = inStream.readLine()) != null)
                      {
                           Log.e("Debug","Server Response "+str);
                      }
                      inStream.close();
         
                }
                catch (IOException ioex){
                     Log.e("Debug", "error: " + ioex.getMessage(), ioex);
                }
        }

}
