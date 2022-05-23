package control;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;

public class JsonStringToObject {
	public JSONObject convert(HttpServletRequest request) {		
		JSONObject jsonObject = null;
		BufferedReader buffReader;
		try {
			buffReader = request.getReader();
			StringBuffer strBuffer = new StringBuffer();
			String line = buffReader.readLine();
			while (line != null) {				
				strBuffer.append(line);
				line = buffReader.readLine(); 
			}
			jsonObject = new JSONObject(strBuffer.toString());			
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		return jsonObject;		
	}
}
