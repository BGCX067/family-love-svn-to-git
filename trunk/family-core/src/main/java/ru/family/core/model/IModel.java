package ru.family.core.model;

/**
 * Author: Dmitriy Rakov
 * Date: 21.03.12
 */
public interface IModel extends IdHolder<Long>
{
    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public Picture getPicture();

    public void setPicture(Picture picture);

    public void setParams(BaseModelParams params);
}
