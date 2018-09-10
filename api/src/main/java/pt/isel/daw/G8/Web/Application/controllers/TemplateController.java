package pt.isel.daw.G8.Web.Application.controllers;

import com.google.code.siren4j.Siren4J;
import com.google.code.siren4j.converter.ReflectingConverter;
import com.google.code.siren4j.error.Siren4JException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.daw.G8.Web.Application.exceptions.NotFoundException;
import pt.isel.daw.G8.Web.Application.inputModel.ChecklistInputModel;
import pt.isel.daw.G8.Web.Application.inputModel.TemplateInputModel;
import pt.isel.daw.G8.Web.Application.data.*;
import pt.isel.daw.G8.Web.Application.mapper.InputMapper;
import pt.isel.daw.G8.Web.Application.mapper.outputMapper.CollectionOutputMapper;
import pt.isel.daw.G8.Web.Application.mapper.outputMapper.SingleOutputMapper;
import pt.isel.daw.G8.Web.Application.model.*;
import pt.isel.daw.G8.Web.Application.outputModel.collections.ChecklistSirenCollection;
import pt.isel.daw.G8.Web.Application.outputModel.collections.TemplateSirenCollection;
import pt.isel.daw.G8.Web.Application.outputModel.singles.ChecklistSiren;

import com.google.code.siren4j.component.Entity;
import pt.isel.daw.G8.Web.Application.outputModel.singles.TemplateSiren;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api", produces = Siren4J.JSON_MEDIATYPE)
public class TemplateController extends Controller {

    @Autowired
    private IChecklistRepository checklistRepository;
    @Autowired
    private ICostumerRepository costumerRepository;
    @Autowired
    private IChecklistTemplateRepository templateRepository;
    @Autowired
    private ITemplateItemRepository templateItemRepository;
    @Autowired
    private IChecklistItemRepository checklistItemRepository;

    private InputMapper inputMapper = new InputMapper();
    private SingleOutputMapper singleOutputMapper = new SingleOutputMapper();
    private CollectionOutputMapper collectionOutputMapper = new CollectionOutputMapper();

    @ApiOperation(value = "Create Checklist Template", notes = "Values to create Checklist passed in RequestBody")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Checklist Template Created"),
            @ApiResponse(code = 409, message = "Checklist Template Already Exist"),
    })
    @PostMapping(value = "/createChecklistTemplate")
    public ResponseEntity<Entity> createChecklistTemplate(@RequestBody TemplateInputModel input, HttpServletRequest req) throws Siren4JException {

        Costumer costumer = costumerRepository.findCostumerBySub((String)req.getAttribute("sub"));
        ChecklistTemplate checklistTemplate = templateRepository.save(inputMapper.mapTemplate(input, costumer));
        List<ChecklistTemplate> costumerChecklistsTemplate = costumer.getChecklistTemplates();

        costumerChecklistsTemplate.add(checklistTemplate);
        costumer.setChecklistTemplates(costumerChecklistsTemplate);
        costumerRepository.save(costumer);

        TemplateSiren siren = singleOutputMapper.templateSirenMapper(checklistTemplate);
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }

    @ApiOperation(value = "Create Checklist Through A Template", notes = "Values to Checklist Template passed in RequestBody")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Checklist Created"),
            @ApiResponse(code = 409, message = "Checklist Already Exist"),
            @ApiResponse(code = 404, message = "The Template you were trying to reach is not found")
    })
    @PostMapping(value = "/template/{templateId}/createChecklist")
    public ResponseEntity<Entity> createChecklistFromTemplate(@RequestBody ChecklistInputModel input,
                                                              @PathVariable("templateId") Integer templateId, HttpServletRequest req) throws Siren4JException {

        Costumer costumer = costumerRepository.findCostumerBySub((String)req.getAttribute("sub"));
        ChecklistTemplate checklistTemplate = templateRepository.findTemplateByTemplateId(templateId);

        if (checklistTemplate == null)
            throw new NotFoundException();

        String name = input.checklistName == null ? checklistTemplate.getChecklistTemplateName()
                                                  : input.checklistName;

        Checklist checklist = checklistRepository.save(inputMapper.mapChecklist(input, costumer, checklistTemplate));
        checklistTemplate.getTemplateItems().forEach(item ->
                checklistItemRepository.save(
                        new ChecklistItem(null, item.getTemplateItemName(), item.getTemplateItemState(),
                                item.getTemplateItemDescription(), checklist)
                )
        );

        ChecklistSiren siren = singleOutputMapper.checklistSirenMapper(checklist);
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }


    @ApiOperation(value = "Get All Authenticated Costumer Templates")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Templates Retrieved"),
    })
    @GetMapping(value = "/myTemplates")
    public ResponseEntity<Entity> getMyTemplates(HttpServletRequest req) throws Siren4JException {

        Costumer costumer = costumerRepository.findCostumerBySub((String)req.getAttribute("sub"));

        TemplateSirenCollection siren = collectionOutputMapper.templateSirenCollectionMapper(costumer.getChecklistTemplates());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }

    @ApiOperation(value = "Get Template By ID", notes = "ID passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Template Retrieved"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/template/{templateId}")
    public ResponseEntity<Entity> getTemplate(@PathVariable("templateId") Integer templateId, HttpServletRequest req) throws Siren4JException {

        Optional<ChecklistTemplate> template = getCostumerTemplate((String)req.getAttribute("sub"), templateId);

        if (!template.isPresent())
            throw new NotFoundException();

        TemplateSiren siren = singleOutputMapper.templateSirenMapper(template.get());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }

    @ApiOperation(value = "Get All Checklists Created By Template", notes = "ID passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Checklist Retrieved"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/{templateId}/checklists")
    public ResponseEntity<Entity> getChecklistsFromTemplate(@PathVariable("templateId") Integer templateId, HttpServletRequest req) throws Siren4JException {

        Optional<ChecklistTemplate> template = getCostumerTemplate((String)req.getAttribute("sub"), templateId);

        if (!template.isPresent())
            throw new NotFoundException();

        ChecklistSirenCollection siren = collectionOutputMapper.checklistSirenCollectionMapper(template.get().getChecklists());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }

    @ApiOperation(value = "Delete Template", notes = "ID passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Checklist Deleted"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteChecklistTemplate/{templateId}")
    public ResponseEntity<Entity> deleteChecklistTemplate(@PathVariable("templateId") Integer templateId, HttpServletRequest req) throws Siren4JException {

        Optional<ChecklistTemplate> checklistTemplate = getCostumerTemplate((String)req.getAttribute("sub"), templateId);

        if (!checklistTemplate.isPresent())
            throw new NotFoundException();

        checklistTemplate.get().getTemplateItems().forEach(item -> templateItemRepository.delete(item));
        checklistTemplate.get().getChecklists().forEach(checklist -> {
                    checklist.setChecklistTemplate(null);
                    checklistRepository.save(checklist);
                }
        );

        templateRepository.deleteById(checklistTemplate.get().getTemplateId());

        TemplateSiren siren = singleOutputMapper.templateSirenMapper(checklistTemplate.get());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }
}
