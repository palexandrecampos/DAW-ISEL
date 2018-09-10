package pt.isel.daw.G8.Web.Application.controllers;

import com.google.code.siren4j.Siren4J;
import com.google.code.siren4j.component.Entity;
import com.google.code.siren4j.converter.ReflectingConverter;
import com.google.code.siren4j.error.Siren4JException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.daw.G8.Web.Application.data.*;
import pt.isel.daw.G8.Web.Application.exceptions.NotFoundException;
import pt.isel.daw.G8.Web.Application.inputModel.ChecklistInputModel;
import pt.isel.daw.G8.Web.Application.mapper.InputMapper;
import pt.isel.daw.G8.Web.Application.mapper.outputMapper.CollectionOutputMapper;
import pt.isel.daw.G8.Web.Application.mapper.outputMapper.SingleOutputMapper;
import pt.isel.daw.G8.Web.Application.model.*;
import pt.isel.daw.G8.Web.Application.outputModel.collections.ChecklistSirenCollection;
import pt.isel.daw.G8.Web.Application.outputModel.singles.ChecklistSiren;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api", produces = Siren4J.JSON_MEDIATYPE)
@Api(description = "Operations about Checklists")
public class ChecklistController {

    @Autowired
    private IChecklistRepository checklistRepository;
    @Autowired
    private ICostumerRepository costumerRepository;
    @Autowired
    private IChecklistItemRepository checklistItemRepository;

    private InputMapper inputMapper = new InputMapper();
    private SingleOutputMapper singleOutputMapper = new SingleOutputMapper();
    private CollectionOutputMapper collectionOutputMapper = new CollectionOutputMapper();


    @ApiOperation(value = "Get Costumer Checklists")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved All Checklist From Authenticated Costumer"),
    })
    @GetMapping(value = "/myChecklists")
    public ResponseEntity<Entity> getMyChecklists(HttpServletRequest req) throws Siren4JException {

        Costumer costumer = costumerRepository.findCostumerBySub((String)req.getAttribute("sub"));

        ChecklistSirenCollection siren = collectionOutputMapper.checklistSirenCollectionMapper(costumer.getChecklists());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }

    @ApiOperation(value = "Get Checklist by ID", notes = "Checklist ID passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Checklist Retrieved"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping(value = "/myChecklist/{checklistId}")
    public ResponseEntity<Entity> getSpecifiedChecklist(@PathVariable("checklistId") Integer checklistId, HttpServletRequest req) throws Siren4JException {

        Costumer costumer = costumerRepository.findCostumerBySub((String)req.getAttribute("sub"));
        Optional<Checklist> checklist = costumer.getChecklists().stream().filter(list ->
                list.getChecklistId().equals(checklistId)).findFirst();

        if (!checklist.isPresent())
            throw new NotFoundException();

        ChecklistSiren siren = singleOutputMapper.checklistSirenMapper(checklist.get());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }

    @ApiOperation(value = "Create Checklists", notes = "Values to create Checklist passed in RequestBody")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Checklist Created"),
            @ApiResponse(code = 409, message = "Checklist Already Exist"),
    })
    @PostMapping(value = "/createChecklist")
    public ResponseEntity<Entity> createChecklist(@RequestBody ChecklistInputModel input, HttpServletRequest req) throws Siren4JException {

        Costumer costumer = costumerRepository.findCostumerBySub((String)req.getAttribute("sub"));
        Checklist createdChecklist = checklistRepository.save(inputMapper.mapChecklist(input, costumer, null));
        List<Checklist> costumerChecklists = costumer.getChecklists();

        costumerChecklists.add(createdChecklist);
        costumer.setChecklists(costumerChecklists);
        costumerRepository.save(costumer);

        ChecklistSiren siren = singleOutputMapper.checklistSirenMapper(createdChecklist);
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }


    @ApiOperation(value = "Delete Checklist", notes = "Checklist ID passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Checklist Deleted"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @DeleteMapping(value = "/deleteChecklist/{checklistId}")
    public ResponseEntity<Entity> deleteChecklist(@PathVariable("checklistId") Integer checklistId, HttpServletRequest req) throws Siren4JException {

        Costumer costumer = costumerRepository.findCostumerBySub((String)req.getAttribute("sub"));
        Optional<Checklist> checklist = costumer.getChecklists().stream().filter(list -> list.getChecklistId().equals(checklistId)).findFirst();

        if (!checklist.isPresent())
            throw new NotFoundException();

        checklist.get().getItems().forEach(item -> checklistItemRepository.delete(item));
        costumer.getChecklists().remove(checklist.get());
        costumerRepository.save(costumer);
        checklistRepository.deleteById(checklist.get().getChecklistId());

        ChecklistSiren siren = singleOutputMapper.checklistSirenMapper(checklist.get());
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }
}
