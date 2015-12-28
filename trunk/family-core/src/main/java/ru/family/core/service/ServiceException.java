package ru.family.core.service;

/**
 * Author: Dmitriy Rakov
 * Date: 22.03.12
 */
public class ServiceException extends Exception
{
    private String businessErrorMessage;

    public ServiceException(String businessErrorMessage)
    {
        this.businessErrorMessage = businessErrorMessage;
    }

    public String getBusinessErrorMessage()
    {
        return businessErrorMessage;
    }
}
