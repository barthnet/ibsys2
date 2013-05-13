package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

public class StringUtils {

	public static String stringify(InputStream stream) {
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(stream, writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return writer.toString();
	}
}
