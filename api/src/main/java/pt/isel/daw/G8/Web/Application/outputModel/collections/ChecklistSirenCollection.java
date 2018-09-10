package pt.isel.daw.G8.Web.Application.outputModel.collections;

import com.google.code.siren4j.annotations.Siren4JEntity;
import com.google.code.siren4j.resource.CollectionResource;
import pt.isel.daw.G8.Web.Application.outputModel.singles.ChecklistSiren;

import java.util.Collection;

@Siren4JEntity(name = "checklists", suppressClassProperty = true)
public class ChecklistSirenCollection extends CollectionResource<ChecklistSiren> {

    public ChecklistSirenCollection() {
    }

    public ChecklistSirenCollection(Collection<ChecklistSiren> checklists) {
        this.addAll(checklists);
    }
}
