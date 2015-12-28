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
package ru.family.core.modifications.request;

import org.springframework.data.mongodb.core.mapping.Document;
import ru.family.core.orm.Entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author DMRA0509
 * @version 1.0 3/23/12
 */
@Document(collection = "modificationRequest")
public class CommonModificationRequest implements ModificationRequest
{
    private Long id;
    private Long invokerId;
    private Set<Long> votes = new HashSet<Long>();
    private int requiredVotesCount;

    private ModificationContext modificationContext;

    public CommonModificationRequest()
    {
    }

    public CommonModificationRequest(Long id, Long invokerId, ModificationContext modificationContext, int requiredVotesCount)
    {
        this.id = id;
        this.invokerId = invokerId;
        this.modificationContext = modificationContext;
        this.requiredVotesCount = requiredVotesCount;

        votes.add(invokerId);
    }

    public Long getId()
    {
        return id;
    }

    public Long getInvokerId()
    {
        return invokerId;
    }

    public Set<Long> getVotes()
    {
        return votes;
    }


    public void vote(Long familyMemberId)
    {
        votes.add(familyMemberId);
    }

    @Override
    public int getRequiredVotesCount()
    {
        return requiredVotesCount;
    }

    @Override
    public ModificationContext getModificationContext()
    {
        return modificationContext;
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