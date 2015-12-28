package ru.family.core.model.family.impl;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.family.core.model.family.CommonFamily;
import ru.family.core.model.family.FamilyMember;
import ru.family.core.model.Picture;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Dmitriy Rakov
 * Date: 20.03.12
 */
@Document(collection = "family")
public class MongoSpecificFamily extends CommonFamily
{
    @Transient
    private Map<Long, FamilyMember> members = new HashMap<Long, FamilyMember>();

    public MongoSpecificFamily()
    {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public MongoSpecificFamily(Long id, String name, String description, Picture picture)
    {
        super(id, name, description, picture);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public Collection<FamilyMember> getMembers()
    {
        return members.values();
    }

    public FamilyMember getFamilyMember(Long id)
    {
        return members.get(id);
    }

    public void setMembers(List<FamilyMember> members)
    {
        for (FamilyMember member : members)
            this.members.put(member.getId(), member);
    }
}
