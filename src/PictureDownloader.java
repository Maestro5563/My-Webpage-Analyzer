import java.awt.image.BufferedImage;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;

public class PictureDownloader {		
	public void downloadImageBatch(ArrayList<String> mLinks, String directory, JTextArea textArea){
		int batchCount = mLinks.size() - 1;
		 
		while(batchCount >= 0){
			 try{	 
				 String url = mLinks.get(batchCount).toString();
				 textArea.append(url + "\n");
				 
				 URL mURL = new URL(url);
				 HttpURLConnection mHttpUrlConnection = (HttpURLConnection) mURL.openConnection();
				 
				 // Deceive the server with this when you face to a 403 error.
		         mHttpUrlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
				 BufferedImage mImage = ImageIO.read(mHttpUrlConnection.getInputStream());
				 
				 File outputFile = new File(directory + "\\" + (batchCount + 1) + url.substring(url.length() - 4));
				 
				 if (mImage != null)
					 ImageIO.write(mImage, url.substring(url.length() - 3), outputFile);
				 		 
				 textArea.append("Downloading: Currently: " + (mLinks.size() - (batchCount--) + "/" + mLinks.size()) + "\n");
		        }catch(Throwable t) {
		            t.printStackTrace();
		            batchCount--;
		        }
		}
	}
}
