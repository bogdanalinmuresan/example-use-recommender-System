package paquete;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import Algorithms.CosineSimilarity;
import Algorithms.ItemBased;
import Algorithms.RecommenderApi;
import Algorithms.ScoreAPI;
import Algorithms.SimilarityApi;
import Algorithms.WeightSum;
import Dao.AccessDataAPI;
import Dao.AccessDataNOSQL;
import Dao.Links;
import Ratings.KnnModel;
import Ratings.ModelAPI;

/**
 * Application Lifecycle Listener implementation class DataListenerE
 *
 */
@WebListener
public class DataListenerE implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public DataListenerE() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	ServletContext cntxt = arg0.getServletContext();
		
		/*
        //acceso conexion base de datos
        String cadenaConexion="jdbc:mysql://localhost:3306/sistemarecomendaciontfg";
        AccessDataAPI accesoDataApi=new AccessDataAPI();
        AccessDataJDBC accesoJDBC=new AccessDataJDBC("bogdan","123456",cadenaConexion);
        accesoDataApi.addNewConnection(accesoJDBC);
       */
		
		
		String cadenaConexion="ds033337.mongolab.com:33337/nosql";
		String user="bogdan";
		String pass="ar03pbo";
		AccessDataAPI accesoDataApi=new AccessDataAPI();
		AccessDataNOSQL accesoNosql=new AccessDataNOSQL(user,pass,cadenaConexion);
		accesoDataApi.addNewConnection(accesoNosql);
	       
        //configuramos la medida de similitud
        CosineSimilarity cosine=new CosineSimilarity();
        SimilarityApi similarityApi=new SimilarityApi();
        similarityApi.addSimilarity(cosine);
        
        /*//configuramos el modelo
        EvaluationModel evalModel=new EvaluationModel(accesoDataApi,-1,similarityApi);
        ModelAPI accesoModelo=new ModelAPI(evalModel);
        accesoModelo.setModel(evalModel);
        */
        KnnModel knn=new KnnModel(accesoDataApi, similarityApi);
        ModelAPI accesoModelo=new ModelAPI();
        accesoModelo.setModel(knn);
        HashMap<Integer, Links> links=accesoDataApi.getLinks();
       
        
     
        /***********************************************************************/
        
        //cpnfiguramos los algoritmos de recoemndacion
        //RecommenderApi recbaseline=new RecommenderApi(accesoModelo);
        RecommenderApi recItem=new RecommenderApi(accesoModelo);
        
        //configure baseline predictor
        //BaseLinePredictor baseprediction=new BaseLinePredictor(accesoModelo);
        //recbaseline.addAlgorithm(baseprediction);
        
        //configure item-based algorithm
        WeightSum weightSum=new WeightSum(accesoModelo);
        ScoreAPI measapi=new ScoreAPI(accesoModelo);
        measapi.setScoreMeasure(weightSum);
        ItemBased itemBased=new ItemBased(measapi,accesoModelo);
        recItem.addAlgorithm(itemBased);
	        
	    cntxt.setAttribute("recommenderApi", recItem);
	    cntxt.setAttribute("links", links);
	    cntxt.setAttribute("accessDataApi", accesoDataApi);
	    System.out.println("ha entrado en context listener ");
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
