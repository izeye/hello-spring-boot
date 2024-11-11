package com.izeye.helloworld.springboot;

import com.github.luben.zstd.ZstdInputStream;
import org.apache.hc.client5.http.entity.InputStreamFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * {@link InputStreamFactory} for zstd.
 *
 * @author Johnny Lim
 */
public class ZstdInputStreamFactory implements InputStreamFactory {

    /**
     * Singleton instance.
     */
    private static final ZstdInputStreamFactory INSTANCE = new ZstdInputStreamFactory();

    /**
     * Gets the singleton instance.
     *
     * @return the singleton instance.
     */
    public static ZstdInputStreamFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public InputStream create(final InputStream inputStream) throws IOException {
        return new ZstdInputStream(inputStream);
    }

}
