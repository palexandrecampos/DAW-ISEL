package pt.isel.daw.G8.Web.Application.mapper.outputMapper;

import pt.isel.daw.G8.Web.Application.model.*;
import pt.isel.daw.G8.Web.Application.outputModel.collections.ChecklistSirenCollection;
import pt.isel.daw.G8.Web.Application.outputModel.collections.TemplateItemSirenCollection;
import pt.isel.daw.G8.Web.Application.outputModel.singles.*;

public class SingleOutputMapper {

    public CostumerSiren costumerSirenMapper(Costumer c){
        return new CostumerSiren(
                c.getSub()
        );
    }

    public ChecklistSiren checklistSirenMapper(Checklist list){
        Integer templateId = list.getChecklistTemplate() == null ? null : list.getChecklistTemplate().getTemplateId();
        return new ChecklistSiren(
                list.getChecklistId(),
                list.getChecklistName(),
                list.getChecklistCompletionDate(),
                templateId,
                list.getOwner().getSub()
        );
    }

    public TemplateSiren templateSirenMapper(ChecklistTemplate template) {
        return new TemplateSiren(
                template.getTemplateId(),
                template.getChecklistTemplateName(),
                template.getTemplateDescription(),
                template.getOwner().getSub(),
                new CostumerSiren(),
                new ChecklistSirenCollection(),
                new TemplateItemSirenCollection()
        );
    }

    public ChecklistItemSiren checklistItemSirenMapper(ChecklistItem item){
        return new ChecklistItemSiren(
                item.getChecklist().getChecklistId(),
                item.getItemId(),
                item.getItemName(),
                item.getItemState(),
                item.getItemDescription()
        );
    }

    public TemplateItemSiren templateItemSirenMapper(TemplateItem item){
        return new TemplateItemSiren(
                item.getTemplate().getTemplateId(),
                item.getTemplateItemId(),
                item.getTemplateItemName(),
                item.getTemplateItemState(),
                item.getTemplateItemDescription()
        );
    }
}
