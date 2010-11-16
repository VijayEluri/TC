/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.idgenerator;

import com.topcoder.util.idgenerator.ejb.IDGeneratorHome;

import junit.framework.TestCase;

import java.util.Hashtable;
import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;


/**
 * <p>
 * Component demonstration for IDGenerator 3.0.
 * </p>
 *
 * <p>
 * This demo is separated into two parts.
 * </p>
 *
 * <p>
 * The first part demonstrates how to generator id from IDGeneratorFactory.
 * </p>
 *
 * <p>
 * The second part demonstrates how to how to generator id from EJB.
 * </p>
 *
 *
 * @author gua
 * @version 3.0
 */
public class DemoTest extends TestCase {
    /** The id sequence name for testing. */
    private static final String TEST_ID_NAME = "unit_test_id_sequence";

    /** The id sequence name for testing. */
    private static final String TEST_ORACLE_ID_NAME = "ID_SEQUENCE";

    /**
     * The main test of Demo, demonstrate how to generator id from IDGeneratorFactory.
     *
     * @throws Exception to JUnit.
     */
    public void testIDGeneratorFactory() throws Exception {
        IDGenerator sqlGenerator = IDGeneratorFactory.getIDGenerator(TEST_ID_NAME);
        long nextID = sqlGenerator.getNextID();
        assertEquals("Failed to get next id.", ++nextID, sqlGenerator.getNextID());

        // get bigId
        assertEquals("Failed to get next big id.", ++nextID, sqlGenerator.getNextBigID().longValue());

        // Ask for a specific implementation
        IDGenerator oracleGenerator = IDGeneratorFactory.getIDGenerator(TEST_ORACLE_ID_NAME,
                OracleSequenceGenerator.class.getName());

        // getId from oracle sequence
        long nextID2 = oracleGenerator.getNextBigID().longValue();
        assertEquals("Failed to get next id.", ++nextID2, oracleGenerator.getNextID());
        assertEquals("Failed to get next big id.", ++nextID2, oracleGenerator.getNextBigID().longValue());
    }

    /**
     * Demonstrate how to generator id from EJB.
     *
     * @throws Exception to JUnit
     */
    public void testIDGeneratorEjb() throws Exception {
        Hashtable props = new Hashtable();

        props.put(InitialContext.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
        props.put(InitialContext.PROVIDER_URL, "jnp://localhost:1099");
        props.put("java.naming.factory.url.pkgs", "org.jboss.naming.client");

        InitialContext ctx = new InitialContext(props);

        // note: home interface JNDI name may differ depending on deployment
        // Retrieve from remote
        Object rawMyGeneratorHome = ctx.lookup(TestIDGeneratorBean.idGeneratorHome);
        IDGeneratorHome myGeneratorHome = (IDGeneratorHome) PortableRemoteObject.narrow(rawMyGeneratorHome,
                IDGeneratorHome.class);

        com.topcoder.util.idgenerator.ejb.IDGenerator myGenerator = myGeneratorHome.create();
        System.out.println("Get id with ejb:" + myGenerator.getNextID(TEST_ID_NAME));

        // note: home interface JNDI name may differ depending on deployment
        // Retrieve from local
        myGeneratorHome = (IDGeneratorHome) ctx.lookup(TestIDGeneratorBean.idGeneratorHome);

        myGenerator = myGeneratorHome.create();
        System.out.println("Get id with ejb:" + myGenerator.getNextID(TEST_ID_NAME));
    }
}