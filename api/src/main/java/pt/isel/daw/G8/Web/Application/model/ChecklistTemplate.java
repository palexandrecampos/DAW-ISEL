package pt.isel.daw.G8.Web.Application.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "ChecklistTemplate")
@Table(name = "checklistTemplate")
public class ChecklistTemplate implements Serializable {

    @Id
    @Column(name = "templateId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated Checklist Template ID")
    private Integer templateId;

    @Column(name = "checklistTemplateName", columnDefinition = "VARCHAR(255)")
    @ApiModelProperty(notes = "The Name of Checklist Template")
    private String checklistTemplateName;

    @Column(name = "templateDescription")
    @ApiModelProperty(notes = "The Description of Checklist Template")
    private String templateDescription;

    @ManyToOne
    @JoinColumn(name = "sub")
    @ApiModelProperty(notes = "The Costumer who created the Checklist")
    private Costumer owner;

    @OneToMany (mappedBy = "checklistTemplate")
    @ApiModelProperty(notes = "The Checklists Created With The Template")
    private List<Checklist> checklists;

    @OneToMany (mappedBy = "template")
    @ApiModelProperty(notes = "The Items present in Checklist Template")
    private List<TemplateItem> templateItems;

    public ChecklistTemplate(Integer templateId, String checklistTemplateName, String templateDescription,
                             Costumer owner, List<Checklist> checklists, List<TemplateItem> templateItems) {
        this.templateId = templateId;
        this.checklistTemplateName = checklistTemplateName;
        this.templateDescription = templateDescription;
        this.owner = owner;
        this.checklists = checklists;
        this.templateItems = templateItems;
    }

    protected ChecklistTemplate(){}

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

    public Costumer getOwner() {
        return owner;
    }

    public void setOwner(Costumer owner) {
        this.owner = owner;
    }

    public List<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(List<Checklist> checklists) {
        this.checklists = checklists;
    }

    public List<TemplateItem> getTemplateItems() {
        return templateItems;
    }

    public void setTemplateItems(List<TemplateItem> templateItems) {
        this.templateItems = templateItems;
    }
}
