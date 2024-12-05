package com.izeye.helloworld.springboot;

import org.springframework.stereotype.Service;

/**
 * Default {@link TestService}.
 *
 * @author Johnny Lim
 */
@Service
public class DefaultTestService implements TestService {

    @Override
    public void test() {
        System.out.println("In test(): " + Thread.currentThread());
    }

    @Override
    public void testAnother() {
        System.out.println("In testAnother(): " + Thread.currentThread());
    }

}
