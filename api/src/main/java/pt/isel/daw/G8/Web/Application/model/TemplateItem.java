package pt.isel.daw.G8.Web.Application.model;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "TemplateItem")
@Table(name = "templateItem")
public class TemplateItem implements Serializable {

    @Id
    @Column(name = "templateItemId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated Template Item ID")
    private Integer templateItemId;

    @Column(name = "templateItemName", columnDefinition = "VARCHAR(255)")
    @ApiModelProperty(notes = "The Template Item Name")
    private String templateItemName;

    @Column(name = "templateItemState")
    @ApiModelProperty(notes = "The state of Template Item", allowableValues = "completed, uncompleted")
    private StateEnum templateItemState;

    @Column(name = "templateItemDescription")
    @ApiModelProperty(notes = "The Description of Template Item")
    private String templateItemDescription;

    @ManyToOne
    @JoinColumn(name = "templateId")
    @ApiModelProperty(notes = "The checklist ID where item is inserted")
    private ChecklistTemplate template;

    public TemplateItem(Integer templateItemId, String templateItemName, StateEnum templateItemState,
                        String templateItemDescription, ChecklistTemplate template) {
        this.templateItemId = templateItemId;
        this.templateItemName = templateItemName;
        this.templateItemState = templateItemState;
        this.templateItemDescription = templateItemDescription;
        this.template = template;
    }

    protected TemplateItem() {
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

    public void setTemplateItemDescription(String templateDescription) {
        this.templateItemDescription = templateItemDescription;
    }

    public ChecklistTemplate getTemplate() {
        return template;
    }

    public void setTemplate(ChecklistTemplate template) {
        this.template = template;
    }

}
