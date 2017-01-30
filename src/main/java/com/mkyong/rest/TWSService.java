package com.mkyong.rest;
 
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("cut")
public class TWSService {
 
	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public String cut(@PathParam("param") String msg) {
		String output;
		Segmenter segmenter = null;
		try {
			segmenter = new Segmenter();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(segmenter != null){
			output = segmenter.cut(msg);
		} else {
			output = "null";
		}

 
		return output;
		//return Response.status(200).entity(output).build();
 
	}
 
}