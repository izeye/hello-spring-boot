package com.izeye.helloworld.springboot;

/**
 * Person.
 *
 * @param id ID
 * @param firstName first name
 * @param lastName last name
 * @author Johnny Lim
 */
public record Person(Long id, String firstName, String lastName) {
}
