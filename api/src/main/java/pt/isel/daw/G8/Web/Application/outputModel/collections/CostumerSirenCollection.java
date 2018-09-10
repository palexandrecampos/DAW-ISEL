package pt.isel.daw.G8.Web.Application.outputModel.collections;


import com.google.code.siren4j.annotations.Siren4JEntity;
import com.google.code.siren4j.resource.CollectionResource;
import pt.isel.daw.G8.Web.Application.outputModel.singles.CostumerSiren;

import java.util.Collection;

@Siren4JEntity(name = "users", suppressClassProperty = true)
public class CostumerSirenCollection extends CollectionResource<CostumerSiren> {

    public CostumerSirenCollection() {
    }

    public CostumerSirenCollection(Collection<CostumerSiren> users) {
        this.addAll(users);
    }
}
