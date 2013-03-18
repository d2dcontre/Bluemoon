package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import javax.sql.DataSource;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.db.DB;
import play.libs.Json;
import play.mvc.*;

public class Application extends Controller 
{
	public static Result index() 
	{
		return ok("Home page");
	}
	
	public static Result printMssg(String error)
	{
		JsonNode json = request().body().asJson();
		ObjectNode result = Json.newObject();
		result.put("message", error);
		return ok(result.toString());
	}
	
	public static void doJson( ResultSet rs, ArrayList<JsonNode> on) throws SQLException
	{
		String tempTitle = "";
		String id = "", name = "",supplier = "";
		int numItems = 0;
		try 
		{  
			while (rs.next()) 
			{
				id = rs.getString(1);
				name = rs.getString(2);
				numItems = rs.getInt(3);
				supplier = rs.getString(4);

				JsonNode json = request().body().asJson();
				ObjectNode result = Json.newObject();
				result.put("id", id);
				result.put("name", name);
				result.put("number_of_items", numItems);
				result.put("supplier", supplier);
				on.add(result);
			}
		} 
		finally 
		{
			rs.close();
		}
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result getItems() throws SQLException
	{
		DataSource ds = DB.getDataSource();
		Statement s = ds.getConnection().createStatement();
		String sql = "SELECT * FROM Item";
		ResultSet rs = s.executeQuery(sql);
		
		ArrayList<JsonNode> on = new ArrayList<JsonNode>();
		ObjectNode fin = Json.newObject();
		doJson(rs, on);
		
		Collection<JsonNode> c = on;
		fin.putArray("items").addAll(c);
		return ok(fin.toString());
	}
	
	public static Result addItem(String id, String name, String supplier, int num) throws SQLException
	{
		DataSource ds = DB.getDataSource();
		Statement s = ds.getConnection().createStatement();
		String sql = "SELECT item_id FROM Item";
		ResultSet rs = s.executeQuery(sql);
		boolean isID = checkExists(rs, id, 1); 
		
		if (isID == true)
		{
			return printMssg("ID already exists.");
		}
		
		sql = "Select item_name FROM Item where upper(item_name) like upper('%" + name + "%')";
		rs = s.executeQuery(sql);
		boolean nameExists = checkExists(rs, name, 1);
		if (nameExists == true)
		{
			return printMssg("Item " + name + " already exists in the database. Restock item " + name + " instead.");
		}
	
		sql = "INSERT INTO Item(item_id,item_name,number_items,supplier) values ('" + id + "', '" + name + "', " + num + ", '" + supplier + "')";
		s.executeUpdate(sql);
		
		return printMssg(name + " successfully added! ");
	}
	
	public static Result restock(String id, int num) throws SQLException
	{
		if ( num < 0 )
		{
			return printMssg("Negative numbers are not allowed.");
		}
		
		DataSource ds = DB.getDataSource();
		Statement s = ds.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String sql = "SELECT item_id FROM Item";
		ResultSet rs = s.executeQuery(sql);
		boolean isID = checkExists(rs, id, 1); 
		if (isID == false)
		{
			return printMssg("ID does not exist.");
		}
		
		sql = "SELECT item_id, number_items FROM Item";
		rs = s.executeQuery(sql);
		String ID = "";
		int tempNum = 0;
		try 
		{  
			while (rs.next()) 
			{
				ID = rs.getString(1);
				if ( ID.equals(id) )
				{
					tempNum = rs.getInt(2);
					tempNum = tempNum + num;
					rs.updateInt("number_items", tempNum);
					rs.updateRow();
				}
			}
		} 
		finally 
		{
			rs.close();
		}
		
		return printMssg(id + " successfully restocked! Has now " + tempNum + " items.");
	}
	
	public static boolean checkExists ( ResultSet rs, String toCheck, int index ) throws SQLException
	{
		String str = "";
		try 
		{  
			while (rs.next()) 
			{
				str = rs.getString(index);
				if ( str.equalsIgnoreCase(toCheck) )
				{
					return true;
				}
			}
		} 
		finally 
		{
			rs.close();
		}
		return false;
	}

	public static Result getTrans() {
		try {
			DataSource ds = DB.getDataSource();
			Statement s = ds.getConnection().createStatement();
			String sql = "SELECT * FROM Transaction";
			ResultSet rs = s.executeQuery(sql);
			
			ArrayList<JsonNode> on = new ArrayList<JsonNode>();
			ObjectNode fin = Json.newObject();
			doJson(rs,on);
			
			Collection<JsonNode> c = on;
			fin.putArray("transactions").addAll(c);
			return ok(fin.toString() );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String s = e.getStackTrace().toString();
			ObjectNode o = Json.newObject();
			o.put("status", "error");
			o.put("message", e.getMessage() );
			
			return internalServerError(o);
		}
	}
}