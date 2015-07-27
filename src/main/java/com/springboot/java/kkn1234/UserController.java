package com.springboot.java.kkn1234;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController 
{
	@RequestMapping(value="/kkn1234", method=RequestMethod.GET)
	public String localForm(Model model)
	{
		model.addAttribute("user", new User());
		return "localregister";

	}

	@RequestMapping(value="/kkn1234/create", method=RequestMethod.POST)
	public String formSubmit(@ModelAttribute User user, Model model) throws MalformedURLException, IOException
	{
		model.addAttribute("user", user);
		HttpPost post = new HttpPost("http://ec2-52-4-138-196.compute-1.amazonaws.com/magento/index.php/customer/account/createpost/");
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore)
				.build();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("firstname",
				user.getFirstName()));
		nameValuePairs.add(new BasicNameValuePair("lastname",
				user.getLastName()));
		nameValuePairs.add(new BasicNameValuePair("email",
				user.getEmail()));
		nameValuePairs.add(new BasicNameValuePair("password",
				user.getPassword()));
		nameValuePairs.add(new BasicNameValuePair("confirmation",
				user.getConfirmation()));

		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = httpclient.execute(post);
		response = httpclient.execute(post);
		System.out.println("Status code is "+response.getStatusLine().getStatusCode());
		System.out.println(response.toString());
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(response.getFirstHeader("Location"));
		HttpEntity entity = response.getEntity();
		EntityUtils.consume(entity);
		EntityUtils.consume(response.getEntity());
		
		/*File newTextFile = new File("C:\\Users\\Kris\\Desktop\\temp.html");
		FileWriter fileWriter = new FileWriter(newTextFile);
		fileWriter.write(response.toString());
		fileWriter.close();*/
		return "result";
	}
}
