package frontsnapk1ck.io;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * @param T the type of the thing your are saving
 * @param F the file to which you are saving
 */
public abstract class SaverXML< T , F > {
    
    public abstract boolean save( T data , F file );

    protected Document loadDoc() throws ParserConfigurationException 
    {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        return docBuilder.newDocument();
    }

    protected void saveDoc(Document doc, File f) throws TransformerException 
    {
        //write the content into xml file
        TransformerFactory transformerFactory =  TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
            
        StreamResult result =  new StreamResult(f);
        transformer.transform(source, result);        
    }

}
