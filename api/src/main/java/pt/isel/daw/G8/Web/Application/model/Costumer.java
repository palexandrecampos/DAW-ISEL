package pt.isel.daw.G8.Web.Application.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Costumer")
@Table(name = "costumer")
public class Costumer implements Serializable {

    @Id
    @Column(name = "sub", columnDefinition = "VARCHAR(255)")
    @ApiModelProperty(notes = "The identifier of Costumer")
    private String sub;

    @OneToMany (mappedBy = "owner")
    @ApiModelProperty(notes = "The Checklists Created By Costumer")
    private List<Checklist> checklists;

    @OneToMany(mappedBy = "owner")
    @ApiModelProperty(notes = "The Checklist Templates Created By Costumer")
    private List<ChecklistTemplate> checklistTemplates;

    public Costumer(String sub, List<Checklist> checklists, List<ChecklistTemplate> checklistTemplates){
        this.sub = sub;
        this.checklists = checklists;
        this.checklistTemplates = checklistTemplates;
    }

    public Costumer(String sub){
        this.sub = sub;
        this.checklists = new ArrayList<>();
        this.checklistTemplates = new ArrayList<>();
    }

    protected Costumer(){}

    public String getSub() {
        return sub;
    }

    public void setSub(String username) {
        this.sub = username;
    }

    public List<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(List<Checklist> checklists) {
        this.checklists = checklists;
    }

    public List<ChecklistTemplate> getChecklistTemplates() {
        return checklistTemplates;
    }

    public void setChecklistTemplates(List<ChecklistTemplate> checklistTemplates) {
        this.checklistTemplates = checklistTemplates;
    }

    @Override
    public String toString() {
        return String.format("Customer[sub=%s]", getSub());
    }
}
