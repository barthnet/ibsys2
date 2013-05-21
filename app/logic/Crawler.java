package logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;

import play.Logger;

import utils.Constants;
import utils.StringUtils;

public class Crawler {

	private String username;
	private String password;
	private String sessionId;
	private String period;
	private HttpClient client;

	public Crawler(String username, String password) {
		this.username = username;
		this.password = password;
		this.sessionId = getSessionId();
	}
	
	public InputStream importFileFromWeb() {
		if (checkLogin()) {
			HttpPost httppost = new HttpPost(Constants.simulate);
			httppost.addHeader("Cookie", this.sessionId);
			StringBody res = null;
			StringBody per = null;
			try {
				res = new StringBody("result");
				per = new StringBody(this.period);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("source", res);
			reqEntity.addPart("period", per);
			httppost.setEntity(reqEntity);
			HttpResponse response = doRequest(httppost);
			String responseStr = StringUtils.toString(toStream(response));
			if (responseStr.contains("<title>Simulation result</title>")) {
				Logger.info("simulate erfolgreich", 0);
				HttpGet httpget = new HttpGet(Constants.xmlFile);
				httpget.setHeader("Cookie", sessionId);
				HttpResponse fileResp = doFileRequest(httpget);
				return toStream(fileResp);
			}			
		}
		return null;
	}

	public boolean checkLogin() {
		HttpPost httppost = new HttpPost(buildLoginUrl());
		httppost.addHeader("Cookie", this.sessionId);
		httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		String response = StringUtils.toString(toStream(doRequest(httppost)));		
		Pattern pattern = Pattern.compile("<option selected value=\\\"([0-9]{1,2})\\\">[0-9]{1,2}</option>");
		Matcher matcher = pattern.matcher(response);
		if (matcher.find())	{
			this.period = matcher.group(1);
			return true;
		}
		return false;
	}

	private String getSessionId() {
		HttpGet httpget = new HttpGet(Constants.startPage);
		HttpResponse response = doRequest(httpget);
		String sessionId = response.getFirstHeader("Set-Cookie").getValue();
		return sessionId.substring(0, sessionId.indexOf(";"));
	}
	
	private HttpResponse doFileRequest(HttpGet get) {
		HttpResponse response = null;
		client = new DefaultHttpClient();
		try {
			response = client.execute(get);
		} catch (IOException e) {
			return null;
		}
		return response;
	}

	private HttpResponse doRequest(HttpRequestBase request) {
		HttpResponse response = null;
		client = new DefaultHttpClient();
		try {
			response = client.execute(request);
		} catch (IOException e) {
			return null;
		} finally {
			client.getConnectionManager().shutdown();
			if (response.getStatusLine().getStatusCode() == 302) {
				HttpGet httpget = new HttpGet(response.getFirstHeader("Location").getValue());
				httpget.setHeader("Cookie", this.sessionId);
				response = doRequest(httpget);
			}
		}
		return response;
	}
	
	@SuppressWarnings("all")
	private void printHeaders(Header[] headers){
		for (Header h : headers) {
			Logger.info("%s : %s", h.getName(), h.getValue());
		}
	}

	private InputStream toStream(HttpResponse response) {
		InputStream stream = null;
		try {
			stream = response.getEntity().getContent();
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return stream;
	}

	private String buildLoginUrl() {
		return Constants.authUrl + this.username + "&j_password=" + this.password + "&btnSubmit=Login";
	}
}
