package ru.family.core.service.transferring;

import ru.family.core.metamodel.Generic;
import ru.family.core.orm.Entity;

public interface ServiceObject<T extends Entity> extends Generic<T>
{
    public Long getId();
}
