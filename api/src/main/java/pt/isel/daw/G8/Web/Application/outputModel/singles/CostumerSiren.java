package pt.isel.daw.G8.Web.Application.outputModel.singles;


import com.google.code.siren4j.annotations.*;
import com.google.code.siren4j.component.impl.ActionImpl.*;
import com.google.code.siren4j.resource.BaseResource;
import com.google.code.siren4j.resource.CollectionResource;

@Siren4JEntity(name = "sub", suppressClassProperty = true,
        actions = {
                @Siren4JAction(
                        name = "createUser",
                        method = Method.POST,
                        href = "api/createCostumer",
                        type = "application/json",
                        fields = {
                                @Siren4JActionField(
                                        name = "sub",
                                        type = "text",
                                        required = true
                                ),
                                @Siren4JActionField(
                                        name = "password",
                                        type = "text",
                                        required = true
                                ),
                                @Siren4JActionField(
                                        name = "email",
                                        type = "text",
                                        required = true
                                )
                        }
                ),
                @Siren4JAction(
                        name = "deleteUser",
                        method = Method.DELETE,
                        href = "api/costumer/deleteCostumer/{sub}",
                        type = "application/json"
                )
        },
        links = {
                @Siren4JLink(
                        rel = "self",
                        href = "api/costumer/{sub}"
                )
        }
)
public class CostumerSiren extends BaseResource {

    private String sub;

    @Siren4JSubEntity(rel = "checklists", embeddedLink = true, uri = "api/myChecklist")
    private CollectionResource<ChecklistSiren> checklists;

    @Siren4JSubEntity(rel = "templates", embeddedLink = true, uri = "api/templates")
    private CollectionResource<TemplateSiren> templates;

    public CostumerSiren(String sub,
                         CollectionResource<ChecklistSiren> checklists, CollectionResource<TemplateSiren> templates) {
        this.sub = sub;
        this.checklists = checklists;
        this.templates = templates;
    }

    public CostumerSiren(String sub) {

        this.sub = sub;
    }

    public CostumerSiren() {
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public CollectionResource<ChecklistSiren> getChecklists() {
        return checklists;
    }

    public void setChecklists(CollectionResource<ChecklistSiren> checklists) {
        this.checklists = checklists;
    }

    public CollectionResource<TemplateSiren> getTemplates() {
        return templates;
    }

    public void setTemplates(CollectionResource<TemplateSiren> templates) {
        this.templates = templates;
    }
}
