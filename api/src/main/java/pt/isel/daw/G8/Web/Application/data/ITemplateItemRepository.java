package pt.isel.daw.G8.Web.Application.data;

import org.springframework.data.repository.CrudRepository;
import pt.isel.daw.G8.Web.Application.model.TemplateItem;

public interface ITemplateItemRepository extends CrudRepository<TemplateItem, Integer> {
    TemplateItem findItemByTemplateItemId(Integer templateItemId);
}
