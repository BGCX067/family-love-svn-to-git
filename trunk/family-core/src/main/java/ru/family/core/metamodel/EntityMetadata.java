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
import java.util.*;

/**
 * @author DMRA0509
 * @version 1.0 3/13/12
 */
public class EntityMetadata
{
    private static final Set<String> excludedFields = new HashSet<String>()
    {
        {
            add("id");
        }
    };

    private Class clazz;
    private final Map<String, FieldMethod> fieldMethods = new HashMap<String, FieldMethod>();

    public EntityMetadata(Class clazz)
    {
        this.clazz = clazz;

        initFieldMethods();
    }

    private void initFieldMethods()
    {
        List<Field> fields = collectWithHierarchy(clazz);

        for (Field field : fields)
            addFieldMethod(field);

    }

    private void addFieldMethod(Field field)
    {
        FieldMethod fieldMethod = new FieldMethod(field);
        fieldMethods.put(field.getName(), fieldMethod);
    }

    private static List<Field> collectWithHierarchy(Class clazz)
    {
        List<Field> fields = fieldsList(clazz);

        Class superClass = clazz.getSuperclass();

        if (superClass != null && !superClass.equals(Object.class))
            fields.addAll(collectWithHierarchy(clazz.getSuperclass()));

        return fields;
    }

    private static List<Field> fieldsList(Class clazz)
    {
        return new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));
    }


    //todo remove hack
    public Set<String> fieldNames()
    {
        Set<String> keys = fieldMethods.keySet();

        keys.removeAll(excludedFields);

        return keys;
    }

    public Class getClazz()
    {
        return clazz;
    }

    public Method methodSet(String field)
    {
        return fieldMethods.get(field).methodSet();
    }

    public Method methodGet(String field)
    {
        return fieldMethods.get(field).methodGet();
    }

    public boolean hasGet(String field)
    {
        boolean hasField = fieldMethods.containsKey(field);

        return hasField && fieldMethods.get(field).hasGet();
    }

    public boolean hasSet(String field)
    {
        boolean hasField = fieldMethods.containsKey(field);

        return hasField && fieldMethods.get(field).hasSet();
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