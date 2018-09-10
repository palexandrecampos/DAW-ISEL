package pt.isel.daw.G8.Web.Application.mapper;

import pt.isel.daw.G8.Web.Application.inputModel.*;
import pt.isel.daw.G8.Web.Application.model.*;

public class InputMapper {

    public Costumer mapCostumer(CostumerInputModel input){
        return new Costumer(
                input.sub,
                null,
                null
        );
    }

    public Checklist mapChecklist(ChecklistInputModel input, Costumer costumer, ChecklistTemplate template){
        return new Checklist(
                null,
                input.checklistName,
                input.completionDate,
                template,
                costumer,
                null
        );
    }

    public ChecklistTemplate mapTemplate(TemplateInputModel input, Costumer costumer){
        return new ChecklistTemplate(
                null,
                input.templateName,
                input.templateDescription,
                costumer,
                null,
                null
        );
    }

    public ChecklistItem mapChecklistItem(ChecklistItemInputModel input, Checklist checklist, StateEnum state){
        return new ChecklistItem(
                null,
                input.itemName,
                state,
                input.description,
                checklist
        );

    }

    public TemplateItem mapTemplateItem (TemplateItemInputModel input, StateEnum state, ChecklistTemplate template){
        return new TemplateItem(
                null,
                input.templateItemName,
                state,
                input.templateItemDescription,
                template
        );
    }
}
