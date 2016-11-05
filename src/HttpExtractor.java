import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpExtractor {
    public String getLinkInfo(String mInput){
        HttpURLConnection mHttpUrlConnection = null;
        InputStream mInputStream = null;

        try{
        	mHttpUrlConnection = (HttpURLConnection) (new URL(mInput)).openConnection(); 
        	
        	mHttpUrlConnection.setRequestMethod("GET");
            
            // Deceive the server with this when you face to a 403 error.
            mHttpUrlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            
            mHttpUrlConnection.connect();

            StringBuffer mStringBuffer = new StringBuffer();
            mInputStream = mHttpUrlConnection.getInputStream();
            BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream));
            String mString = null;

            while ((mString = mBufferedReader.readLine()) != null)
                mStringBuffer.append(mString + "\n");
            
            return mStringBuffer.toString(); 
        }catch (Throwable t){
            t.printStackTrace();
        }finally{     	
            try {mInputStream.close();}catch(Throwable t) {}
            try {mHttpUrlConnection.disconnect();}catch(Throwable t) {}
        }

        return "Faulty URL!";
    }
}
