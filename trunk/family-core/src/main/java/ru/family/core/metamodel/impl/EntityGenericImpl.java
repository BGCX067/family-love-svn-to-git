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
package ru.family.core.metamodel.impl;

import ru.family.core.metamodel.EntityMetadata;
import ru.family.core.metamodel.Generic;
import ru.family.core.metamodel.GenericData;
import ru.family.core.orm.Entity;

import java.util.Map;
import java.util.Set;

/**
 * @author DMRA0509
 * @version 1.0 3/13/12
 */
public class EntityGenericImpl<T> implements Generic<T>
{
    private EntityMetadata entityMetadata;
    private T entityObject;

    public EntityGenericImpl(EntityMetadata entityMetadata, T entityObject)
    {
        this.entityMetadata = entityMetadata;
        this.entityObject = entityObject;
    }

    public void setValue(String field, Object value) throws Exception
    {
        if (!entityMetadata.hasSet(field))
            throw new NoSuchMethodException("Object: " + entityObject.getClass().getName() + " has not setter for field: " + field);

        entityMetadata.methodSet(field).invoke(entityObject, value);
    }

    public Object getValue(String field) throws Exception
    {
        if (!entityMetadata.hasGet(field))
            throw new NoSuchMethodException("Object: " + entityObject.getClass().getName() + " has not getter for field: " + field);

        return entityMetadata.methodGet(field).invoke(entityObject);
    }

    public Set<String> getAllFields()
    {
        return entityMetadata.fieldNames();
    }

    @Override
    public void setValues(GenericData data) throws Exception
    {
        setValues(data.getValues());
    }

    @Override
    public void setValues(Map<String, Object> data) throws Exception
    {
        for (Map.Entry<String, Object> value : data.entrySet())
            setValue(value.getKey(), value.getValue());
    }

    @Override
    public GenericData getValues() throws Exception
    {
        GenericData data = new MapBasedGenericData();

        for (String field : entityMetadata.fieldNames())
            data.getValues().put(field, getValue(field));

        return data;
    }

    public T getSource()
    {
        return entityObject;
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