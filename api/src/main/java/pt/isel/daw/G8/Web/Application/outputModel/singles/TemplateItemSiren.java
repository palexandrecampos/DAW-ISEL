package pt.isel.daw.G8.Web.Application.outputModel.singles;

import com.google.code.siren4j.annotations.*;
import com.google.code.siren4j.component.impl.ActionImpl.*;
import com.google.code.siren4j.resource.BaseResource;
import pt.isel.daw.G8.Web.Application.model.StateEnum;
import pt.isel.daw.G8.Web.Application.outputModel.collections.TemplateItemSirenCollection;

@Siren4JEntity(name = "templateItem",suppressClassProperty = true,
        actions = {
                @Siren4JAction(
                        name = "deleteTemplateItem",
                        method = Method.DELETE,
                        href = "api/{templateId}/deleteTemplateItem/{templateItemId}",
                        type = "application/json",
                        fields = {}
                ),
                @Siren4JAction(
                        name = "updateTemplateItem",
                        method = Method.PUT,
                        href = "api/{templateId}/editTemplateItemState/{templateItemId}",
                        type = "application/json",
                        fields = {
                                @Siren4JActionField(
                                        name = "state",
                                        type = "text",
                                        required = true
                                )
                        }
                )
        },
        links = {
                @Siren4JLink(
                        rel = "self",
                        href = "/api/{templateId}/templateItem/{templateItemId}"
                )
        }
)
public class TemplateItemSiren extends BaseResource {

    private Integer templateId;
    private Integer templateItemId;
    private String templateItemName;
    private StateEnum templateItemState;
    private String templateItemDescription;

    @Siren4JSubEntity(rel = "collection", embeddedLink = true, uri = "api/{templateId}/templateItems")
    private TemplateItemSirenCollection templateItems;

    @Siren4JSubEntity(rel = "about", embeddedLink = true, uri = "api/template/{templateId}")
    private TemplateSiren template;

    public TemplateItemSiren(Integer templateId,Integer templateItemId, String templateItemName,
                             StateEnum templateItemState, String templateItemDescription,
                             TemplateItemSirenCollection templateItems, TemplateSiren template) {
        this.templateId = templateId;
        this.templateItemId = templateItemId;
        this.templateItemName = templateItemName;
        this.templateItemState = templateItemState;
        this.templateItemDescription = templateItemDescription;
        this.templateItems = templateItems;
        this.template = template;
    }

    public TemplateItemSiren(Integer templateId,Integer templateItemId, String templateItemName,
                             StateEnum templateItemState, String templateItemDescription) {
        this.templateId = templateId;
        this.templateItemId = templateItemId;
        this.templateItemName = templateItemName;
        this.templateItemState = templateItemState;
        this.templateItemDescription = templateItemDescription;
    }

    public Integer getTemplateId() {

        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getTemplateItemId() {
        return templateItemId;
    }

    public void setTemplateItemId(Integer templateItemId) {
        this.templateItemId = templateItemId;
    }

    public String getTemplateItemName() {
        return templateItemName;
    }

    public void setTemplateItemName(String templateItemName) {
        this.templateItemName = templateItemName;
    }

    public StateEnum getTemplateItemState() {
        return templateItemState;
    }

    public void setTemplateItemState(StateEnum templateItemState) {
        this.templateItemState = templateItemState;
    }

    public String getTemplateItemDescription() {
        return templateItemDescription;
    }

    public void setTemplateItemDescription(String templateItemDescription) {
        this.templateItemDescription = templateItemDescription;
    }

    public TemplateSiren getTemplate() {
        return template;
    }

    public void setTemplate(TemplateSiren template) {
        this.template = template;
    }

    public TemplateItemSirenCollection getTemplateItems() {
        return templateItems;
    }

    public void setTemplateItems(TemplateItemSirenCollection templateItems) {
        this.templateItems = templateItems;
    }
}