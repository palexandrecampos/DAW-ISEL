package pt.isel.daw.G8.Web.Application.controllers;

import com.google.code.siren4j.Siren4J;
import com.google.code.siren4j.converter.ReflectingConverter;
import com.google.code.siren4j.error.Siren4JException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.code.siren4j.component.Entity;
import pt.isel.daw.G8.Web.Application.data.ICostumerRepository;
import pt.isel.daw.G8.Web.Application.exceptions.NotFoundException;
import pt.isel.daw.G8.Web.Application.inputModel.ChecklistItemInputModel;
import pt.isel.daw.G8.Web.Application.data.IChecklistItemRepository;
import pt.isel.daw.G8.Web.Application.data.IChecklistRepository;
import pt.isel.daw.G8.Web.Application.mapper.InputMapper;
import pt.isel.daw.G8.Web.Application.mapper.outputMapper.CollectionOutputMapper;
import pt.isel.daw.G8.Web.Application.mapper.outputMapper.SingleOutputMapper;
import pt.isel.daw.G8.Web.Application.model.Checklist;
import pt.isel.daw.G8.Web.Application.model.ChecklistItem;
import pt.isel.daw.G8.Web.Application.model.Costumer;
import pt.isel.daw.G8.Web.Application.model.StateEnum;
import pt.isel.daw.G8.Web.Application.outputModel.collections.ChecklistItemSirenCollection;
import pt.isel.daw.G8.Web.Application.outputModel.singles.ChecklistItemSiren;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api", produces = Siren4J.JSON_MEDIATYPE)
public class ChecklistItemController extends Controller {

    @Autowired
    private IChecklistRepository checklistRepository;
    @Autowired
    private IChecklistItemRepository checklistItemRepository;
    @Autowired
    private ICostumerRepository costumerRepository;

    private InputMapper inputMapper = new InputMapper();
    private SingleOutputMapper singleOutputMapper = new SingleOutputMapper();
    private CollectionOutputMapper collectionOutputMapper = new CollectionOutputMapper();

    @ApiOperation(value = "Create Item in Checklist", notes = "Checklist ID passed in RequestURI and Item Values passed in RequestBody")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Checklist Item Created"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping(value = "/{checklistId}/createItem")
    public ResponseEntity<Entity> createChecklistItem(@PathVariable("checklistId") Integer checklistId,
                                                      @RequestBody ChecklistItemInputModel input, HttpServletRequest req) throws Siren4JException {

        Optional<Checklist> checklist = getCostumerChecklist((String)req.getAttribute("sub"), checklistId);

        if (!checklist.isPresent())
            throw new NotFoundException();

        ChecklistItem item = inputMapper.mapChecklistItem(input, checklist.get(), StateEnum.uncompleted);
        List<ChecklistItem> checklistItems = checklist.get().getItems();
        checklistItems.add(item);
        checklist.get().setItems(checklistItems);
        checklistRepository.save(checklist.get());
        checklistItemRepository.save(item);

        ChecklistItemSiren siren = singleOutputMapper.checklistItemSirenMapper(item);
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }

    @ApiOperation(value = "Get Items from Checklist", notes = "Checklist ID passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Checklist Items Retrieved"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/{checklistId}/items")
    public ResponseEntity<Entity> getChecklistItems(@PathVariable("checklistId") Integer checklistId, HttpServletRequest req) throws Siren4JException {

        Costumer costumer = costumerRepository.findCostumerBySub((String)req.getAttribute("sub"));
        Optional<Checklist> checklist = costumer.getChecklists().stream().filter(list ->
                list.getChecklistId().equals(checklistId)).findFirst();

        if (!checklist.isPresent())
            throw new NotFoundException();

        ChecklistItemSirenCollection siren = collectionOutputMapper.checklistItemSirenCollectionMapper(checklist.get().getItems());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }

    @ApiOperation(value = "Get Specific Item from Checklist", notes = "Checklist ID and Item ID passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Checklist Item Retrieved"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/{checklistId}/item/{itemId}")
    public ResponseEntity<Entity> getChecklistSpecifiedItem(@PathVariable("itemId") Integer itemId,
                                                            @PathVariable("checklistId") Integer checklistId, HttpServletRequest req) throws Siren4JException {

        Optional<Checklist> checklist = getCostumerChecklist((String)req.getAttribute("sub"), checklistId);

        if (!checklist.isPresent())
            throw new NotFoundException();

        Optional<ChecklistItem> checklistItem = getItemFromChecklist(checklist.get(), itemId);

        if (!checklistItem.isPresent())
            throw new NotFoundException();

        ChecklistItemSiren siren = singleOutputMapper.checklistItemSirenMapper(checklistItem.get());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }


    @ApiOperation(value = "Get Specific Item from Checklist", notes = "Checklist ID and Item ID passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Checklist Item Deleted"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping("/{checklistId}/deleteItem/{itemId}")
    public ResponseEntity<Entity> deleteChecklistItem(@PathVariable("checklistId") Integer checklistId,
                                                      @PathVariable("itemId") Integer itemId, HttpServletRequest req) throws Siren4JException {

        Optional<Checklist> checklist = getCostumerChecklist((String)req.getAttribute("sub"), checklistId);

        if (!checklist.isPresent())
            throw new NotFoundException();

        Optional<ChecklistItem> checklistItem = getItemFromChecklist(checklist.get(), itemId);

        if (!checklistItem.isPresent())
            throw new NotFoundException();

        checklist.get().getItems().remove(checklistItem.get());
        checklistRepository.save(checklist.get());
        checklistItemRepository.delete(checklistItem.get());

        ChecklistItemSiren siren = singleOutputMapper.checklistItemSirenMapper(checklistItem.get());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }


    @ApiOperation(value = "Change State of Specific Item from Checklist", notes = "Checklist ID and Item ID passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Checklist Item State Changed"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/{checklistId}/editChecklistItemState/{itemId}")
    public ResponseEntity<Entity> editChecklist(@PathVariable("checklistId") Integer checklistId,
                                                @PathVariable("itemId") Integer itemId, HttpServletRequest req) throws Siren4JException {

        Optional<Checklist> checklist = getCostumerChecklist((String)req.getAttribute("sub"), checklistId);

        if (!checklist.isPresent())
            throw new NotFoundException();

        Optional<ChecklistItem> checklistItem = getItemFromChecklist(checklist.get(), itemId);

        if (!checklistItem.isPresent())
            throw new NotFoundException();

        checklistItem.get().setItemState(StateEnum.completed);
        checklistItemRepository.save(checklistItem.get());

        ChecklistItemSiren siren = singleOutputMapper.checklistItemSirenMapper(checklistItem.get());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }
}
