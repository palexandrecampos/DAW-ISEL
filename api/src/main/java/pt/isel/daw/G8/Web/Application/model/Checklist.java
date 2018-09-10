package pt.isel.daw.G8.Web.Application.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "Checklist")
@Table(name = "checklist")
public class Checklist implements Serializable {

    @Id
    @Column(name = "checklistId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated Checklist ID")
    private Integer checklistId;

    @Column(name = "checklistName", columnDefinition = "VARCHAR(255)")
    @ApiModelProperty(notes = "The Checklist Name")
    private String checklistName;

    @Column(name = "checklistCompletionDate")
    @ApiModelProperty(notes = "The Checklist Completion Date")
    private Date checklistCompletionDate;

    @ManyToOne
    @JoinColumn(name = "templateId")
    @ApiModelProperty(notes = "The Checklist Template ID used to create the Checklist")
    private ChecklistTemplate checklistTemplate;

    @ManyToOne
    @JoinColumn(name = "sub")
    @ApiModelProperty(notes = "The Costumer who created the Checklist")
    private Costumer owner;

    @OneToMany (mappedBy = "checklist")
    @ApiModelProperty(notes = "The Items present in Checklist")
    private List<ChecklistItem> items;

    public Checklist(Integer checklistId, String checklistName, Date checklistCompletionDate, ChecklistTemplate checklistTemplate,
                     Costumer owner, List<ChecklistItem> items) {
        this.checklistId = checklistId;
        this.checklistName = checklistName;
        this.checklistCompletionDate = checklistCompletionDate;
        this.checklistTemplate = checklistTemplate;
        this.owner = owner;
        this.items = items;
    }

    protected Checklist(){}

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

    public Date getChecklistCompletionDate() {
        return checklistCompletionDate;
    }

    public void setChecklistCompletionDate(Date checklistCompletionDate) {
        this.checklistCompletionDate = checklistCompletionDate;
    }

    public ChecklistTemplate getChecklistTemplate() {
        return checklistTemplate;
    }

    public void setChecklistTemplate(ChecklistTemplate checklistTemplate) {
        this.checklistTemplate = checklistTemplate;
    }

    public Costumer getOwner() {
        return owner;
    }

    public void setOwner(Costumer owner) {
        this.owner = owner;
    }

    public List<ChecklistItem> getItems() {
        return items;
    }

    public void setItems(List<ChecklistItem> items) {
        this.items = items;
    }
}
