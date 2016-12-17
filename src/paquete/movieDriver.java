package paquete;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



public class movieDriver {
	public final static int REQUEST_DELAY = 3000;
	public String url="https://www.themoviedb.org/movie/";
	public movieDriver(){
		
	}
	
    public static int getStatusConnectionCode(String url) {
		
        Response response = null;
		
        try {
            response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
        }
        return response.statusCode();
    }
    
    public static Document getHtmlDocument(String url) {

        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
        }

        return doc;
    }
	
	 public String run(String tag) throws Exception {
		//concatenamos la peli a la url de consulta 
	    url=url.concat(tag);
	    
	    // Compruebo si me da un 200 al hacer la petición
	    if (getStatusConnectionCode(url) == 200) {
		    //System.out.println("Url de la consulta es "+url+"\n");
		    
		    //Obtengo el HTML de la web en un objeto Document2
	        Document document = getHtmlDocument(url);
			//document.select(".image_content");
	        //obtengo el elemento que contiene la imagen
	        Elements entrada = document.select("img.poster");
	        
	        String imagenSource=entrada.attr("data-src");
	        
	        //System.out.println("entrada"+entrada);
	        //System.out.println("src === "+imagenSource);
	        //String direccionFoto = entradas.select(".posterl").attr("src");
	        return imagenSource;
	    }else{
	    	System.out.println("Code error = : "+getStatusConnectionCode(url));              
	        
	    }
	    
		Thread.sleep(REQUEST_DELAY);
		return null;
		 
	 }
}
