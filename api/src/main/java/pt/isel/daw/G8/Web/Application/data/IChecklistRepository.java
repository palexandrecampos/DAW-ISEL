package pt.isel.daw.G8.Web.Application.data;

import org.springframework.data.repository.CrudRepository;
import pt.isel.daw.G8.Web.Application.model.Checklist;
import pt.isel.daw.G8.Web.Application.model.Costumer;

public interface IChecklistRepository extends CrudRepository<Checklist, Integer> {
    Checklist findChecklistByChecklistId(Integer checklistId);
}
