package training;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.spi.XmlReader;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Benobab on 20/01/16.
 */
public class FluxRss extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            URL url = new URL("http://www.lequipe.fr/rss/actu_rss.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            String message="";
            while ((line = reader.readLine()) != null)
            { // Traitement des données reçue
                message+=line;
            }
            System.out.println(message);
            StringWriter stringWriter = new StringWriter();
            Result m = new StreamResult(stringWriter);
            Source xsl = new StreamSource(new File("rssxsl.xsl"));
            try {
                Transformer t = TransformerFactory.newInstance().newTransformer(xsl);
                t.transform(new StreamSource(new StringReader(message)), m);
                StringBuffer stringBuffer = stringWriter.getBuffer();

                response.getWriter().write(stringBuffer.toString());

            }catch (TransformerException e) {
                    e.printStackTrace();
            }
            reader.close();


        } catch (MalformedURLException e) {
            // Gestion d’exceptions d’ouverture de flux
        } catch (IOException e) {
            // Gestion d’exceptions de lecture de flux
        }
    }
}
