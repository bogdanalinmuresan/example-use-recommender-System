package paquete;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import Algorithms.RecommenderApi;
import Dao.AccessDataAPI;
import Dao.Events;
import Dao.Item;
import Dao.Links;
import Dao.User;

/**
 * Servlet implementation class recommenderServlet
 */
@WebServlet("/recommenderServlet")
public class recommenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public recommenderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Sets the content type of the response being sent to the client, if the response has not been committed yet.
		response.setContentType("text/html");
		//
		String usuarioString = request.getParameter("usuario");
		if (usuarioString!=null){
			int usuario=Integer.parseInt(usuarioString);
			String xml=request.getParameter("xml");
			Boolean hayXml=Boolean.parseBoolean(xml);
			
			
			//Returns a PrintWriter object that can send character text to the client.
			PrintWriter pw = response.getWriter();
			//pw.print("entra en servlet recommenderServlet");
			ServletContext sc=getServletContext();
			
			try{
	
	            RecommenderApi recApi=(RecommenderApi) sc.getAttribute("recommenderApi");
	            //*******************
	            
	            HashMap<Integer, Links> links=(HashMap<Integer, Links>) sc.getAttribute("links");
	            ArrayList<Item> top10=recApi.topNRecomendation(new User(usuario), 10);
	            
	            //request.setAttribute("elementos", top10);
	            //request.getRequestDispatcher("index.jsp").forward(request, response);
	            ArrayList<String> peliculasRecomendadassUrl=new ArrayList<>();
	            
	            //para todas las peliculas recomendado, sacar URL de la imagen
	            for(Item peliculaRecomendada:top10){
	            	movieDriver sacarUrl=new movieDriver();
	            	//codigo pelicula para tmdb
	            	String codigoPeliVista= links.get(peliculaRecomendada.getId()).getTmdbId();
	            	//saca url para pelicula recomendada
	            	String peliculaUrl=sacarUrl.run(java.net.URLEncoder.encode(codigoPeliVista, "UTF-8"));
	            	peliculasRecomendadassUrl.add(peliculaUrl);
	            }
	            request.setAttribute("PeliculasRecomendadasUrl", peliculasRecomendadassUrl);
	            
	          //*******************
	            ArrayList<Events>peliculasEventos=new ArrayList<>();
	            ArrayList<Item>peliculasVistas=new ArrayList<>();
	            ArrayList<String>peliculasVistasUrl=new ArrayList<>();
	            
	            
	            AccessDataAPI accessDataApi=(AccessDataAPI) sc.getAttribute("accessDataApi");
	            //obtener los eventos del usuario
	            peliculasEventos=accessDataApi.getUserEvent().get(new User(usuario));
	            if(peliculasEventos==null){
	            	System.out.println("es null");
	            }
	            //obtener peliculas vistas
	            for(Events eventos:peliculasEventos){
	            	Item i=new Item(eventos.getItem());
	            	peliculasVistas.add(i);
	            }
	            
	            //obtener la url de todas la peliculas vistas
	            for(int i=0;i<peliculasVistas.size() && i<20 ;i++){
	            	movieDriver obtencionUrlPeliVista=new movieDriver();
	            	String codigoPeliVista= links.get(peliculasVistas.get(i).getId()).getTmdbId();
	            	String peliculaUrl=obtencionUrlPeliVista.run(java.net.URLEncoder.encode(codigoPeliVista, "UTF-8"));
	            	peliculasVistasUrl.add(peliculaUrl);
	            }
	            
	            request.setAttribute("peliculasVistasUrl", peliculasVistasUrl);
	            request.getRequestDispatcher("index.jsp").forward(request, response);

			}//try
		
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
		}//if
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
