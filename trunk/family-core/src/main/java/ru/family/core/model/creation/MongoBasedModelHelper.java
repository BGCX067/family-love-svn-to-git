package ru.family.core.model.creation;

import ru.family.core.model.family.Family;
import ru.family.core.model.Picture;
import ru.family.core.model.family.impl.MongoSpecificFamily;

/**
 * Author: Dmitriy Rakov
 * Date: 20.03.12
 */
public class MongoBasedModelHelper extends CommonModelFactory
{
    public Family createFamily(String name, String description, Picture picture)
    {
        return new MongoSpecificFamily(id(), name, description, picture);
    }
}
