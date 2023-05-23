package com.example;

import com.salesforce.functions.jvm.sdk.Context;
import com.salesforce.functions.jvm.sdk.InvocationEvent;
import com.salesforce.functions.jvm.sdk.SalesforceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
/**
 * Describe CalloutFunction here.
 */
public class CalloutFunction implements SalesforceFunction<FunctionInput, FunctionOutput> {
  private static final Logger LOGGER = LoggerFactory.getLogger(CalloutFunction.class);
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

  @Override
	public FunctionOutput apply(InvocationEvent<FunctionInput> event, Context context) {
		try {

			String payload = event.getData().payload;
			String url = event.getData().url;
			String authCode = event.getData().authCode;

			//"{\"key1\": \"value1\", \"key2\": \"value2\"}";
            GenericUrl genericUrl = new GenericUrl(url);

			HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType("application/json");

            
            ByteArrayContent httpContent = ByteArrayContent.fromString(
                    headers.getContentType(),
                    payload
            );

            HttpRequest request = requestFactory.buildPostRequest(genericUrl, httpContent);
            request.setHeaders(headers);
			HttpResponse response = request.execute();

            // Handle the response as needed
            System.out.println("Response Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.parseAsString());

		} catch (Exception e) {
			// Handle any exceptions that occur during the callout
			e.printStackTrace();
		}

		return null;
	}
}
