package pt.isel.daw.G8.Web.Application.controllers;

import com.google.code.siren4j.Siren4J;
import com.google.code.siren4j.component.Entity;
import com.google.code.siren4j.converter.ReflectingConverter;
import com.google.code.siren4j.error.Siren4JException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.daw.G8.Web.Application.exceptions.BadRequestException;
import pt.isel.daw.G8.Web.Application.exceptions.NotFoundException;
import pt.isel.daw.G8.Web.Application.inputModel.TemplateItemInputModel;
import pt.isel.daw.G8.Web.Application.data.IChecklistTemplateRepository;
import pt.isel.daw.G8.Web.Application.data.ITemplateItemRepository;
import pt.isel.daw.G8.Web.Application.mapper.InputMapper;
import pt.isel.daw.G8.Web.Application.mapper.outputMapper.CollectionOutputMapper;
import pt.isel.daw.G8.Web.Application.mapper.outputMapper.SingleOutputMapper;
import pt.isel.daw.G8.Web.Application.model.*;
import pt.isel.daw.G8.Web.Application.outputModel.collections.TemplateItemSirenCollection;
import pt.isel.daw.G8.Web.Application.outputModel.singles.TemplateItemSiren;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api", produces = Siren4J.JSON_MEDIATYPE)
public class TemplateItemController extends Controller {

    @Autowired
    private ITemplateItemRepository templateItemRepository;
    @Autowired
    private IChecklistTemplateRepository templateRepository;

    private InputMapper inputMapper = new InputMapper();
    private SingleOutputMapper singleOutputMapper = new SingleOutputMapper();
    private CollectionOutputMapper collectionOutputMapper = new CollectionOutputMapper();


    @ApiOperation(value = "Create Item in Checklist Template", notes = "Template ID passed in RequestURI and Item Values passed in RequestBody")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Template Item Created"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping(value = "/{templateId}/createTemplateItem")
    public ResponseEntity<Entity> createChecklistTemplateItem(@PathVariable("templateId") Integer templateId,
                                                              @RequestBody TemplateItemInputModel input, HttpServletRequest req) throws Siren4JException {

        Optional<ChecklistTemplate> checklistTemplate = getCostumerTemplate((String)req.getAttribute("sub"), templateId);

        if (!checklistTemplate.isPresent())
            throw new NotFoundException();

        TemplateItem templateItem = inputMapper.mapTemplateItem(input, StateEnum.uncompleted, checklistTemplate.get());
        List<TemplateItem> templateItems = checklistTemplate.get().getTemplateItems();
        templateItems.add(templateItem);
        checklistTemplate.get().setTemplateItems(templateItems);
        templateRepository.save(checklistTemplate.get());
        templateItemRepository.save(templateItem);

        TemplateItemSiren siren = singleOutputMapper.templateItemSirenMapper(templateItem);
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }

    @ApiOperation(value = "Get Items From Checklist Template", notes = "Template ID passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Template Items Retrieved"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/{templateId}/templateItems")
    public ResponseEntity<Entity> getChecklistTemplateItems(@PathVariable("templateId") Integer templateId, HttpServletRequest req) throws Siren4JException {

        Optional<ChecklistTemplate> checklistTemplate = getCostumerTemplate((String)req.getAttribute("sub"), templateId);

        if (!checklistTemplate.isPresent())
            throw new NotFoundException();

        TemplateItemSirenCollection siren = collectionOutputMapper.templateItemSirenCollectionMapper(checklistTemplate.get().getTemplateItems());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }

    @ApiOperation(value = "Get Specific Item From Checklist Template", notes = "Template ID and Template Item ID passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Template Item Retrieved"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/{templateId}/templateItem/{templateItemId}")
    public ResponseEntity<Entity> getTemplateItem(@PathVariable("templateItemId") Integer templateItemId,
                                                  @PathVariable("templateId") Integer templateId, HttpServletRequest req) throws Siren4JException {
        Optional<ChecklistTemplate> checklistTemplate = getCostumerTemplate((String)req.getAttribute("sub"),templateId);

        if (!checklistTemplate.isPresent())
            throw new BadRequestException();

        Optional<TemplateItem> item = getItemFromTemplate(checklistTemplate.get(), templateItemId);
        if (!item.isPresent())
            throw new NotFoundException();

        TemplateItemSiren siren = singleOutputMapper.templateItemSirenMapper(item.get());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }

    @ApiOperation(value = "Delete Item From Checklist Template", notes = "Template ID and Template Item ID passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Template Item Deleted"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping("/{templateId}/deleteTemplateItem/{templateItemId}")
    public ResponseEntity<Entity> deleteTemplateItem(@PathVariable("templateId") Integer templateId,
                                                     @PathVariable("templateItemId") Integer templateItemId, HttpServletRequest req) throws Siren4JException {

        Optional<ChecklistTemplate> checklistTemplate = getCostumerTemplate((String)req.getAttribute("sub"), templateId);

        if (!checklistTemplate.isPresent())
            throw new BadRequestException();

        Optional<TemplateItem> itemToRemove = getItemFromTemplate(checklistTemplate.get(), templateItemId);

        if (!itemToRemove.isPresent())
            throw new NotFoundException();

        checklistTemplate.get().getTemplateItems().remove(itemToRemove.get());

        templateRepository.save(checklistTemplate.get());
        templateItemRepository.delete(itemToRemove.get());

        TemplateItem item = itemToRemove.get();

        TemplateItemSiren siren = singleOutputMapper.templateItemSirenMapper(item);
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }

    @ApiOperation(value = "Edit Item State From Checklist Template", notes = "Template ID and Template Item ID passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Template Item State Changed"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/{templateId}/editTemplateItemState/{templateItemId}")
    public ResponseEntity<Entity> editTemplateItem(@PathVariable("templateId") Integer templateId,
                                                   @PathVariable("templateItemId") Integer templateItemId, HttpServletRequest req) throws Siren4JException {
        Optional<ChecklistTemplate> checklistTemplate = getCostumerTemplate((String)req.getAttribute("sub"), templateId);
        if (!checklistTemplate.isPresent())
            throw new BadRequestException();

        Optional<TemplateItem> templateItem = getItemFromTemplate(checklistTemplate.get(), templateItemId);

        if (!templateItem.isPresent())
            throw new NotFoundException();

        templateItem.get().setTemplateItemState(StateEnum.completed);
        templateItemRepository.save(templateItem.get());

        TemplateItemSiren siren = singleOutputMapper.templateItemSirenMapper(templateItem.get());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }
}
