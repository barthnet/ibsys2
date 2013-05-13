package controllers;

import play.*;
import play.mvc.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

import org.apache.commons.io.IOUtils;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }      
    
    public static void test() {
    	renderText("test2");
    }
    
    /**
     * receives XML File
     */
    public static void uploadXML() {
    	StringWriter writer = new StringWriter();
    	try {
			IOUtils.copy(request.body, writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String theString = writer.toString();
    	renderText(theString);
    }

}