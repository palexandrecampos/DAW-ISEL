package pt.isel.daw.G8.Web.Application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.daw.G8.Web.Application.data.ICostumerRepository;
import pt.isel.daw.G8.Web.Application.model.*;

import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    private ICostumerRepository costumerRepository;

    Optional<Checklist> getCostumerChecklist(String sub, Integer checklistId) {
        Costumer costumer = costumerRepository.findCostumerBySub(sub);
        return costumer.getChecklists().stream().filter(list ->
                list.getChecklistId().equals(checklistId)).findFirst();
    }

    Optional<ChecklistItem> getItemFromChecklist(Checklist checklist, Integer itemId) {
        return checklist.getItems().stream().filter(item ->
                item.getItemId().equals(itemId)).findFirst();
    }

    Optional<ChecklistTemplate> getCostumerTemplate(String sub, Integer templateId) {
        Costumer costumer = costumerRepository.findCostumerBySub(sub);
        return costumer.getChecklistTemplates().stream().filter(template ->
                template.getTemplateId().equals(templateId)).findFirst();
    }

    Optional<TemplateItem> getItemFromTemplate(ChecklistTemplate checklistTemplate, Integer templateItemId) {
        return checklistTemplate.getTemplateItems().stream().filter(item ->
                item.getTemplateItemId().equals(templateItemId)).findFirst();
    }
}
