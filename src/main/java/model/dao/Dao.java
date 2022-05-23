package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Vene;

public class Dao {
	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement prepdStmt=null; 
	private String sql;
	private String db ="Venekanta.sqlite";
	
	// Connect to database
	private Connection connect(){		
    	Connection con = null;    	
    	String path = "C:\\Users\\janij\\Desktop\\Koulu\\Ohjelmointi 2\\TenttiOhj2/";
    	//String path = System.getProperty("catalina.base").substring(0, System.getProperty("catalina.base").indexOf(".metadata"));
    	String url = "jdbc:sqlite:"+path+db;
    	try {	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	        
	     }catch (Exception e){	
	        e.printStackTrace();	         
	     }
	     return con;
	}
	// List everything
	public ArrayList<Vene> listAll() {
		ArrayList<Vene> boats = new ArrayList<Vene>();
		sql = "SELECT * FROM veneet";       
		try {
			con=connect();
			if(con!=null){ 
				prepdStmt = con.prepareStatement(sql);        		
        		rs = prepdStmt.executeQuery();
				if(rs!=null){ 					
					while(rs.next()){
						Vene boat = new Vene();
						boat.setTunnus(rs.getInt(1));
						boat.setNimi(rs.getString(2));
						boat.setMerkkimalli(rs.getString(3));
						boat.setPituus(rs.getDouble(4));	
						boat.setLeveys(rs.getDouble(5));
						boat.setHinta(rs.getInt(6));
						boats.add(boat);
					}					
				}				
			}	
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boats;
	}
	// List everything with param
	public ArrayList<Vene> listAll(String searchWord){
		ArrayList<Vene> boats = new ArrayList<Vene>();
		sql = "SELECT * FROM veneet WHERE tunnus LIKE ? OR nimi LIKE ? OR merkkimalli LIKE ? OR pituus LIKE ? OR leveys LIKE ? OR hinta LIKE ?";       
		try {
			con=connect();
			if(con!=null){ 
				prepdStmt = con.prepareStatement(sql);  
				prepdStmt.setString(1, "%"+searchWord+"%");
				prepdStmt.setString(2, "%"+searchWord+"%");
				prepdStmt.setString(3, "%"+searchWord+"%");
				prepdStmt.setString(4, "%"+searchWord+"%");
				prepdStmt.setString(5, "%"+searchWord+"%");
				prepdStmt.setString(6, "%"+searchWord+"%");
        		rs = prepdStmt.executeQuery();
				if(rs!=null){ 					
					while(rs.next()){
						Vene boat = new Vene();
						boat.setTunnus(rs.getInt(1));
						boat.setNimi(rs.getString(2));
						boat.setMerkkimalli(rs.getString(3));
						boat.setPituus(rs.getDouble(4));	
						boat.setLeveys(rs.getDouble(5));
						boat.setHinta(rs.getInt(6));
						boats.add(boat);
					}					
				}				
			}	
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return boats;
	}
	// Search with ID
	public Vene getBoat(int tunnus) {
		Vene boat = null;
		sql ="SELECT * FROM veneet WHERE tunnus=?";
		try {
			con = connect();
			prepdStmt = con.prepareStatement(sql);
			prepdStmt.setInt(1, tunnus);
			rs = prepdStmt.executeQuery();
			if(rs.isBeforeFirst()){ 					
				rs.next();
				boat = new Vene();
				boat.setTunnus(rs.getInt(1));
				boat.setNimi(rs.getString(2));
				boat.setMerkkimalli(rs.getString(3));
				boat.setPituus(rs.getDouble(4));	
				boat.setLeveys(rs.getDouble(5));
				boat.setHinta(rs.getInt(6));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boat;
	}
	// Add entry
	public boolean addBoat(Vene boat) {
		boolean isSuccess = true;
		sql="INSERT INTO veneet (nimi,merkkimalli,pituus,leveys,hinta) VALUES(?,?,?,?,?)";						  
		try {
			con = connect();
			prepdStmt=con.prepareStatement(sql); 
			prepdStmt.setString(1, boat.getNimi());
			prepdStmt.setString(2, boat.getMerkkimalli());
			prepdStmt.setDouble(3, boat.getPituus());
			prepdStmt.setDouble(4, boat.getLeveys());
			prepdStmt.setInt(5, boat.getHinta());
			prepdStmt.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			isSuccess = false;
		}				
		return isSuccess;
	}
	// Delete entry
	public boolean deleteBoat(int tunnus) {
		boolean isSuccess = true;
		sql="DELETE FROM veneet WHERE tunnus=?";
		try {
			con = connect();
			prepdStmt=con.prepareStatement(sql);
			prepdStmt.setInt(1, tunnus);
			prepdStmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}
	
	// Edit entry
	public boolean editBoat(Vene boat) {
		boolean isSuccess = true;
		sql = "UPDATE veneet SET nimi=?, merkkimalli=?, pituus=?, leveys=?, hinta=? WHERE tunnus=?";
		try {
			con = connect();
			prepdStmt=con.prepareStatement(sql);
			prepdStmt.setString(1, boat.getNimi());
			prepdStmt.setString(2, boat.getMerkkimalli());
			prepdStmt.setDouble(3, boat.getPituus());
			prepdStmt.setDouble(4, boat.getLeveys());
			prepdStmt.setInt(5, boat.getHinta());
			prepdStmt.setInt(6, boat.getTunnus());
			prepdStmt.executeUpdate();
	        con.close();
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}
}
