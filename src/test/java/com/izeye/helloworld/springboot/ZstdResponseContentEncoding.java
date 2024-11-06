package com.izeye.helloworld.springboot;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.DecompressingEntity;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.Locale;

/**
 * {@link HttpResponseInterceptor} for handling zstd {@literal Content-Encoding}.
 *
 * This has been copied from {@link org.apache.http.client.protocol.ResponseContentEncoding} and modified for zstd.
 *
 * @author Johnny Lim
 */
public class ZstdResponseContentEncoding implements HttpResponseInterceptor {

    @Override
    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
        HttpEntity entity = response.getEntity();

        HttpClientContext clientContext = HttpClientContext.adapt(context);
        RequestConfig requestConfig = clientContext.getRequestConfig();
        // entity can be null in case of 304 Not Modified, 204 No Content or similar
        // check for zero length entity.
        if (requestConfig.isContentCompressionEnabled() && entity != null && entity.getContentLength() != 0) {
            Header ceheader = entity.getContentEncoding();
            if (ceheader != null) {
                HeaderElement[] codecs = ceheader.getElements();
                for (HeaderElement codec : codecs) {
                    String codecname = codec.getName().toLowerCase(Locale.ROOT);
                    if (codecname.equals("zstd")) {
                        response.setEntity(new DecompressingEntity(response.getEntity(),
                                ZstdInputStreamFactory.getInstance()));
                        response.removeHeaders("Content-Length");
                        response.removeHeaders("Content-Encoding");
                        response.removeHeaders("Content-MD5");
                        break;
                    }
                }
            }
        }
    }

}
