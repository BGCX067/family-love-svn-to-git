package ru.family.core.orm;

import ru.family.core.model.IdHolder;

import java.io.Serializable;

public interface Entity extends Serializable, IdHolder<Long>
{
}
