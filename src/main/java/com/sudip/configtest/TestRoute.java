package com.sudip.configtest;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
class TestRoute extends RouteBuilder {

    @Override
    public void configure() {

        restConfiguration()
                .enableCORS(true)
                .apiProperty("cors", "true") // cross-site
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");

        rest()
                .get("/")
                .to("direct:runningStatus")
        ;
        from("direct:runningStatus")
                .transform().simple("Service is running")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .endRest()
        ;

    }

}
