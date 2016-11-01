import java.io.IOException;

import asg.cliche.*;
import okhttp3.*;
import org.json.*;

public class Client {
	
	String endpoint;
	OkHttpClient client;
	JSONObject json;
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	
	public Client(String endpoint){
		this.endpoint = endpoint;
		client = new OkHttpClient();
		json = new JSONObject();
	}
	
	@Command(name = "put")
	public void putEvent(String name, String date) throws IOException{
		
		String jsoninput = "{\"name\":\""+name+"\",\"date\":\""+date+"\"}";
		
		RequestBody body = RequestBody.create(JSON, jsoninput);
		Request request = new Request.Builder()
			.url(this.endpoint)
			.put(body)
			.build();
		
		Response response = client.newCall(request).execute();
		try{
			System.out.println(response.body().string());
		} catch(IOException e){
			
		}

	}
	
	@Command(name = "get")
	public void timeToDate(String eventName) throws IOException{
		
		String endpointhelp = this.endpoint+"/timeToDate?eventName="+eventName;
		
		Request request = new Request.Builder()
			.url(endpointhelp)
			.get()
			.build();
		
		Response response = client.newCall(request).execute();
		try{
			System.out.println(response.body().string());
		} catch(IOException e){
			
		}
	}

	public static void main(String[] args) throws IOException{
		
		String endpoint;
		
		if(args.length >= 1){
			endpoint = args[0];
		}
		else {
			endpoint = "http://api-server.eu-gb.mybluemix.net/api/Calendars";
			
		}
		ShellFactory.createConsoleShell("Type '?list' for all commands", "REST Client - Enterprise Computing", new Client(endpoint)).commandLoop();
	}
}
