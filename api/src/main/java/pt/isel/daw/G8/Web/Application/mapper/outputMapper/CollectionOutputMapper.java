package pt.isel.daw.G8.Web.Application.mapper.outputMapper;

import com.google.code.siren4j.resource.CollectionResource;
import pt.isel.daw.G8.Web.Application.model.*;
import pt.isel.daw.G8.Web.Application.outputModel.collections.*;

import java.util.List;
import java.util.stream.Collectors;

public class CollectionOutputMapper {

    private SingleOutputMapper singleOutputMapper = new SingleOutputMapper();

    public CostumerSirenCollection costumerSirenCollectionMapper (List<Costumer> list){
        return new CostumerSirenCollection(
                list
                        .stream()
                        .map(s -> singleOutputMapper.costumerSirenMapper(s))
                        .collect(Collectors.toCollection(CollectionResource::new))
        );
    }

    public ChecklistSirenCollection checklistSirenCollectionMapper(List<Checklist> list){
        return new ChecklistSirenCollection(
                list
                        .stream()
                        .map(s -> singleOutputMapper.checklistSirenMapper(s))
                        .collect(Collectors.toCollection(CollectionResource::new))
        );
    }

    public TemplateSirenCollection templateSirenCollectionMapper(List<ChecklistTemplate> list){
        return new TemplateSirenCollection(
                list
                        .stream()
                        .map(s -> singleOutputMapper.templateSirenMapper(s))
                        .collect(Collectors.toCollection(CollectionResource::new))
        );
    }

    public ChecklistItemSirenCollection checklistItemSirenCollectionMapper(List<ChecklistItem> list){
        return new ChecklistItemSirenCollection(
                list
                        .stream()
                        .map(s -> singleOutputMapper.checklistItemSirenMapper(s))
                        .collect(Collectors.toCollection(CollectionResource::new))
        );
    }

    public TemplateItemSirenCollection templateItemSirenCollectionMapper(List<TemplateItem> list){
        return new TemplateItemSirenCollection(
                list
                        .stream()
                        .map(s -> singleOutputMapper.templateItemSirenMapper(s))
                        .collect(Collectors.toCollection(CollectionResource::new))
        );
    }
}
