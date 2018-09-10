package pt.isel.daw.G8.Web.Application.outputModel.singles;

import com.google.code.siren4j.annotations.*;
import com.google.code.siren4j.component.impl.ActionImpl.*;
import com.google.code.siren4j.resource.BaseResource;
import com.google.code.siren4j.resource.CollectionResource;

@Siren4JEntity(name = "template", suppressClassProperty = true,
        actions = {
                @Siren4JAction(
                        name = "createChecklistTemplate",
                        method = Method.POST,
                        href = "api/createChecklistTemplate",
                        type = "application/json",
                        fields = {
                                @Siren4JActionField(
                                        name = "templateName",
                                        type = "text",
                                        required = true
                                ),
                                @Siren4JActionField(
                                        name = "templateDescription",
                                        type = "text",
                                        required = true
                                ),
                                @Siren4JActionField(
                                        name = "owner",
                                        type = "text",
                                        required = true
                                )
                        }

                ),
                @Siren4JAction(
                        name = "createChecklist",
                        method = Method.POST,
                        href = "api/template/{templateId}/createChecklist",
                        type = "application/json",
                        fields = {
                                @Siren4JActionField(
                                        name = "checklistName",
                                        type = "text",
                                        required = true
                                ),
                                @Siren4JActionField(
                                        name = "completionDate",
                                        type = "text",
                                        required = true
                                ),
                                @Siren4JActionField(
                                        name = "owner",
                                        type = "text",
                                        required = true
                                )
                        }
                ),
                @Siren4JAction(
                        name = "createTemplateItem",
                        method = Method.POST,
                        href = "api/{templateId}/createTemplateItem",
                        type = "application/json",
                        fields = {
                                @Siren4JActionField(
                                        name = "templateItemName",
                                        type = "text",
                                        required = true
                                ),
                                @Siren4JActionField(
                                        name = "templateItemDescription",
                                        type = "text",
                                        required = true
                                ),
                                @Siren4JActionField(
                                        name = "templateItemState",
                                        type = "text",
                                        required = true
                                )
                        }
                ),
                @Siren4JAction(
                        name = "deleteTemplate",
                        method = Method.DELETE,
                        href = "api/deleteChecklistTemplate/{templateId}",
                        type = "application/json"
                )
        },
        links = {
                @Siren4JLink(
                        rel = "self",
                        href = "api/template/{templateId}"
                ),
                @Siren4JLink(
                        rel = "collection",
                        href = "api/{templateId}/templateItems"
                )
        }
)
public class TemplateSiren extends BaseResource {

    private Integer templateId;
    private String checklistTemplateName;
    private String templateDescription;
    private String owner;

    @Siren4JSubEntity(rel = "author", embeddedLink = true, uri = "api/costumer/{parent.owner}")
    private CostumerSiren user;

    @Siren4JSubEntity(rel = "collection", embeddedLink = true, uri = "api/{parent.templateId}/checklists")
    private CollectionResource<ChecklistSiren> checklists;

    @Siren4JSubEntity(rel = "collection", embeddedLink = true, uri = "api/{parent.templateId}/templateItems")
    private CollectionResource<TemplateItemSiren> templateItems;

    public TemplateSiren(Integer templateId, String checklistTemplateName, String templateDescription,String owner, CostumerSiren user,
                         CollectionResource<ChecklistSiren> checklists, CollectionResource<TemplateItemSiren> templateItems) {
        this.templateId = templateId;
        this.checklistTemplateName = checklistTemplateName;
        this.templateDescription = templateDescription;
        this.owner = owner;
        this.user = user;
        this.checklists = checklists;
        this.templateItems = templateItems;
    }

    public TemplateSiren(Integer templateId, String checklistTemplateName, String templateDescription, String owner) {
        this.templateId = templateId;
        this.checklistTemplateName = checklistTemplateName;
        this.templateDescription = templateDescription;
        this.owner = owner;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getChecklistTemplateName() {
        return checklistTemplateName;
    }

    public void setChecklistTemplateName(String checklistTemplateName) {
        this.checklistTemplateName = checklistTemplateName;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public CostumerSiren getUser() {
        return user;
    }

    public void setUser(CostumerSiren user) {
        this.user = user;
    }

    public CollectionResource<ChecklistSiren> getChecklists() {
        return checklists;
    }

    public void setChecklists(CollectionResource<ChecklistSiren> checklists) {
        this.checklists = checklists;
    }

    public CollectionResource<TemplateItemSiren> getTemplateItems() {
        return templateItems;
    }

    public void setTemplateItems(CollectionResource<TemplateItemSiren> templateItems) {
        this.templateItems = templateItems;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
