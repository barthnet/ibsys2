package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;

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

}
