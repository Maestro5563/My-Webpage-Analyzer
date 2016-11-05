import java.util.ArrayList;

public class WordSearcher {
	public ArrayList<Integer> getFoundWordPosition(String mString, String mCandidate){
		int cursor = 0; 
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		while (mString.indexOf(mCandidate, cursor) != -1){
			result.add(mString.indexOf(mCandidate, cursor));
			cursor += mCandidate.length();
		}		
		
		return result;
	}

}
