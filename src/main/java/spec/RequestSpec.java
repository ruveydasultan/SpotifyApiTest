package spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {
    RequestSpecification requestSpecification;

    public RequestSpec(String baseUrl) {

       requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .build();

    }


    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }
}
