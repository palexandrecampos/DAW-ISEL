package pt.isel.daw.G8.Web.Application.outputModel.collections;

         import com.google.code.siren4j.annotations.Siren4JEntity;
         import com.google.code.siren4j.resource.CollectionResource;
         import pt.isel.daw.G8.Web.Application.outputModel.singles.ChecklistItemSiren;

         import java.util.Collection;

@Siren4JEntity(name = "checklistItems", suppressClassProperty = true)
public class ChecklistItemSirenCollection extends CollectionResource<ChecklistItemSiren> {


    public ChecklistItemSirenCollection() {
    }

    public ChecklistItemSirenCollection(Collection<ChecklistItemSiren> items) {
        this.addAll(items);
    }
}
