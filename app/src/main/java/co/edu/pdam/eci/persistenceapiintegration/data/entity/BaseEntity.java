package co.edu.pdam.eci.persistenceapiintegration.data.entity;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * @author Santiago Carrillo
 */

public abstract class BaseEntity
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @DatabaseField( generatedId = true )
    Long id;

    public Long getId()
    {
        return id;
    }
}
