package pt.isel.daw.G8.Web.Application.data;

import org.springframework.data.repository.CrudRepository;
import pt.isel.daw.G8.Web.Application.model.ChecklistItem;

public interface IChecklistItemRepository extends CrudRepository<ChecklistItem, Integer> {
    ChecklistItem findItemByItemId(Integer itemId);
}
