import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher; 

public class Parsing
{
	public static void main(String args[])
	{
		BufferedReader fp = null;
		String pattern = "[a-z]+";
		String num = "[0-9]+";
		try {
 
			String curLine = null;
			String[] curArr;
			String timestamp = null;
			String logmsg = null;
			String loglevel = null;
			String reqid = null;
			fp = new BufferedReader(new FileReader("log1.txt"));
			while((curLine = fp.readLine()) != null) 
			{
			//	System.out.println(curLine);
				curArr = curLine.split("[ ]+");
				Pattern p1 = Pattern.compile(pattern);
				Matcher m1 = p1.matcher(curArr[0]);
				if(!m1.find())
				{
					timestamp = curArr[0] + " " + curArr[1];
					Pattern p2 = Pattern.compile(num);
					Matcher m2 = p2.matcher(curArr[2]);
					if(!m2.find())
					{
					loglevel = curArr[2];
					logmsg = curArr[7]+curArr[8]+curArr[9];
					reqid = curArr[4].substring(1,curArr[4].length());
					}
				}
								
				System.out.println(timestamp);	
				System.out.println(reqid);
				System.out.println(logmsg);
				System.out.println(loglevel);
				
				
			}
 		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fp != null)
				{	
					fp.close();
				}
			} 
			catch (IOException ex) 
				{
				ex.printStackTrace();
			}
		}
		
}
}
 
