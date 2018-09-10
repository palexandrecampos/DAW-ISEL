package pt.isel.daw.G8.Web.Application.outputModel.collections;

import com.google.code.siren4j.annotations.Siren4JEntity;
import com.google.code.siren4j.resource.CollectionResource;
import pt.isel.daw.G8.Web.Application.outputModel.singles.TemplateItemSiren;

import java.util.Collection;

@Siren4JEntity(name = "templateItems", suppressClassProperty = true)
public class TemplateItemSirenCollection extends CollectionResource<TemplateItemSiren> {

    public TemplateItemSirenCollection() {
    }

    public TemplateItemSirenCollection(Collection<TemplateItemSiren> items) {
        this.addAll(items);
    }
}
