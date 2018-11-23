
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jorge
 */
public class EjecutarXpath 
{
   
   Document doc;
//Como abrir un documento XML con DOM para
//ejecutar sobre él una consulta (/Libros/*/Autor).

   public String ejecutaXPath(String text)
    {
        String salida = "";
        
     try
     {
               
       //Crea el objetoXPath
         XPath xpath = XPathFactory.newInstance().newXPath();
       
       //Crea un XpathExpression con la consulta deseada
         XPathExpression exp = xpath.compile(text);
       
       //Ejecuta la consulta indicando que se ejecute sobre el DOM y
       //que devolverá el resultadocomo una lista de nodos
         Object result = exp.evaluate(doc, XPathConstants.NODESET);
         NodeList nodeList = (NodeList) result;
         
       //Ahora recorre la lista para sacar los resultados
         for(int i=0; i<nodeList.getLength();i++)
         {
            salida = salida + "\n" + nodeList.item(i).getChildNodes().item(0).getNodeValue();
            
         }
         return salida;
     }catch(Exception ex)
        {
            System.out.println("Error:" + ex.toString());
           
        }
         return text;
         
    }
   
    public int abrir_XML_DOM(File fichero )
            {
           
                 
                try
                 {
                    DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
                    factory.setIgnoringComments(true);
                    factory.setIgnoringElementContentWhitespace(true);
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    doc = builder.parse(fichero);
                    return 0;
                 }catch(Exception e)
                    {
                        e.printStackTrace();
                        return -1;
                    }
                
            }
    
    public String recorrerDOMyMostrar(Document doc)
    {
    
        String datos_nodo[]=null;
        String salida="";
        Node node;
       
        //obtiene el primer nodo del DOM(primer hijo)
        Node raiz=doc.getFirstChild();  
        
        //obtiene una lista de nodos con todos los nodos hijo de la raiz
        NodeList nodelist=raiz.getChildNodes();                                 
        //Procesa los nodos hijo
        for(int i=0; i<nodelist.getLength(); i++)
        {                              
            node = nodelist.item(i);
            if (node.getNodeType()==Node.ELEMENT_NODE)
            {
                //Es un nodo libro
                datos_nodo = procesarLibro(node);                               
                    salida = salida + "\n" + "Publicado en:"+ datos_nodo[0];
                    salida = salida + "\n" + "El autor es:" +datos_nodo[1];
                    salida = salida + "\n" + "El titulo es:"+datos_nodo[2];
                    salida = salida + "\n-----------"; 
            }
            
        }
        return salida;
    }
    
    protected String[] procesarLibro(Node n)
    {
        String datos[]= new String[3];
        Node ntemp = null;
        int contador = 1;
        
        //obtiene el valor del primer atributo del nodo(uno en este ejempo)
        datos[0]=n.getAttributes().item(0).getNodeValue();                      
        
        //obtiene los hijos del libro (título y autor)
        NodeList nodos = n.getChildNodes();                                      
        
        for (int i=0; i < nodos.getLength(); i++)
        {
            ntemp =nodos.item(i);
            
            if(ntemp.getNodeType() == Node.ELEMENT_NODE)
            {
                //IMPORTANTE:Para obtener el texto con el título y el autor se accede al
                //nodo TEXT hijo de ntemp y se saca su valor
                datos[contador]= ntemp.getChildNodes().item(0).getNodeValue();  
                contador++;
            }
        }
        return datos;
    }
    
    public static void main(String[] args) 
    {
       
    }
    
}
