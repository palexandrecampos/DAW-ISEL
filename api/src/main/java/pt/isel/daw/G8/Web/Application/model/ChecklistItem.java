package pt.isel.daw.G8.Web.Application.model;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "ChecklistItem")
@Table(name = "checklistItem")
public class ChecklistItem implements Serializable {

    @Id
    @Column(name = "itemId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated ChecklistItem ID")
    private Integer itemId;

    @Column(name = "itemName", columnDefinition = "VARCHAR(255)")
    @ApiModelProperty(notes = "The name of Checklist Item")
    private String itemName;

    @Column(name = "itemState")
    @ApiModelProperty(notes = "The state of Checklist Item", allowableValues = "completed, uncompleted")
    private StateEnum itemState;

    @Column(name = "itemDescription")
    @ApiModelProperty(notes = "The Checklist Item Description")
    private String itemDescription;

    @ManyToOne
    @JoinColumn(name = "checklistId")
    @ApiModelProperty(notes = "The checklist ID where item is inserted")
    private Checklist checklist;

    public ChecklistItem(Integer itemId, String itemName, StateEnum itemState, String itemDescription, Checklist checklist) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemState = itemState;
        this.itemDescription = itemDescription;
        this.checklist = checklist;
    }

    protected ChecklistItem(){}

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

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklistId(Checklist checklistId) {
        this.checklist = checklistId;
    }
}
