package co.edu.pdam.eci.persistenceapiintegration.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Santiago Carrillo
 */

//TODO add database annotations and proper model structure
@DatabaseTable
public class Team  extends BaseEntity {
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "shortName")
    private String shortName;
    @DatabaseField(columnName = "imageUrl")
    private String imageUrl;

    public Team() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
