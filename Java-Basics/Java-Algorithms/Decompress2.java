import java.util.*;

public class Decompress2 {

    List<Object> list = new ArrayList<Object>();
    List<Object> list2 = new ArrayList<Object>();

    Object[] tokens, tokens2;
    int posLast = 0;
    String[] array = new String[10];
	
    public void tokenize(String s) {
    
	int i = 0;
	while (i < s.length()) {
	    if ('0' <= s.charAt(i) && s.charAt(i) <= '9') {
	        String number = "";
		while ( ('0' <= s.charAt(i) && s.charAt(i) <= '9') && (i < s.length())) {
		    number += s.charAt(i++);		    
		}
	    list.add(Integer.valueOf(number));

	    } else 
	        list.add(s.charAt(i++));
	}
	
	tokens = list.toArray(new Object[list.size()]);
	
    }	
	
    public String parse(Object[] tokens, int pos) {
	int p1 = pos;
	int p2 = pos;
	int pointer1 = pos;
	int pointer2 = pos;
	
  	String s = "";
	String fs = "";
	int r = 0;
	
	if (tokens[pos]	!= (Object)')') {
	   return parse(tokens, pos + 1);
	
	} else if (tokens[pos] == (Object)')') {
	    p1 = pos;
	    p2 = setP2(pos);
            pointer1 = p1;
	    pointer2 = p2;
	    	   
	    while (p2 + 1 < p1) {
		if (!(tokens[p2 + 1] instanceof Integer)) {
		    s += tokens[p2 + 1];
		    p2++;
		} else if (tokens[p2 + 1] instanceof Integer) {
		    s += repeat(tokens[p2 + 2].toString(), (int)tokens[p2 + 1]);
		    tokens[p2 + 1] = "";
		    tokens[p2 + 2] = "";
		    p2++;
		}
	    }
	    
	    if ( (tokens[pointer2] == (Object)'(')  && (tokens[pointer2 - 1] instanceof Integer) ) {
	        fs = repeat(s, (int)tokens[pointer2 - 1]);
		for (int i = pointer2 - 1; i < pointer1; i++) 
		    tokens[i] = "";
		    tokens[pointer1] = "";
		    tokens[pointer2] = fs;
		
	    } else {
		tokens[pointer2] = s;
	    }
	
	}
     	
	boolean tr = false;
	for(Object rr: tokens) {
	if (rr == (Object)'(')
	tr = true;
	}
	
	if(tr) {
	return parse(tokens, 0);
	}
	else
	return fs;
    }

	
    public int setP2(int pos) {
	if (tokens[pos] != (Object)'(') {
	 return  setP2(pos - 1);	
	}
	else return pos;
    }
    
    public String repeat(String s, int r) {
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < r; i++) {
	    sb.append(s);
	}
	return sb.toString();
    }
	    
   	
    public static void main(String[] args) {
	Decompress2 d2 = new Decompress2();

	Scanner sc = new Scanner(System.in);
	String s = sc.next();
	
	d2.tokenize("1(" + s + ")");
	System.out.println(d2.parse(d2.tokens, 0));	
	sc.close();
    }
}
