package rpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import db.DBConnection;

/**
 * Servlet implementation class GetRestaurantsNearby
 */
@WebServlet("/GetRestaurantsNearby")
public class GetRestaurantsNearby extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRestaurantsNearby() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json"); //what if I change it to txt
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter writer = response.getWriter();
		JSONObject obj = new JSONObject();
		String username = "";
		try {
			username = request.getParameter("username");
			System.out.println(username);
			if (username != null) {
				obj.append("name", "panda express");
				obj.append("location", "downtown");
				obj.append("country", "united states");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		writer.print(obj);
		writer.flush();
		writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	private static final DBConnection connection = new DBConnection(); //database connection object
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			reader.close();
		} catch (Exception e) { /* report an error */
		}

	 	try {
	 		JSONObject input = new JSONObject(jb.toString());
	 		JSONArray array = null;
	 		if (input.has("lat") && input.has("lon")) {
	 			double lat = (Double) input.get("lat");
	 			double lon = (Double) input.get("lon");
	 			array = connection.GetRestaurantsNearLoationViaYelpAPI(lat, lon); //This function only returns the first 10 restaurants
	 		}
	 		response.setContentType("application/json");
	 		response.addHeader("Access-Control-Allow-Origin", "*");
	 		PrintWriter out = response.getWriter();
	 		out.print(array);
	 		out.flush();
	 		out.close();
	 	} catch (JSONException e) {
	 		e.printStackTrace();
		}
	}
														

}
