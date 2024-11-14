package com.izeye.helloworld.springboot;

import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.util.unit.DataSize;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Tomcat configuration.
 *
 * @author Johnny Lim
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TomcatConfigTests {

    @Autowired
    TomcatServletWebServerFactory factory;

    @Autowired
    WebServerApplicationContext context;

    @Test
    void maxHttpHeaderSizeShouldBeSet() {
        long expectedMaxHttpHeaderSize = DataSize.ofKilobytes(80).toBytes();

        TomcatWebServer server = (TomcatWebServer) this.context.getWebServer();
        Tomcat tomcat = server.getTomcat();
        AbstractHttp11Protocol protocolHandler = (AbstractHttp11Protocol) tomcat.getConnector().getProtocolHandler();

        assertThat(protocolHandler.getMaxHttpHeaderSize()).isEqualTo(expectedMaxHttpHeaderSize);
        assertThat(protocolHandler.getMaxHttpRequestHeaderSize()).isEqualTo(expectedMaxHttpHeaderSize);
        assertThat(protocolHandler.getMaxHttpResponseHeaderSize()).isEqualTo(expectedMaxHttpHeaderSize);
    }

}
