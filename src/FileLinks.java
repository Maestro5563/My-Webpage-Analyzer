import java.util.ArrayList;

public class FileLinks {
	private String[] typeName = {".jpg", ".png", ".bmp"};
	private String[] urlTail = {".com", ".org"};
	private String root = "";
	public ArrayList<String> getFileLinks(String[] mScript, boolean[] type){	
		ArrayList<String> mLinks = new ArrayList<String>();
		ArrayList<Integer> pos;
		
		getRoot(mScript[1]);
		
		for (int i = 0; i < type.length; i++){
			if (type[i]) {
				pos = new WordSearcher().getFoundWordPosition(mScript[0], typeName[i]);
				for (int j = 0; j < pos.size(); j++){
					String temp = pickOutLink(mScript, pos.get(j).intValue() + 3);
					if (!mLinks.contains(temp))
						mLinks.add(temp);
				}
			}
		}
		
		return mLinks;
	}
	
	private String pickOutLink(String[] mScript, int tail){
	    int head = tail;
	    while (mScript[0].charAt(head) != '\'' && mScript[0].charAt(head) != '\"' &&
	    		mScript[0].charAt(head) != '('){
	    	head--;
	    }
	    
		String url = mScript[0].substring(head + 1, tail + 1);
		if (url.indexOf("http") == -1){
			url = root + url;
		}
		
		return url;
	}
	
	private void getRoot(String raw){
		int cutPoint = 0;
		for (int i = 0; i < urlTail.length; i++){
			cutPoint = raw.indexOf(urlTail[i]);
			if (cutPoint != -1) 
				break;
		}
		
		root = raw.substring(0, cutPoint + 4);
	}
}
