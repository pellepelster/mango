import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.Element;

@Endpoint
public class AccountServiceEndpoint {

	private static final String TARGET_NAMESPACE = "http://mangodemo/entity1";

	@PayloadRoot(localPart = "AccountDetailsRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload Element getAccountDetails(@RequestPayload Element element) {
		return null;
	}

}