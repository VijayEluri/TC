/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.objectfactory;

import junit.framework.TestCase;


/**
 * Unit tests for UnknownReferenceException.
 *
 * @author mgmg
 * @version 2.0
 */
public class UnknownReferenceExceptionUnitTest extends TestCase {
    /**
     * Test the constructor with one message parameter.
     * The object should be created correctly.
     */
    public void testConstructor1() {
        UnknownReferenceException e = new UnknownReferenceException("message");

        assertNotNull("The object should not be null", e);
    }

    /**
     * Test the constructor with two parameters.
     * The object should be created correctly.
     */
    public void testConstructor2() {
        UnknownReferenceException e = new UnknownReferenceException("message", new Exception());

        assertNotNull("The object should not be null", e);
    }
}