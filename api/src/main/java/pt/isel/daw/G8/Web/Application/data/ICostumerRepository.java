package pt.isel.daw.G8.Web.Application.data;

import org.springframework.data.repository.CrudRepository;
import pt.isel.daw.G8.Web.Application.model.Costumer;

public interface ICostumerRepository extends CrudRepository<Costumer, String>{
    Costumer findCostumerBySub(String sub);
}
