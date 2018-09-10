package pt.isel.daw.G8.Web.Application.outputModel.collections;

import com.google.code.siren4j.annotations.Siren4JEntity;
import com.google.code.siren4j.resource.CollectionResource;
import pt.isel.daw.G8.Web.Application.outputModel.singles.TemplateSiren;

import java.util.Collection;

@Siren4JEntity(name = "templates", suppressClassProperty = true)
public class TemplateSirenCollection extends CollectionResource<TemplateSiren> {

    public TemplateSirenCollection() {
    }

    public TemplateSirenCollection (Collection<TemplateSiren> templates){
        this.addAll(templates);
    }
}
