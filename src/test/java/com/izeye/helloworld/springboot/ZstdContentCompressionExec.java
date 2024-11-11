package com.izeye.helloworld.springboot;

import org.apache.hc.client5.http.classic.ExecChain;
import org.apache.hc.client5.http.classic.ExecChainHandler;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.DecompressingEntity;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HeaderElement;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.message.BasicHeaderValueParser;
import org.apache.hc.core5.http.message.ParserCursor;

import java.io.IOException;
import java.util.Locale;

/**
 * {@link ExecChainHandler} for handling zstd {@literal Content-Encoding}.
 *
 * This has been copied from {@link org.apache.hc.client5.http.impl.classic.ContentCompressionExec} and modified for zstd.
 *
 * @author Johnny Lim
 */
public class ZstdContentCompressionExec implements ExecChainHandler {

    @Override
    public ClassicHttpResponse execute(ClassicHttpRequest request, ExecChain.Scope scope, ExecChain chain) throws IOException, HttpException {
        HttpClientContext clientContext = scope.clientContext;
        RequestConfig requestConfig = clientContext.getRequestConfigOrDefault();

        ClassicHttpResponse response = chain.proceed(request, scope);

        HttpEntity entity = response.getEntity();
        // entity can be null in case of 304 Not Modified, 204 No Content or similar
        // check for zero length entity.
        if (requestConfig.isContentCompressionEnabled() && entity != null && entity.getContentLength() != 0) {
            String contentEncoding = entity.getContentEncoding();
            if (contentEncoding != null) {
                ParserCursor cursor = new ParserCursor(0, contentEncoding.length());
                HeaderElement[] codecs = BasicHeaderValueParser.INSTANCE.parseElements(contentEncoding, cursor);
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
        return response;
    }

}
