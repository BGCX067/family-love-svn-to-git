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
package ru.family.core.metamodel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author DMRA0509
 * @version 1.0 3/12/12
 */
public class FieldMethod
{
    private Method set;
    private Method get;

    private Class clazz;

    public FieldMethod(Field field)
    {
        this.clazz = field.getDeclaringClass();

        String setMethodName = methodSetName(field.getName());
        String getMethodName = methodGetName(field.getName());

        this.get = initMethod(getMethodName, null);
        this.set = initMethod(setMethodName, field.getType());

    }

    private Method initMethod(String methodName, Class paramType)
    {
        try
        {
            return paramType != null ? clazz.getMethod(methodName,paramType) : clazz.getMethod(methodName);
        }
        catch (NoSuchMethodException e)
        {
            return null;
        }
    }

    public Method methodSet()
    {
        return set;
    }

    public Method methodGet()
    {
        return get;
    }

    public boolean hasSet()
    {
        return hasMethod(set);
    }

    public boolean hasGet()
    {
        return hasMethod(get);
    }

    private static boolean hasMethod(Method method)
    {
        return method != null;
    }

    private static String methodSetName(String fieldName)
    {
        return methodName(fieldName, "set");
    }

    private static String methodGetName(String fieldName)
    {
        return methodName(fieldName, "get");
    }

    //todo refactor
    private static String methodName(String fieldName, String prefix)
    {
        char[] fieldNameArr = fieldName.toCharArray();
        fieldNameArr[0] = toUpperCase(fieldNameArr[0]);
        return prefix + new String(fieldNameArr);
    }

    private static char toUpperCase(Character c)
    {
        return c.toString().toUpperCase().toCharArray()[0];
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