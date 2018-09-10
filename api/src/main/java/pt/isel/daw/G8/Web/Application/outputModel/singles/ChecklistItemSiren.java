package pt.isel.daw.G8.Web.Application.outputModel.singles;

import com.google.code.siren4j.annotations.*;
import com.google.code.siren4j.component.impl.ActionImpl.*;
import com.google.code.siren4j.resource.BaseResource;
import com.google.code.siren4j.resource.CollectionResource;
import pt.isel.daw.G8.Web.Application.model.StateEnum;
import pt.isel.daw.G8.Web.Application.outputModel.collections.ChecklistItemSirenCollection;

@Siren4JEntity(name = "checklistItem",suppressClassProperty = true,
        actions = {
                @Siren4JAction(
                        name = "deleteItem",
                        method = Method.DELETE,
                        href = "api/{checklistId}/deleteItem/{itemId}",
                        type = "application/json",
                        fields = {}
                ),
                @Siren4JAction(
                        name = "updateItem",
                        method = Method.PUT,
                        href = "api/{checklistId}/editChecklistItemState/{itemId}",
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
                        href = "/api/{checklistId}/item/{itemId}"
                )
        }
)
public class ChecklistItemSiren extends BaseResource {

    private Integer checklistId;
    private Integer itemId;
    private String itemName;
    private StateEnum itemState;
    private String itemDescription;

    @Siren4JSubEntity(rel = "collection", embeddedLink = true, uri = "api/{checklistId}/items")
    private ChecklistItemSirenCollection items;

    @Siren4JSubEntity(rel = "about", embeddedLink = true, uri = "api/checklist/{parent.checklistId}" )
    private ChecklistSiren checklist;

    public ChecklistItemSiren(Integer checklistId, Integer itemId, String itemName, StateEnum itemState,
                              String itemDescription, ChecklistItemSirenCollection items, ChecklistSiren checklist) {
        this.checklistId = checklistId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemState = itemState;
        this.itemDescription = itemDescription;
        this.items = items;
        this.checklist = checklist;
    }

    public ChecklistItemSiren(Integer checklistId, Integer itemId, String itemName, StateEnum itemState, String itemDescription) {
        this.checklistId = checklistId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemState = itemState;
        this.itemDescription = itemDescription;
    }

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public StateEnum getItemState() {
        return itemState;
    }

    public void setItemState(StateEnum itemState) {
        this.itemState = itemState;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public ChecklistSiren getChecklist() {
        return checklist;
    }

    public void setChecklist(ChecklistSiren checklist) {
        this.checklist = checklist;
    }

    public CollectionResource<ChecklistItemSiren> getItems() {
        return items;
    }

    public void setItems(ChecklistItemSirenCollection items) {
        this.items = items;
    }
}