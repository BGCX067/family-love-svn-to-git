/*
 This software is the confidential information and copyrighted work of
 NetCracker Technology Corp. ("NetCracker") and/or its suppliers and
 is only distributed under the terms of a separate license agreement
 with NetCracker.
 Use of the software is governed by the terms of the license agreement.
 Any use of this software not in accordance with the license agreement
 is expressly prohibited by law, and may result in severe civil
 and criminal penalties. 
 
 Copyright (c) 1995-2010 NetCracker Technology Corp.
 
 All Rights Reserved.
 
*/
package ru.family.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.family.core.metamodel.Generic;
import ru.family.core.metamodel.MetamodelManager;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author DMRA0509
 * @version 1.0 3/15/12
 */
public class AbstractTest
{
    protected ApplicationContext applicationContext;

  //  protected MetamodelManager metamodelManager;

    protected AbstractTest()
    {
        applicationContext = new ClassPathXmlApplicationContext("classpath*:app-config.xml");
    //    metamodelManager = applicationContext.getBean("metamodelManager", MetamodelManager.class);
    }



    private static void assertValues(Object expected, Object actual)
    {
        if (expected == null)
            assertNull(actual);
        else
        {
            assertNotNull(actual);
            assertNotNullValues(expected, actual);
        }
    }

    private static void assertNotNullValues(Object expected, Object actual)
    {
        assertEquals(expected.getClass(), actual.getClass());

        if (expected instanceof Map)
            assertMaps((Map) expected, (Map) actual);
        else if (expected instanceof Collection)
            assertCollections((Collection) expected, (Collection) actual);
        else
            assertEquals(expected, actual);
    }

    private static void assertMaps(Map expected, Map actual)
    {
        assertEquals(expected.size(), actual.size());

        Iterator<Map.Entry> expectedIt = expected.entrySet().iterator();
        Iterator<Map.Entry> actualIt = actual.entrySet().iterator();

        for (; expectedIt.hasNext(); )
        {
            assertEquals(expectedIt.next().getKey(), actualIt.next().getKey());
            assertEquals(expectedIt.next().getValue(), actualIt.next().getValue());
        }
    }

    private static void assertCollections(Collection expected, Collection actual)
    {
        assertEquals(expected.size(), actual.size());

        Iterator expectedIt = expected.iterator();
        Iterator actualIt = actual.iterator();

        for (; expectedIt.hasNext(); )
        {
            assertEquals(expectedIt.next(), actualIt.next());
        }
    }
}
/*
 WITHOUT LIMITING THE FOREGOING, COPYING, REPRODUCTION, REDISTRIBUTION,
 REVERSE ENGINEERING, DISASSEMBLY, DECOMPILATION OR MODIFICATION
 OF THE SOFTWARE IS EXPRESSLY PROHIBITED, UNLESS SUCH COPYING,
 REPRODUCTION, REDISTRIBUTION, REVERSE ENGINEERING, DISASSEMBLY,
 DECOMPILATION OR MODIFICATION IS EXPRESSLY PERMITTED BY THE LICENSE
 AGREEMENT WITH NETCRACKER. 
 
 THIS SOFTWARE IS WARRANTED, IF AT ALL, ONLY AS EXPRESSLY PROVIDED IN
 THE TERMS OF THE LICENSE AGREEMENT, EXCEPT AS WARRANTED IN THE
 LICENSE AGREEMENT, NETCRACKER HEREBY DISCLAIMS ALL WARRANTIES AND
 CONDITIONS WITH REGARD TO THE SOFTWARE, WHETHER EXPRESS, IMPLIED
 OR STATUTORY, INCLUDING WITHOUT LIMITATION ALL WARRANTIES AND
 CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE,
 TITLE AND NON-INFRINGEMENT.
 
 Copyright (c) 1995-2010 NetCracker Technology Corp.
 
 All Rights Reserved.
*/