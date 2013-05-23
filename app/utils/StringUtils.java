package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class StringUtils {

	public static String toString(InputStream stream) {
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(stream, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}
	
	public static String toString(HttpResponse response) {
		String result = null;
		try {
			InputStream in = response.getEntity().getContent();
			result = toString(in);
			in.close();
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}

}
