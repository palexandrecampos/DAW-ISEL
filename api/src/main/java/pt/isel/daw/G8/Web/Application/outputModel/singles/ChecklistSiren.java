package pt.isel.daw.G8.Web.Application.outputModel.singles;

import com.google.code.siren4j.annotations.*;
import com.google.code.siren4j.component.impl.ActionImpl.*;
import com.google.code.siren4j.resource.BaseResource;
import com.google.code.siren4j.resource.CollectionResource;

import java.util.Date;

@Siren4JEntity(name = "checklist", suppressClassProperty = true,
        actions = {
                @Siren4JAction(
                        name = "createChecklist",
                        method = Method.POST,
                        href = "api/createChecklist",
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
                        name = "createChecklistItem",
                        method = Method.POST,
                        href = "api/{checklistId}/createItem",
                        type = "application/json",
                        fields = {
                                @Siren4JActionField(
                                        name = "itemName",
                                        type = "text",
                                        required = true
                                ),
                                @Siren4JActionField(
                                        name = "description",
                                        type = "text",
                                        required = true
                                )
                        }
                ),
                @Siren4JAction(
                        name = "deleteChecklist",
                        method = Method.DELETE,
                        href = "/deleteChecklist/{checklistId}",
                        type = "application/json"
                )
        },
        links = {
                @Siren4JLink(
                        rel = "self",
                        href = "api/myChecklist/{checklistId}"
                ),
                @Siren4JLink(
                        rel = "collection",
                        href = "api/{checklistId}/items"
                )
        }
)
public class ChecklistSiren extends BaseResource {

    private Integer checklistId;
    private String checklistName;
    private Date completionDate;
    private Integer templateId;
    private String owner;

    @Siren4JSubEntity(rel = "user", embeddedLink = true, uri = "api/costumer/{parent.owner}")
    private CostumerSiren user;

    @Siren4JSubEntity(rel = "items", embeddedLink = true, uri = "api/{parent.checklistId}/items")
    private CollectionResource<ChecklistItemSiren> items;

    public ChecklistSiren(Integer checklistId, String checklistName, Date completionDate, String owner,
                          CostumerSiren user, CollectionResource<ChecklistItemSiren> items) {
        this.checklistId = checklistId;
        this.checklistName = checklistName;
        this.completionDate = completionDate;
        this.owner = owner;
        this.user = user;
        this.items = items;
    }

    public ChecklistSiren(Integer checklistId, String checklistName, Date completionDate, Integer templateId, String owner) {
        this.checklistId = checklistId;
        this.checklistName = checklistName;
        this.completionDate = completionDate;
        this.templateId = templateId;
        this.owner = owner;
    }

    public ChecklistSiren(Integer checklistId, String checklistName, Date completionDate, String owner) {
        this.checklistId = checklistId;
        this.checklistName = checklistName;
        this.completionDate = completionDate;
        this.owner = owner;
    }

    public ChecklistSiren(Integer checklistId, String checklistName, Date checklistCompletionDate) {
        this.checklistId = checklistId;
        this.checklistName = checklistName;
        this.completionDate = checklistCompletionDate;
    }

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public String getChecklistName() {
        return checklistName;
    }

    public void setChecklistName(String checklistName) {
        this.checklistName = checklistName;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public CostumerSiren getUser() {
        return user;
    }

    public void setUser(CostumerSiren user) {
        this.user = user;
    }

    public CollectionResource<ChecklistItemSiren> getItems() {
        return items;
    }

    public void setItems(CollectionResource<ChecklistItemSiren> items) {
        this.items = items;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

}
