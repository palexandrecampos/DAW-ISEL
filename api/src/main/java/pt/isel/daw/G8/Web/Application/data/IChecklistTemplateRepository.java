package pt.isel.daw.G8.Web.Application.data;

import org.springframework.data.repository.CrudRepository;
import pt.isel.daw.G8.Web.Application.model.ChecklistTemplate;

public interface IChecklistTemplateRepository extends CrudRepository<ChecklistTemplate, Integer> {
    ChecklistTemplate findTemplateByTemplateId(Integer templateId);
}
