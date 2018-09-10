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
import pt.isel.daw.G8.Web.Application.inputModel.CostumerInputModel;
import pt.isel.daw.G8.Web.Application.data.*;
import pt.isel.daw.G8.Web.Application.exceptions.NotFoundException;
import pt.isel.daw.G8.Web.Application.mapper.InputMapper;
import pt.isel.daw.G8.Web.Application.mapper.outputMapper.CollectionOutputMapper;
import pt.isel.daw.G8.Web.Application.mapper.outputMapper.SingleOutputMapper;
import pt.isel.daw.G8.Web.Application.model.Costumer;
import pt.isel.daw.G8.Web.Application.outputModel.collections.CostumerSirenCollection;
import pt.isel.daw.G8.Web.Application.outputModel.singles.CostumerSiren;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = Siren4J.JSON_MEDIATYPE)
@Api(description = "Operations about Costumers")
public class CostumerController {

    @Autowired
    private ICostumerRepository costumerRepository;
    @Autowired
    private IChecklistRepository checklistRepository;
    @Autowired
    private IChecklistItemRepository checklistItemRepository;
    @Autowired
    private ITemplateItemRepository templateItemRepository;
    @Autowired
    private IChecklistTemplateRepository checklistTemplateRepository;

    private InputMapper inputMapper = new InputMapper();
    private SingleOutputMapper singleOutputMapper = new SingleOutputMapper();
    private CollectionOutputMapper collectionOutputMapper = new CollectionOutputMapper();

    @ApiOperation(value = "Find Costumer by username", notes = "Username passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Costumer"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping(value = "/costumer/{username}")
    public ResponseEntity<Entity> getCostumerByUsername(@PathVariable("username") String sub) throws Siren4JException {

        Costumer costumer = costumerRepository.findCostumerBySub(sub);

        if (costumer == null)
            throw new NotFoundException();

        CostumerSiren siren = singleOutputMapper.costumerSirenMapper(costumer);
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }

    @ApiOperation(value = "Delete Costumer", notes = "Values to delete Costumer passed in RequestURI")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Costumer Deleted"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @DeleteMapping(value = "/deleteCostumer/{username}")
    public ResponseEntity<Entity> deleteCostumer(@PathVariable("username") String sub) throws Siren4JException {

        Costumer costumer = costumerRepository.findCostumerBySub(sub);

        if (costumer == null)
            throw new NotFoundException();

        costumer.getChecklistTemplates().forEach(template -> template.getTemplateItems().forEach(item -> templateItemRepository.delete(item)));
        costumer.setChecklistTemplates(null);
        costumer.getChecklists().forEach(checklist -> {
            checklist.getItems().forEach(item -> checklistItemRepository.delete(item));
            checklistRepository.delete(checklist);
        });
        costumer.setChecklists(null);
        costumerRepository.delete(costumer);

        CostumerSiren siren = singleOutputMapper.costumerSirenMapper(costumer);
        return ResponseEntity.ok(ReflectingConverter.newInstance().toEntity(siren));
    }
}
