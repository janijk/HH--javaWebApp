package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import model.Vene;
import model.dao.Dao;

@WebServlet("/veneet/*")
public class veneet extends HttpServlet {
	private static final long serialVersionUID = 1L;       
    
    public veneet() {
        super();        
    }
    // GET	/veneet && /veneet/ && /veneet/edit/{id} && /veneet/listaus/{searchWord} (space = &nbsp for js)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		String searchWord ="";
		String strJSON ="";
		Dao dao = new Dao();
		ArrayList<Vene> boats;
		if(path==null) {
			boats = dao.listAll();			
			strJSON = new JSONObject().put("boats", boats).toString();
		}else if(path.indexOf("/edit/")!=-1) {
			String id = path.replace("/edit/", "");
			boats = new ArrayList<Vene>();
			if(id=="") {
				strJSON = new JSONObject().put("boats", boats).toString();
			}else {
				int idInt = Integer.parseInt(id);				
				boats.add(dao.getBoat(idInt));
				strJSON = new JSONObject().put("boats", boats).toString();
			}	
		}else if(path.indexOf("/listaus/")!=-1){
			searchWord = path.replace("/listaus/", "");
			boats = dao.listAll(searchWord);
			for (Vene vene : boats) {
				vene.setNimi(vene.getNimi().replace(" ", "&nbsp"));
				vene.setMerkkimalli(vene.getMerkkimalli().replace(" ", "&nbsp"));
			}			
			strJSON = new JSONObject().put("boats", boats).toString();
		}else {
			searchWord = path.replace("/", "");
			boats = dao.listAll(searchWord);
			strJSON = new JSONObject().put("boats", boats).toString();
		}
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(strJSON);
	}
	// POST /veneet
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonObj = new JsonStringToObject().convert(request);
		Vene boat = new Vene();
		Dao dao = new Dao();
		boat.setNimi(jsonObj.getString("nimi"));
		boat.setMerkkimalli(jsonObj.getString("merkkimalli"));		
		boat.setPituus(jsonObj.getDouble("pituus"));
		boat.setLeveys(jsonObj.getDouble("leveys"));
		boat.setHinta(jsonObj.getInt("hinta"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();					
		if(dao.addBoat(boat)){
			out.println("{\"response\":1}");
		}else{
			out.println("{\"response\":0}");
		}
	}
	// PUT /veneet
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonObj = new JsonStringToObject().convert(request);
		Vene boat = new Vene();
		Dao dao = new Dao();
		boat.setNimi(jsonObj.getString("nimi"));
		boat.setMerkkimalli(jsonObj.getString("merkkimalli"));
		boat.setPituus(jsonObj.getDouble("pituus"));
		boat.setLeveys(jsonObj.getDouble("leveys"));
		boat.setHinta(jsonObj.getInt("hinta"));
		boat.setTunnus(jsonObj.getInt("tunnus"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();					
		if(dao.editBoat(boat)){
			out.println("{\"response\":1}");
		}else{
			out.println("{\"response\":0}");
		}
	}
	// DELETE /veneet/{id}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		String cleanId = path.replace("/", "");
		int idInt = Integer.parseInt(cleanId);
		Dao dao = new Dao();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();					
		if(dao.deleteBoat(idInt)){
			out.println("{\"response\":1}");
		}else{
			out.println("{\"response\":0}");
		}
	}

}

