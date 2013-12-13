package operations;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

import devices.RaspberryPi;

public class GetCityOp extends Operation {

	/**
	 * Constructor
	 */
	public GetCityOp() {
		super("GetCityOp", new QName("BasicServices", RaspberryPi.namespace));

		// create new Element called "name" (just a simple one in this case)
		Element nameElem = new Element(new QName("getLoc", RaspberryPi.namespace), SchemaUtil.TYPE_STRING);

		// set the input of the operation
		setInput(nameElem);

		// create new element called "reply"
		Element reply = new Element(new QName("loc", RaspberryPi.namespace), SchemaUtil.TYPE_STRING);

		// set this element as output
		setOutput(reply);
	}

	public ParameterValue invokeImpl(ParameterValue parameterValue, CredentialInfo credentialInfo) throws InvocationException, CommunicationException {

		// get string value from input
		String par = ParameterValueManagement.getString(parameterValue, "name");
		System.out.println("Someone is saying " + par);
		String mode = "ip-city";
		
        String apiKey = "e338ef9cea525a48c848246d3c6716b216a2c66e3518c90e6bc8484d3dc6b4fa";

        String url = "http://api.ipinfodb.com/v3/" + mode + "/?format=json&key=" + apiKey;
        String loc = "";
        
        try {
            HttpGet request = new HttpGet(url);
            HttpResponse response = HTTP_CLIENT.execute(request, new BasicHttpContext());
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("IpInfoDb response is " + response.getStatusLine());
            }

            String responseBody = EntityUtils.toString(response.getEntity());
            IpCityResponse ipCityResponse = MAPPER.readValue(responseBody, IpCityResponse.class);
            if ("OK".equals(ipCityResponse.getStatusCode())) {
                loc = ipCityResponse.getCountryCode() + ", " + ipCityResponse.getRegionName() + ", " + ipCityResponse.getCityName();
            	System.out.println(loc);
                
                //System.out.println(ipCityResponse.toString());
            } else {
                System.out.println("API status message is '" + ipCityResponse.getStatusMessage() + "'");
            }
        } catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            HTTP_CLIENT.getConnectionManager().shutdown();
        }
    
		// create output and set value
		ParameterValue result = createOutputValue();
		ParameterValueManagement.setString(result, "reply", loc);

		return result;
	}
	
	private static final HttpClient HTTP_CLIENT = new DefaultHttpClient();
    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        // Add a handler to handle unknown properties (in case the API adds new properties to the response)
        MAPPER.addHandler(new DeserializationProblemHandler() {
            @Override
            public boolean handleUnknownProperty(DeserializationContext context, JsonParser jp, JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException {
                // Do not fail - just log
                String className = (beanOrClass instanceof Class) ? ((Class) beanOrClass).getName() : beanOrClass.getClass().getName();
                System.out.println("Unknown property while de-serializing: " + className + "." + propertyName);
                context.getParser().skipChildren();
                return true;
            }
        });
    }
    

    public static class IpCityResponse {

        private String statusCode;
        private String statusMessage;
        private String ipAddress;
        private String countryCode;
        private String countryName;
        private String regionName;
        private String cityName;
        private String zipCode;
        private String latitude;
        private String longitude;
        private String timeZone;

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getStatusMessage() {
            return statusMessage;
        }

        public void setStatusMessage(String statusMessage) {
            this.statusMessage = statusMessage;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        @Override
        public String toString() {
            return "IpCityResponse{" +
                    "statusCode='" + statusCode + '\'' +
                    ", statusMessage='" + statusMessage + '\'' +
                    ", ipAddress='" + ipAddress + '\'' +
                    ", countryCode='" + countryCode + '\'' +
                    ", countryName='" + countryName + '\'' +
                    ", regionName='" + regionName + '\'' +
                    ", cityName='" + cityName + '\'' +
                    ", zipCode='" + zipCode + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", timeZone='" + timeZone + '\'' +
                    '}';
        }
    }
}
