package ru.family.core.model;

import java.io.Serializable;

/**
 * Author: Dmitriy Rakov
 * Date: 23.03.12
 */
public class BaseModelParams implements Serializable
{
    private final String name;
    private final String desc;
    private final Picture picture;

    public BaseModelParams(String name, String desc, Picture picture)
    {
        this.name = name;
        this.desc = desc;
        this.picture = picture;
    }

    public String getName()
    {
        return name;
    }

    public String getDesc()
    {
        return desc;
    }

    public Picture getPicture()
    {
        return picture;
    }
}
