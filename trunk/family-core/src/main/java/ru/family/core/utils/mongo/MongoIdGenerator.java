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
package ru.family.core.utils.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;
import ru.family.core.utils.IdGeneratorBulk;

import java.util.HashSet;
import java.util.Set;

/**
 * @author DMRA0509
 * @version 1.0 3/19/12
 */
public class MongoIdGenerator extends IdGeneratorBulk
{
    private MongoTemplate mongoTemplate;

    public MongoIdGenerator(MongoTemplate mongoTemplate, int idsCount)
    {
        super(idsCount);

        this.mongoTemplate = mongoTemplate;

        initSequence();
    }

    private void initSequence()
    {
        SequenceId sequenceId = mongoTemplate.findById(SequenceId.SEQUENCE_ID, SequenceId.class);

        if (sequenceId == null)
            sequenceId = new SequenceId(1L);

        mongoTemplate.save(sequenceId);
    }

    @Override
    protected Set<Long> nextIds(int idsCount)
    {
        SequenceId sequenceId = mongoTemplate.findById(SequenceId.SEQUENCE_ID, SequenceId.class);

        Long startId = sequenceId.getCurrentId();

        sequenceId.increment(idsCount);

        mongoTemplate.save(sequenceId);

        return generateIdsList(startId, sequenceId.getCurrentId());
    }

    private static Set<Long> generateIdsList(Long start, Long end)
    {
        Set<Long> ids = new HashSet<Long>();

        for (long i = start; i < end; i++)
            ids.add(i);

        return ids;
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